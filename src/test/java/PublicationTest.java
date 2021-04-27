import domain.Actor;
import domain.Publication;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PublicationTest {
    SoftAssertions soft = new SoftAssertions();

    @AfterEach
    public void assertAll() {
        soft.assertAll();
    }

    @Test
    public void shouldCreatePublication() {
        String photo = "C:/Users/newPhoto.png";
        String description = "new descr";
        Actor actor = Mockito.mock(Actor.class);
        Publication publication = new Publication();

        publication.create(new Publication(actor, photo, description));

        soft.assertThat(publication).hasFieldOrPropertyWithValue("photo", photo);
    }

    @Test
    public void shouldCreatePublicationUsingConstructorWihtId() {
        long id = 12L;

        Publication publication = new Publication(id);

        soft.assertThat(publication).hasFieldOrPropertyWithValue("id", id);
    }

    @Test
    public void shouldCreatePublicationUsingConstructor() {
        String photo = "some photo";
        String description = "some description";
        Actor actor = Mockito.mock(Actor.class);

        Publication publication = new Publication(actor, photo, description);

        soft.assertThat(publication).hasFieldOrPropertyWithValue("description", description);
    }
}
