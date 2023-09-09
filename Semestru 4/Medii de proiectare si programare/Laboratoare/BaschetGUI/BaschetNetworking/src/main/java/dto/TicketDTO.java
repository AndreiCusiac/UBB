package dto;

import java.io.Serializable;

public class TicketDTO implements Serializable {
    String idMatch;
    String id;

    String name;
    Integer seats;

    public TicketDTO(String idTicket, String idMatch, String name, Integer seats) {
        this.id = idTicket;
        this.idMatch = idMatch;
        this.name = name;
        this.seats = seats;
    }

    public TicketDTO(String idMatch, String name, Integer seats) {
        this.idMatch = idMatch;
        this.name = name;
        this.seats = seats;
    }

    public String getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(String idMatch) {
        this.idMatch = idMatch;
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

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }
}
