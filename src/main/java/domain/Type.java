package domain;

/**
 * Класс Тип события
 * Содержит тип события, его описание и цену
 */
public class Type extends AbstractEntity {

    ExtraType type;
    int price;
    String description;

    public Type() {
    }

    public Type(ExtraType type) {
        this.type = type;
    }

    public void create(ExtraType type, int price, String description) {
        this.type = type;
        this.price = price;
        this.description = description;
    }

}