package models;

import java.util.Objects;

public class Regizor extends Entity{

    String name;
    String password;

    public Regizor() {

    }

    public Regizor(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Regizor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Regizor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Regizor that = (Regizor) o;
        return id.equals(that.id) && Objects.equals(name, that.name) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password);
    }

    @Override
    public String toString() {
        return "Regizor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
