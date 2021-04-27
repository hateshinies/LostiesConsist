import domain.Actor;
import domain.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ResponseTest {
    SoftAssertions soft = new SoftAssertions();

    @AfterEach
    public void assertAll() {
        soft.assertAll();
    }

    @Test
    public void shouldCreateResponseUsingConstructorWithId() {
        long id = 31L;
        Response response = new Response(id);

        soft.assertThat(response).hasFieldOrPropertyWithValue("id", id);
    }

    @Test
    public void shouldCreateResponse() {
        long pubId = 31L;
        String photo = "C:/Users/photo.jpg";
        String description = "very short description";
        Response response = new Response();

        response.create(pubId, photo, description);

        soft.assertThat(response).hasFieldOrPropertyWithValue("photo", photo);
    }


    @Test
    public void shouldCreateResponseUsingActor() {
        long pubId = 31L;
        String photo = "C:/Users/photo.jpg";
        String description = "very short description";
        Actor actor = Mockito.mock(Actor.class);
        Response response = new Response(actor);

        response.create(pubId, photo, description);

        soft.assertThat(response).hasFieldOrPropertyWithValue("actor", actor);
    }
}
