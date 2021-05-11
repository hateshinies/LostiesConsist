package specification;

import domain.AbstractEntity;
import domain.Publication;

public class PublicationById extends AbstractEntity implements Specification<Publication> {

    private final long id;

    public PublicationById(long id) {
        this.id = id;
    }

    @Override
    public boolean isSatisfiedBy(Publication publication) {
        return id == publication.id;
    }

    @Override
    public String toSqlClauses() {
        return String.format("select * from publication where id = %d", id);
    }

}