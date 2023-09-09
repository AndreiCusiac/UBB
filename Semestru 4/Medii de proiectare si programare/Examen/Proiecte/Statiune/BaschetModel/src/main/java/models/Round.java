package models;

import java.util.Arrays;
import java.util.Objects;

public class Round extends Entity{

    String id_game;

    String id_player;

    String id_cards;

    Integer cardsReceived;

    public Round() {

    }

    public Round(String id_game, String id_player, String id_cards, Integer cardsReceived) {
        this.id_game = id_game;
        this.id_player = id_player;
        this.id_cards = id_cards;
        this.cardsReceived = cardsReceived;
    }

    public String getId_game() {
        return id_game;
    }

    public void setId_game(String id_game) {
        this.id_game = id_game;
    }

    public String getId_player() {
        return id_player;
    }

    public void setId_player(String id_player) {
        this.id_player = id_player;
    }

    public String getId_cards() {
        return id_cards;
    }

    public void setId_cards(String id_cards) {
        this.id_cards = id_cards;
    }

    public Integer getCardsReceived() {
        return cardsReceived;
    }

    public void setCardsReceived(Integer cardsReceived) {
        this.cardsReceived = cardsReceived;
    }

    @Override
    public String toString() {
        return "Round{" +
                "id='" + id + '\'' +
                ", id_game='" + id_game + '\'' +
                ", id_player='" + id_player + '\'' +
                ", id_cards=" + id_cards +
                ", cardsReceived=" + cardsReceived +
                '}';
    }
}
