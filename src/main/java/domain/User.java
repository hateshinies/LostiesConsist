package domain;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;

public class User extends AbstractEntity {
    String email;
    UserRole role;
    String token;

    String name;
    String phone;
    String password;

    String code;
    boolean isActual;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public User(String code, String password, UserRole role, String name, String phone) {
        this.code = code;
        this.password = password;
        this.role = role;
        this.name = name;
        this.phone = phone;
    }

    public String startTwoStepRegistration(Actor actor) {
        email = actor.email;
        role = actor.role;
        code = generateCode();
        return code;
    }

    /**
     * @param user - это JSON-объект, который получен от фронта
     */
    public boolean finishTwoStepRegistration(User user) {
        if (!user.code.equals(code)) return false;
        password = createHash(user.password);
        role = user.role;
        name = user.name;
        phone = user.phone;
        token = generateToken();
        isActual = true;
        return true;
    }

    public Actor auth(String password) {
        password = createHash(password);
        token = null;
        if (Objects.equals(this.password, password) && isActual)
            token = generateToken();
        return new Actor(email, role, token);
    }

    public void block() {
        isActual = false;
        token = null;
    }

    private String generateCode() {
        byte[] array = new byte[6];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private String generateToken() {
        byte[] array = new byte[12];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private String createHash(String password) {
        return String.valueOf(password.hashCode());
    }

}
