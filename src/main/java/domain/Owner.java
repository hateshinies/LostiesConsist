package domain;

import java.util.Map;

public class Owner {
    Integer ownerId;
    String name;
    String phone;
    String email;
    String[] fields;
    Map<String, String> values;

    public String[] getFieldsArray() {
        return fields;
//        return new String[]{ownerId.toString(), name, phone, email};
    }

    public void setFieldsArray(String[] fields) {
        this.fields = fields;
    }

    public Owner() {
    }

    public Owner(Integer ownerId, String name, String phone, String email) {
        this.ownerId = ownerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean hasOwnerId(Integer ownerId) {
        return this.ownerId.equals(ownerId);
    }
}
