package domain;

/**
 * Класс Поисковое событие
 * Содержит поля id, pubId, photo, description, actor
 */
public class Response extends AbstractEntity {
    long id;
    long pubId;
    String photo;
    String description;
    Actor actor;

    public Response() {
    }

    public Response(long id) {
        this.id = id;
    }

    public Response(Actor actor) {
        this.actor = actor;
    }

    /**
     * Создание отклика
     * @param pubId ID публикации, на которую отклик
     */
    public void create(long pubId, String photo, String description) {
        this.pubId = pubId;
        this.photo = photo;
        this.description = description;
    }

}