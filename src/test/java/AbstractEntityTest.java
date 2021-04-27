import domain.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class AbstractEntityTest {
    SoftAssertions soft = new SoftAssertions();

    @AfterEach
    public void assertAll() {
        soft.assertAll();
    }

    @Test
    public void shouldSetCargo() {
        Map<String, String> cargo = new HashMap<>();
        String fieldName = "email";
        String email = "email@email.ru";
        cargo.put(fieldName, email);
        User user = new User();

        user.setCargo(cargo);

        soft.assertThat(user.getCargo()).isEqualTo(cargo);
    }

    @Test
    public void shouldGetCargo() throws IllegalAccessException {
        Map<String, String> cargo;
        String email = "email@email.ru";
        User user = new User(email);
        user.pack();

        cargo = user.getCargo();

        soft.assertThat(cargo).hasFieldOrPropertyWithValue("email", email);
    }

    @Test
    public void shouldPackInstance() throws Exception {
        String email = "email@email.ru";
        User user = new User(email);

        user.pack();

        soft.assertThat(user.getCargo()).hasFieldOrPropertyWithValue("email", email);
    }

    @Test
    public void shouldUnPackInstance() throws Exception {
        String email = "email@email.ru";
        User user = new User(email);
        user.pack();

        User newUser = (User) user.unpack(User.class);

        soft.assertThat(newUser).hasFieldOrPropertyWithValue("email", email);
    }
}
