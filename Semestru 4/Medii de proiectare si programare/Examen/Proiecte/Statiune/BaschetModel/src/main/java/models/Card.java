package models;

import java.util.Objects;

public class Card extends Entity{

    String color;

    Integer value;

    public Card() {

    }

    public Card(String color, Integer value) {
        this.color = color;
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", color='" + color + '\'' +
                ", value=" + value +
                '}';
    }
}
