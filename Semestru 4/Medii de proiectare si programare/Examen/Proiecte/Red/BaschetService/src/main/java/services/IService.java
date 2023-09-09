package services;

import models.Match;
import models.Player;

import java.util.ArrayList;

public interface IService {
    public ArrayList<Match> getAllMatches() throws Exception;
    public ArrayList<Match> getMatchesSortedByAvailableSeatsDesc() throws Exception;

    public void sellTicket(Match match, String name, Integer seats) throws Exception;

    public Player findPlayer(Player player);
    public Player getPlayerByName(String name);
    public boolean hasPlayerByName(String name);

    public boolean isAuthValid (String currentName, IObserver client) throws Exception;
//    public ArrayList<TicketBooth> getAllMTicketBooths();

    public boolean startGame (String currentName) throws Exception;

    public boolean giveGuess(String currentName, String currentGuessedWord) throws Exception;
//    public ArrayList<TicketBooth> getAllMTicketBooths();

    public void logout(Player player, IObserver client) throws Exception;
}
