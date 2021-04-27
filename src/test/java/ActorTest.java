import domain.Actor;
import domain.UserRole;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ActorTest {
    SoftAssertions soft = new SoftAssertions();

    @AfterEach
    public void assertAll() {
        soft.assertAll();
    }

    @Test
    public void shouldCreateActorUsingConstructorWithEmail() {
        String email = "zero@cocacola.com";
        Actor actor = new Actor(email);

        soft.assertThat(actor).hasFieldOrPropertyWithValue("email", email);
    }

    @Test
    public void shouldCreateActorUsingConstructor() {
        String email = "zero@cocacola.com";
        UserRole role = UserRole.OWNER;
        Actor actor = new Actor(email, role, null);

        soft.assertThat(actor).hasFieldOrPropertyWithValue("role", role);
    }

    @Test
    public void shouldCheckActorIsAuthed() {
        String email = "zero@cocacola.com";
        UserRole role = UserRole.ADMIN;
        Actor actor = new Actor(email, role, null);

        boolean result = actor.isAuthed();

        soft.assertThat(result).isFalse();
    }
}
