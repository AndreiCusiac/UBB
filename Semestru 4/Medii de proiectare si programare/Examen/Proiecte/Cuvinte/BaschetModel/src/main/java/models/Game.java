package models;

import java.util.Date;
import java.util.Objects;

public class Game extends Entity{

    String player;

    String pozitie;

    String punctaj;

    String timp;

    String guessed;

    public Game () {

    }

    public Game(String player, String pozitie, String punctaj, String timp, String guessed) {
        this.player = player;
        this.pozitie = pozitie;
        this.punctaj = punctaj;
        this.timp = timp;
        this.guessed = guessed;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getPozitie() {
        return pozitie;
    }

    public void setPozitie(String pozitie) {
        this.pozitie = pozitie;
    }

    public String getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(String punctaj) {
        this.punctaj = punctaj;
    }

    public String getTimp() {
        return timp;
    }

    public void setTimp(String timp) {
        this.timp = timp;
    }

    public String getGuessed() {
        return guessed;
    }

    public void setGuessed(String guessed) {
        this.guessed = guessed;
    }

    @Override
    public String toString() {
        return "Game{" +
                "player='" + player + '\'' +
                ", pozitie='" + pozitie + '\'' +
                ", punctaj='" + punctaj + '\'' +
                ", timp='" + timp + '\'' +
                ", guessed='" + guessed + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
