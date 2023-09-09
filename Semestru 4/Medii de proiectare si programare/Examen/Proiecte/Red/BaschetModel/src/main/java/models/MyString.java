package models;

public class MyString extends Entity{

    String value;

    public MyString() {

    }

    public MyString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MyString{" +
                "value='" + value + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
