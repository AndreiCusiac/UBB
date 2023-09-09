package dto;

import models.Match;

import java.io.Serializable;

public class SellTicketDTO implements Serializable {
    String id;

    String homeTeam;
    String awayTeam;

    Double ticketPrice;
    Integer totalSeats;
    Integer availableSeats;

    String soldOut="";

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getSoldOut() {
        return soldOut;
    }

    public void setSoldOut(String soldOut) {
        this.soldOut = soldOut;
    }

    String name;
    Integer seats;

    public SellTicketDTO(String id, String homeTeam, String awayTeam, Double ticketPrice, Integer totalSeats, String name, Integer seats) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
        this.name = name;
        this.seats = seats;
    }

    public SellTicketDTO(Match match, String name, Integer seats) {
        this.id = match.getId();
        this.homeTeam = match.getHomeTeam();
        this.awayTeam = match.getAwayTeam();
        this.availableSeats = match.getAvailableSeats();
        this.ticketPrice = match.getTicketPrice();
        this.totalSeats = match.getTotalSeats();
        this.soldOut = match.getSoldOut();

        this.name = name;
        this.seats = seats;
    }

    public SellTicketDTO(String id, String homeTeam, String awayTeam, Double ticketPrice, Integer totalSeats, Integer availableSeats, String soldOut, String name) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.soldOut = soldOut;
        this.name = name;
    }

    public SellTicketDTO(String id, String homeTeam, String awayTeam, Double ticketPrice, Integer totalSeats, Integer availableSeats, String soldOut, String name, Integer seats) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.soldOut = soldOut;
        this.name = name;
        this.seats = seats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
