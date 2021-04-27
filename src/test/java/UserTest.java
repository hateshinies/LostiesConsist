import domain.Actor;
import domain.User;
import domain.UserRole;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    SoftAssertions soft = new SoftAssertions();

    @AfterEach
    public void assertAll() {
        soft.assertAll();
    }

    @Test
    public void shouldCreateUserFromConstructor() {
        UserRole role = UserRole.OWNER;
        String password = "easypass";
        String name = "Jack";
        String phone = "8-970-421-9812";
        String code = "2b7as";

        User user = new User(code, password, role, name, phone);

        soft.assertThat(user).hasFieldOrPropertyWithValue("phone", phone);
    }

    @Test
    public void shouldRegisterUser() {
        String email = "vasko@dagama.com";
        UserRole role = UserRole.OWNER;
        String password = "easypass";
        String name = "Jack";
        String phone = "8-970-421-9812";
        Actor actor = new Actor(email, role, null);
        User user = new User();

        String confirmationCode = user.startTwoStepRegistration(actor);
        User userFromWeb = new User(confirmationCode, password, role, name, phone);
        boolean result = user.finishTwoStepRegistration(userFromWeb);

        soft.assertThat(result).isTrue();
        soft.assertThat(user).hasFieldOrProperty("token");
    }

    @Test
    public void shouldAuthenticateUser() {
        String email = "vasko@dagama.com";
        UserRole role = UserRole.VOLUNTEER;
        String password = "easypass";
        String name = "Jack";
        String phone = "8-970-421-9812";
        Actor actor = new Actor(email, role, null);
        User user = new User();
        String confirmationCode = user.startTwoStepRegistration(actor);
        User userFromWeb = new User(confirmationCode, password, role, name, phone);
        user.finishTwoStepRegistration(userFromWeb);
        //вместо этого внести юзера скриптом в базу через executor

        Actor authedActor = user.auth(password);

        soft.assertThat(authedActor).hasFieldOrProperty("token");
    }

    @Test
    public void shouldBlockUser() {
        String email = "vasko@dagama.com";
        UserRole role = UserRole.RESPONDER;
        String password = "easypass";
        String name = "Jack";
        String phone = "8-970-421-9812";
        Actor actor = new Actor(email, role, null);
        User user = new User();
        String confirmationCode = user.startTwoStepRegistration(actor);
        User userFromWeb = new User(confirmationCode, password, role, name, phone);
        user.finishTwoStepRegistration(userFromWeb);

        user.block();

        soft.assertThat(user).hasFieldOrPropertyWithValue("token", null).hasFieldOrPropertyWithValue("isActual", false);
    }
}
