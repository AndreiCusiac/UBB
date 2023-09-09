package models;

import java.util.Objects;

public class Game extends Entity{

    String player1;

    String player2;

    String player3;

    public Game() {

    }

    public Game(String player1, String player2, String player3) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer3() {
        return player3;
    }

    public void setPlayer3(String player3) {
        this.player3 = player3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game joc = (Game) o;
        return Objects.equals(player1, joc.player1) && Objects.equals(player2, joc.player2) && Objects.equals(player3, joc.player3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player1, player2, player3);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", player1='" + player1 + '\'' +
                ", player2='" + player2 + '\'' +
                ", player3='" + player3 + '\'' +
                '}';
    }
}
