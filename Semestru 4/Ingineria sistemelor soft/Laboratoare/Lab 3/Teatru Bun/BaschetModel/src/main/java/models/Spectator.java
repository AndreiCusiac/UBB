package models;

import java.util.Objects;

public class Spectator extends Entity{

    String name;
    String password;

    public Spectator() {

    }

    public Spectator(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Spectator(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Spectator(String name) {
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
        Spectator that = (Spectator) o;
        return id.equals(that.id) && Objects.equals(name, that.name) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password);
    }

    @Override
    public String toString() {
        return "Spectator{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
