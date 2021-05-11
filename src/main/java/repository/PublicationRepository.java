package repository;

import domain.Actor;
import domain.Publication;
import specification.Specification;
import utils.SqlExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PublicationRepository implements Repository<Publication> {
    private final SqlExecutor executor;

    public PublicationRepository(Connection connection) throws SQLException {
        this.executor = new SqlExecutor(connection);
    }

    @Override
    public void create(Publication publication) throws Exception {
        Actor actor = publication.actor;
        if (actor != null) {
            actor.pack();
            executor.simpleStringQuery(actor.queries.createQuery);
            publication.actor = null;
        }
        if (publication.pack().isEmpty())
            throw new IllegalStateException("nothing to insert!");
        executor.simpleLongQuery(publication.queries.createQuery);
    }

    @Override
    public void update(Publication publication) throws Exception {
        Actor actor = publication.actor;
        if (actor != null) {
            actor.pack();
            executor.getNonDeleted(actor.queries.updateQuery);
            publication.actor = null;
        }

        if (publication.pack().isEmpty())
            throw new IllegalStateException("nothing to update!");
        executor.getNonDeleted(publication.queries.updateQuery);
    }

    @Deprecated
    @Override
    public void delete(Publication publication) throws Exception {
        Actor actor = publication.actor;
        if (actor != null) {
            actor.pack();
            executor.simpleStringQuery(actor.queries.deleteQuery);
            publication.actor = null;
        }
        publication.pack();
        executor.simpleLongQuery(publication.queries.deleteQuery);
    }

    @Override
    public void softDelete(Publication publication) throws Exception {
        Actor actor;
        if (publication.actor != null) {
            actor = publication.actor;
            actor.pack();
            executor.getNonDeleted(actor.queries.softDeleteQuery);
            publication.actor = null;
        }
        if (publication.pack().isEmpty())
            throw new IllegalStateException("nothing to update!");
        executor.getNonDeleted(publication.queries.softDeleteQuery);
    }

    @Override
    public Publication getOne(Publication publication) throws Exception {
        publication.pack();
        Map<String, String> object = executor.getNonDeleted(publication.queries.getOneQuery).get(0);
        if (object.isEmpty() || object.get("isDeleted").equals("true"))
            return null;
        publication = (Publication) publication.unpack(object);
        String email = publication.email;
        if (email != null) {
            Actor actor = new Actor(email);
            actor.pack();
            actor = (Actor) actor.unpack(executor.getNonDeleted(actor.queries.getOneQuery).get(0));
            publication.actor = actor;
        }
        return publication;

    }

    @Override
    public List<Publication> getAll() throws Exception {
        Publication publication = new Publication();
        publication.pack();
        List<Map<String, String>> parentList = executor.getNonDeleted(publication.queries.getAllQuery);
        Actor actor = new Actor();
        actor.pack();
        List<Map<String, String>> childList = executor.getNonDeleted(actor.queries.getAllQuery);

        if (parentList.isEmpty())
            throw new IllegalStateException("table is empty!");

        List<Publication> publicationList = new ArrayList<>();
        for (Map<String, String> parent : parentList) {
            publication = (Publication) publication.unpack(parent);

            for (Map<String, String> object : childList) {
                if (object.containsValue(publication.email)) {
                    actor = (Actor) actor.unpack(object);
                    publication.actor = actor;
                }
            }
            publicationList.add(publication);
        }
        return publicationList;
    }

    @Override
    public boolean exists(Publication publication) throws Exception {
        return getOne(publication) != null;
    }

    @Override
    public void save(Publication publication) throws Exception {
        if (exists(publication))
            update(publication);
        else
            create(publication);
    }

    @Override
    public Actor getDelegate(Publication publication) throws Exception {
        if (publication.email == null)
            throw new IllegalStateException("cannot get actor. email is null");
        Actor actor = new Actor(publication.email);
        actor.pack();
        List<Map<String, String>> childList = executor.getNonDeleted(actor.queries.getOneQuery);
        actor = (Actor) actor.unpack(childList.get(0));
        return actor;
    }

    @Override
    public List<Publication> find(Specification<Publication> spec) throws Exception {
        List<Map<String, String>> parentList = executor.getNonDeleted(spec.toSqlClauses());

        Actor actor = new Actor();
        actor.pack();
        List<Map<String, String>> childList = executor.getNonDeleted(actor.queries.getAllQuery);

        if (parentList.isEmpty())
            throw new IllegalStateException("nothing found!");

        Publication publication = new Publication();
        List<Publication> publicationList = new ArrayList<>();
        for (Map<String, String> parent : parentList) {
            publication = (Publication) publication.unpack(parent);

            for (Map<String, String> object : childList) {
                if (object.containsValue(publication.email))
                    actor = (Actor) actor.unpack(object);
                publication.actor = actor;
            }
            publicationList.add(publication);
        }

        return publicationList;
    }
}
