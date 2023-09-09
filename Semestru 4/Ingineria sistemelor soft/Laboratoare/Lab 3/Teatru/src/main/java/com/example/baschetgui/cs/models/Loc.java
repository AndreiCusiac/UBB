package com.example.baschetgui.cs.models;

import java.util.Date;
import java.util.Objects;

public class Loc extends Entity {

    String name;
    String email;
    String seat;

    Boolean reserved;
    Boolean payed;

    public Loc() {

    }

    public Loc(String name, String email, String seat, Boolean reserved, Boolean payed) {
        this.name = name;
        this.email = email;
        this.seat = seat;
        this.reserved = reserved;
        this.payed = payed;
    }

    public Loc(String seat, Boolean reserved, Boolean payed) {
        this.seat = seat;
        this.reserved = reserved;
        this.payed = payed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loc loc = (Loc) o;
        return Objects.equals(name, loc.name) && Objects.equals(email, loc.email) && Objects.equals(seat, loc.seat) && Objects.equals(reserved, loc.reserved) && Objects.equals(payed, loc.payed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, seat, reserved, payed);
    }

    @Override
    public String toString() {
        return "Loc{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", seat='" + seat + '\'' +
                ", reserved=" + reserved +
                ", payed=" + payed +
                '}';
    }
}
