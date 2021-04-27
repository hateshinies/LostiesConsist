package domain;

public class Actor extends AbstractEntity {
    String email;
    UserRole role;
    String token;

    public Actor() {
    }

    public Actor(String email) {
        this.email = email;
    }

    public Actor(String email, UserRole role, String token) {
        this.email = email;
        this.role = role;
        this.token = token;
    }

    public boolean isAuthed() {
        return token != null;
    }
}
