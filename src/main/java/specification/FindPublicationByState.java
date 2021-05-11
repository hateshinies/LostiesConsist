package specification;

import domain.AbstractEntity;
import domain.Publication;
import domain.PublicationState;

public class FindPublicationByState extends AbstractEntity implements Specification<Publication> {

    private final PublicationState state;

    public FindPublicationByState(PublicationState state) {
        this.state = state;
    }

    @Override
    public boolean isSatisfiedBy(Publication publication) {
        return this.state.equals(publication.state);
    }

    @Override
    public String toSqlClauses() {
        return String.format("select * from publication where state = %s", state.toString());
    }
}
/*
public class PublicationByState extends AbstractSpecification<PublicationState> {

    private final Specification<PublicationState> specification;

    public PublicationByState(Specification<PublicationState> specification) {
        this.specification = specification;
    }

    @Override
    public boolean isSatisfiedBy(PublicationState state) {
        return specification.isSatisfiedBy(state);
    }
 */