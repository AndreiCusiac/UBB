package services;

import models.Card;
import models.Match;
import models.Player;
import models.Pozitie;

import java.util.ArrayList;

public interface IObserver {
    void ticketSold() throws Exception;
    void ticketSold(ArrayList<Match> matches) throws Exception;

    void gameStarted(Pozitie pozitieCurenta) throws Exception;

    void movesMade(String guess, String punctaj) throws Exception;
}
