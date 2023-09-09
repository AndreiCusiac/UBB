package proiect.server;

import proiect.model.Challenge;
import proiect.model.Employee;
import proiect.model.Participant;
import proiect.model.validator.Validator;
import proiect.model.validator.ValidatorException;
import proiect.persistence.employees.EmployeesRepository;
import proiect.persistence.participants.ParticipantsRepository;
import proiect.services.Observer;
import proiect.services.Services;
import proiect.services.ServicesException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServicesImplementation implements Services {
    private ParticipantsRepository participantsRepository;
    private EmployeesRepository employeesRepository;

    private Validator<Participant> participantValidator;
    private Validator<Employee> employeeValidator;

    private Map<Integer, Observer> loggedEmployees;

    private final int defaultThreadsNo = 4;

    public ServicesImplementation(ParticipantsRepository participantsRepository, Validator<Participant> participantValidator, EmployeesRepository employeesRepository, Validator<Employee> employeeValidator) {
        this.participantsRepository = participantsRepository;
        this.participantValidator = participantValidator;
        this.employeesRepository = employeesRepository;
        this.employeeValidator = employeeValidator;

        loggedEmployees = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void logIn(String username, String password, Observer client) throws ServicesException {
        if(username == null || password == null)
            throw new ServicesException("username and password cannot be null");

        Employee employee = employeesRepository.getOneByUsername(username);
        if(loggedEmployees.get(employee.getId()) != null)
            throw new ServicesException("employee already logged in");

        if(!employee.getPassword().equals(password))
            throw new ServicesException("invalid username or password");

        loggedEmployees.put(employee.getId(), client);
    }

    @Override
    public Employee getOneEmployee(String username) throws ServicesException {
        Employee employee = employeesRepository.getOneByUsername(username);

        if(employee == null)
            throw new ServicesException("employee not found");

        return employee;
    }

    @Override
    public void logOut(Employee employee, Observer client) throws ServicesException {
        Observer localEmployee = loggedEmployees.remove(employee.getId());

        if(localEmployee == null)
            throw new ServicesException("employee " + employee.getId() + " is already logged out");
    }

    @Override
    public synchronized void addParticipant(String firstName, String lastName, int age, Challenge challenge1, Challenge challenge2) throws ServicesException {
        if(firstName == null || lastName == null || challenge1 == null)
            throw new ServicesException("first name, last name and first challenge cannot be null");

        Participant participant = new Participant(firstName, lastName, age, challenge1, challenge2);
        try {
            participantValidator.validate(participant);
        } catch (ValidatorException ex) {
            throw new ServicesException(ex.getMessage());
        }
        participantsRepository.add(participant);
        notifyParticipantAdded(participant);
    }

    private void notifyParticipantAdded(Participant participant) throws ServicesException {
        Iterable<Employee> employees = employeesRepository.getAll();
        ExecutorService executorService = Executors.newFixedThreadPool(defaultThreadsNo);

        for(Employee employee : employees) {
            Observer client = loggedEmployees.get(employee.getId());
            if(client != null) {
                executorService.execute(() -> {
                    try {
                        System.out.println("Notifying employee " + employee.getId() + " that participant " + participant.getId() + " was added.");
                        client.participantAdded(participant);
                    } catch (ServicesException ex) {
                        System.out.println("error notifying employee " + ex);
                    }
                });
            }
        }
        executorService.shutdown();
    }

    @Override
    public synchronized List<Participant> getAllParticipants() {
        return participantsRepository.getAll();
    }

    @Override
    public synchronized List<Participant> findAllParticipantsByChallenge(Challenge challenge) throws ServicesException {
        if(challenge == null)
            throw new ServicesException("challenge cannot be null");

        return participantsRepository.findByChallenge(challenge);
    }

    @Override
    public synchronized List<Participant> findAllParticipantsByAgeCategory(int min, int max) throws ServicesException {
        if(min > max)
            throw new ServicesException("the max age must be greater that the min age");

        if(min < 6 || max > 15)
            throw new ServicesException("the min age of a participant is 6 years and the max age is 15");

        return participantsRepository.findByAgeCategory(min, max);
    }

    @Override
    public synchronized List<Participant> findAllParticipantsByAgeCategoryAndChallenge(int min, int max, Challenge challenge) throws ServicesException {
        if(min > max)
            throw new ServicesException("the max age must be greater that the min age");

        if(min < 6 || max > 15)
            throw new ServicesException("the min age of a participant is 6 years and the max age is 15");

        if(challenge == null)
            throw new ServicesException("challenge cannot be null");

        return participantsRepository.findByAgeCategoryAndChallenge(min, max, challenge);
    }
}
