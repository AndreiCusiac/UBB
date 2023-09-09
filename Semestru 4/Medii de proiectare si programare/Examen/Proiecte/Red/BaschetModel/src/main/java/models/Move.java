package models;

import java.util.Arrays;

public class Move extends Entity{

    String id_game;

    String id_round;

    String id_player;

    String id_card;

    public Move() {

    }

    public Move(String id_game, String id_round, String id_player, String id_card) {
        this.id_game = id_game;
        this.id_round = id_round;
        this.id_player = id_player;
        this.id_card = id_card;
    }

    public String getId_game() {
        return id_game;
    }

    public void setId_game(String id_game) {
        this.id_game = id_game;
    }

    public String getId_round() {
        return id_round;
    }

    public void setId_round(String id_round) {
        this.id_round = id_round;
    }

    public String getId_player() {
        return id_player;
    }

    public void setId_player(String id_player) {
        this.id_player = id_player;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    @Override
    public String toString() {
        return "Move{" +
                "id='" + id + '\'' +
                ", id_game='" + id_game + '\'' +
                ", id_round='" + id_round + '\'' +
                ", id_player='" + id_player + '\'' +
                ", id_card='" + id_card + '\'' +
                '}';
    }
}
