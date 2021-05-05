package domain;

/**
 * Класс Публикация - это "карточка" с информацией(текст, изображение, состояние, время создания)
 */
public class Publication extends AbstractEntity {
    long id;
    String description;
    String photo;
    public Actor actor;
    public PublicationState state;
    long createdAt;
    public String email;

    public Publication() {
    }

    public Publication(Long id) {
        this.id = id;
    }

    public Publication(Actor actor, String photo, String description) {
        this.actor = actor;
        this.photo = photo;
        this.description = description;
    }

    public void create(Publication publication) {
        this.actor = publication.actor;
        if (actor == null)
            throw new IllegalStateException("actor is null!");
        this.description = publication.description;
        this.photo = publication.photo;
        this.createdAt = System.currentTimeMillis();
        this.email = actor.email;
        if (actor.isAuthed())
            state = PublicationState.CONFIRMED;
        else
            state = PublicationState.CONFIRM_WAIT;
    }

    public boolean hasId(long id) {
        return this.id == id;
    }

    public boolean hasState(PublicationState state) {
        return this.state.equals(state);
    }

}
