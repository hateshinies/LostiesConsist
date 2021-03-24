package domain;

import java.util.Map;

public class Owner {
    Long id;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean hasId(Long id) {
        return this.id.equals(id);
    }
}
