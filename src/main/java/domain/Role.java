package domain;

public class Role {
    UserRole role;
    String description;
    int permissions;

    public int getPermissions() {
        return this.permissions;
    }

    public void create(UserRole role, String description, int permissions) {
        this.role = role;
        this.description = description;
        this.permissions = permissions;
    }
}
