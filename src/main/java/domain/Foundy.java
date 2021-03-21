package domain;

public class Foundy {
    Long foundyId;
    Long finderId;
    Long category;
    String name;
    String description;
    String photo;

    public Foundy() {
    }

    public Long getFoundyId() {
        return foundyId;
    }

    public void setFoundyId(Long foundyId) {
        this.foundyId = foundyId;
    }

    public Long getFinderId() {
        return finderId;
    }

    public void setFinderId(Long finderId) {
        this.finderId = finderId;
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
