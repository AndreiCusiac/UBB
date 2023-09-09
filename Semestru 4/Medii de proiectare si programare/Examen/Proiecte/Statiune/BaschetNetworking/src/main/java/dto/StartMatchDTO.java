package dto;

import models.Card;
import models.Match;
import models.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class StartMatchDTO implements Serializable {

    ArrayList<Player> players;
    ArrayList<Card> cards;

    public StartMatchDTO(ArrayList<Player> players, ArrayList<Card> cards) {
        this.players = players;
        this.cards = cards;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
