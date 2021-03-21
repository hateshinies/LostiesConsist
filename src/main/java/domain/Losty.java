package domain;

public class Losty {
    Long lostyId;
    Long ownerId;
    Long category;
    String name;
    String description;
    String photo;

    public Long getLostyId() {
        return lostyId;
    }

    public void setLostyId(Long lostyId) {
        this.lostyId = lostyId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
