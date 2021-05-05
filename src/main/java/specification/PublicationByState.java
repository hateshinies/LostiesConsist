package specification;

import domain.Publication;
import domain.PublicationState;

public class PublicationByState implements Specification<Publication> {

    private final PublicationState state;

    public PublicationByState(PublicationState state) {
        this.state = state;
    }

    @Override
    public boolean specified(Publication publication) {
        return publication.hasState(state);
    }

}