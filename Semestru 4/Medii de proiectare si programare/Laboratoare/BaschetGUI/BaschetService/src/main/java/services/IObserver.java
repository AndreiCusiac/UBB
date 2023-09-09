package services;

import models.Match;
import models.Ticket;

import java.util.ArrayList;

public interface IObserver {
    void ticketSold() throws Exception;
    void ticketSold(ArrayList<Match> matches) throws Exception;
}
