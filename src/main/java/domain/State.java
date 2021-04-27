package domain;

public class State extends AbstractEntity {
    //add setter/getter?
    public PublicationState state;
    String description;

    public State() {
    }

    public void create(PublicationState state, String description) {
        this.state = state;
        this.description = description;
    }
}
