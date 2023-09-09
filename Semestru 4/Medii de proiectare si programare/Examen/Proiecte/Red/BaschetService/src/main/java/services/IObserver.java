package services;

import models.Card;
import models.Match;
import models.Player;

import java.util.ArrayList;

public interface IObserver {
    void ticketSold() throws Exception;
    void ticketSold(ArrayList<Match> matches) throws Exception;

    void gameStarted(String lettersPossible) throws Exception;

    void movesMade(String s) throws Exception;
}
