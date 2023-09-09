package models;

import java.util.Arrays;

public class Move extends Entity{

    String game;

    String player;

    String coord;

    public Move() {

    }

    public Move(String game, String player, String coord) {
        this.game = game;
        this.player = player;
        this.coord = coord;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }

    @Override
    public String toString() {
        return "Move{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", coord='" + coord + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
