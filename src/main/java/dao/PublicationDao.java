/*
package dao;

import domain.Actor;
import domain.Publication;
import utils.SqlExecutor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PublicationDao {
    private final SqlExecutor executor;

    public PublicationDao(Connection connection) {
        this.executor = new SqlExecutor(connection);
    }

    public long create(Publication publication) throws Exception {
        Actor actor = publication.actor;
        String email;
        if (actor != null) {
            actor.pack();
            email = executor.simpleStringQuery(actor.queries.createQuery);
            System.out.println("actor saved with email " + email);
            publication.actor = null;
        }
        long id;
        publication.pack();
        id = executor.simpleLongQuery(publication.queries.createQuery);
        System.out.println("publication saved with id " + id);
        return id;
    }

    public long update(Publication publication) throws Exception {
        Actor actor = publication.actor;
        String email;
        if (actor != null) {
            actor.pack();
            email = executor.simpleStringQuery(actor.queries.updateQuery);
            System.out.println("actor updated with email " + email);
            publication.actor = null;
        }

        publication.pack();
        long id;

        if (publication.getCargo().isEmpty())
            throw new IllegalStateException("nothing to update!");
        System.out.println(publication.queries.updateQuery);
        id = executor.simpleLongQuery(publication.queries.updateQuery);
        System.out.println("publication updated with id " + id);
        return id;
    }

    public long delete(Publication publication) throws Exception {
        Actor actor = publication.actor;
        String email;
        if (actor != null) {
            actor.pack();
            email = executor.simpleStringQuery(actor.queries.deleteQuery);
            System.out.println("actor deleted with email " + email);
            publication.actor = null;
        }
        publication.pack();
        long id = executor.simpleLongQuery(publication.queries.deleteQuery);
        System.out.println("publication deleted with id " + id);
        return id;
    }

    public Publication getOne(Publication publication) throws Exception {
        publication.pack();
        Map<String, String> object = executor.getValuesQuery(publication.queries.getOneQuery).get(0);
        if (object.isEmpty())
            return null;
        publication.setCargo(object);
        publication = (Publication) publication.unpack(Publication.class);
        String email = publication.email;
        if (email == null)
            return publication;
        Actor actor = new Actor(email);
        actor.pack();
        actor.setCargo(executor.getValuesQuery(actor.queries.getOneQuery).get(0));
        actor = (Actor) actor.unpack(Actor.class);
        publication.actor = actor;
        return publication;
    }

    public List<Publication> getAll() throws Exception {
        Publication publication = new Publication();
        publication.pack();
        List<Map<String, String>> parentList = executor.getValuesQuery(publication.queries.getAllQuery);
        Actor actor = new Actor();
        actor.pack();
        List<Map<String, String>> childList = executor.getValuesQuery(actor.queries.getAllQuery);

        if (parentList.isEmpty())
            throw new IllegalStateException("table is empty!");

        List<Publication> publicationList = new ArrayList<>();
        for (Map<String, String> parent : parentList) {
            publication.setCargo(parent);
            publication = (Publication) publication.unpack(Publication.class);

            for (Map<String, String> object : childList) {
                if (object.containsValue(publication.email))
                    actor.setCargo(object);
                actor = (Actor) actor.unpack(Actor.class);
                publication.actor = actor;
            }
            publicationList.add(publication);
        }
        return publicationList;
    }
}

*/
