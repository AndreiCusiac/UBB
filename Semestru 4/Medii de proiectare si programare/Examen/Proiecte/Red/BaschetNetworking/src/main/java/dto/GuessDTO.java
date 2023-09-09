package dto;

import models.Player;

import java.io.Serializable;

public class GuessDTO implements Serializable {

    Player player;
    String guess;

    public GuessDTO(Player player, String guess) {
        this.player = player;
        this.guess = guess;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
}
