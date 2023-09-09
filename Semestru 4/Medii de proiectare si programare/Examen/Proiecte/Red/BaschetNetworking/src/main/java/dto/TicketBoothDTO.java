package dto;

import java.io.Serializable;

public class TicketBoothDTO implements Serializable {
    String id;

    String name;
    String password;
    String salt;
    String hash;

    public TicketBoothDTO(String id, String name, String password, String salt, String hash) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.hash = hash;
    }

    public TicketBoothDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
