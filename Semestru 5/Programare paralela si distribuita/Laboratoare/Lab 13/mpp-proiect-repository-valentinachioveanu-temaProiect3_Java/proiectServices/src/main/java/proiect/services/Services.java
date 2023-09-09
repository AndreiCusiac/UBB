package proiect.services;

import proiect.model.Challenge;
import proiect.model.Employee;
import proiect.model.Participant;

import java.util.List;

public interface Services {
    void logIn(String username, String password, Observer client) throws ServicesException;
    Employee getOneEmployee(String username) throws ServicesException;
    void logOut(Employee employee, Observer client) throws ServicesException;

    void addParticipant(String firstName, String lastName, int age, Challenge challenge1, Challenge challenge2) throws ServicesException;
    List<Participant> getAllParticipants() throws ServicesException;

    List<Participant> findAllParticipantsByChallenge(Challenge challenge) throws ServicesException;
    List<Participant> findAllParticipantsByAgeCategory(int min, int max) throws ServicesException;
    List<Participant> findAllParticipantsByAgeCategoryAndChallenge(int min, int max, Challenge challenge) throws ServicesException;
}
