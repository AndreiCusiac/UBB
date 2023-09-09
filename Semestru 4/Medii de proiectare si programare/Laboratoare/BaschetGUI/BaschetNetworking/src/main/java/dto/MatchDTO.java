package dto;

import java.io.Serializable;

public class MatchDTO implements Serializable {
    String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MatchDTO(String id, String homeTeam, String awayTeam, Double ticketPrice, Integer totalSeats, Integer availableSeats) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    public MatchDTO(String id, String homeTeam, String awayTeam, Double ticketPrice, Integer totalSeats, Integer availableSeats, String soldOut) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.soldOut = soldOut;
    }

    public MatchDTO(String homeTeam, String awayTeam, Double ticketPrice, Integer totalSeats, Integer availableSeats, String soldOut) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.soldOut = soldOut;
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
}
