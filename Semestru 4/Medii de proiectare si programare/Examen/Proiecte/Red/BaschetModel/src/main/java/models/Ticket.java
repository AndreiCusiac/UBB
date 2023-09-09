package models;

import java.util.Objects;

public class Ticket extends Entity{
    String idMatch;
    String name;
    Integer seats;

    public Ticket() {

    }

    public Ticket(String idTicket, String idMatch, String name, Integer seats) {
        this.id = idTicket;
        this.idMatch = idMatch;
        this.name = name;
        this.seats = seats;
    }

    public Ticket(String idMatch, String name, Integer seats) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id.equals(ticket.id) && idMatch.equals(ticket.idMatch) && Objects.equals(name, ticket.name) && Objects.equals(seats, ticket.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idMatch, name, seats);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", idMatch='" + idMatch + '\'' +
                ", name='" + name + '\'' +
                ", seats='" + seats + '\'' +
                '}';
    }
}
