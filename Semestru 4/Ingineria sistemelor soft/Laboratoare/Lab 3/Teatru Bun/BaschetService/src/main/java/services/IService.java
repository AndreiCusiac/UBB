package services;

import models.Match;
import models.TicketBooth;

import java.util.ArrayList;

public interface IService {
    public ArrayList<Match> getAllMatches() throws Exception;
    public ArrayList<Match> getMatchesSortedByAvailableSeatsDesc() throws Exception;

    public void sellTicket(Match match, String name, Integer seats) throws Exception;

    public TicketBooth findTicketBooth(TicketBooth ticketBooth);
    public TicketBooth getTicketBoothByName (String name);
    public boolean hasTicketBoothByName (String name);

    public boolean isAuthValid (String currentName, String currentPassword, IObserver client) throws Exception;
//    public ArrayList<TicketBooth> getAllMTicketBooths();

    public void logout(TicketBooth ticketBooth, IObserver client) throws Exception;
}
