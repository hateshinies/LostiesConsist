package specification;

import domain.Publication;
import repo.Repo;

import java.sql.SQLException;

public class PublicationById implements Specification<Publication> {

    private final long id;

    public PublicationById(long id) {
        this.id = id;
    }

    @Override
    public boolean specified(Repo<Publication> repository) {
        return repository.hasId(id);
    }

}