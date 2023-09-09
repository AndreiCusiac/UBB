package dto;

import models.Card;
import models.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class CardsDTO implements Serializable {

    ArrayList<Card> cards;

    public CardsDTO(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
