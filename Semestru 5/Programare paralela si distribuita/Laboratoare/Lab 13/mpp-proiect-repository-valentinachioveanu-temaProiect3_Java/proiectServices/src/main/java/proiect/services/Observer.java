package proiect.services;

import proiect.model.Participant;

public interface Observer {
    void participantAdded(Participant participant) throws ServicesException;
}
