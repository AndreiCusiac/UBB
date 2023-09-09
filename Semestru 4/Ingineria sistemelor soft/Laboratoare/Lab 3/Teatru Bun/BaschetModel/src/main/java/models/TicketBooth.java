package models;

import java.util.Objects;

public class TicketBooth extends Entity{

    String name;
    String password;
    String salt;
    String hash;

    public TicketBooth(String id, String name, String password, String salt, String hash) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.hash = hash;
    }

    public TicketBooth(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public TicketBooth(String name) {
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketBooth that = (TicketBooth) o;
        return id.equals(that.id) && Objects.equals(name, that.name) && Objects.equals(password, that.password) && Objects.equals(salt, that.salt) && Objects.equals(hash, that.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, salt, hash);
    }

    @Override
    public String toString() {
        return "TicketBooth{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
