import proiect.model.Employee;
import proiect.model.Participant;
import proiect.model.validator.Validator;
import proiect.model.validator.ValidatorEmployee;
import proiect.model.validator.ValidatorParticipant;
import proiect.network.utils.AbstractServer;
import proiect.network.utils.ProiectConcurrentServer;
import proiect.network.utils.ServerException;
import proiect.persistence.employees.EmployeesDBRepository;
import proiect.persistence.employees.EmployeesRepository;
import proiect.persistence.participants.ParticipantsDBRepository;
import proiect.persistence.participants.ParticipantsRepository;
import proiect.server.ServicesImplementation;
import proiect.services.Services;

import java.io.IOException;
import java.util.Properties;

public class StartServer {
    private static int defaultPort = 55555;

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(StartServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server prop set");
            properties.list(System.out);
        } catch (IOException ex) {
            System.out.println("Cannot find server.properties");
            return;
        }

        ParticipantsRepository participantsRepository = new ParticipantsDBRepository(properties);
        Validator<Participant> participantValidator = new ValidatorParticipant();

        EmployeesRepository employeesRepository = new EmployeesDBRepository(properties);
        Validator<Employee> employeeValidator = new ValidatorEmployee();

        Services services = new ServicesImplementation(participantsRepository, participantValidator, employeesRepository, employeeValidator);
        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(properties.getProperty("proiect.server.port"));
        } catch (NumberFormatException ex) {
            System.out.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port " + defaultPort);
        }

        System.out.println("Starting server on port: " + serverPort);
        AbstractServer server = new ProiectConcurrentServer(serverPort, services);
        try {
            server.start();
        } catch (ServerException ex) {
            System.err.println("Error starting server: " + ex.getMessage());
        } finally {
            try {
                server.stop();
            } catch (ServerException ex) {
                System.err.println("Error stopping server: " + ex.getMessage());
            }
        }
    }
}