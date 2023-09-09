package models;

import java.util.Objects;

public class Match extends Entity{

    String homeTeam;
    String awayTeam;

    Double ticketPrice;
    Integer totalSeats;

    public String getSoldOut() {
        return soldOut;
    }

    public void setSoldOut(String soldOut) {
        this.soldOut = soldOut;
    }

    Integer availableSeats;

    String soldOut="";

    public Match() {

    }

    public Match(String id, String homeTeam, String awayTeam, Double ticketPrice, Integer totalSeats, Integer availableSeats) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    public Match(String id, String homeTeam, String awayTeam, Double ticketPrice, Integer availableSeats) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.ticketPrice = ticketPrice;
        this.availableSeats = availableSeats;
    }

    public Match(String homeTeam, String awayTeam, Double ticketPrice, Integer totalSeats, Integer availableSeats) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    public Match(String homeTeam, String awayTeam, Double ticketPrice, Integer availableSeats) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.ticketPrice = ticketPrice;
        this.availableSeats = availableSeats;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return id.equals(match.id) && Objects.equals(homeTeam, match.homeTeam) && Objects.equals(awayTeam, match.awayTeam) && Objects.equals(ticketPrice, match.ticketPrice) && Objects.equals(totalSeats, match.totalSeats) && Objects.equals(availableSeats, match.availableSeats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, homeTeam, awayTeam, ticketPrice, totalSeats, availableSeats);
    }

    @Override
    public String toString() {
        return "Match{" +
                "id='" + id +'\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", totalSeats=" + totalSeats +
                ", availableSeats=" + availableSeats +
                '}';
    }
}
