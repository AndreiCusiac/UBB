package client;
import common.Plata;
import common.Reservation;
import common.Rezervare;
import java.lang.Math.*;
import common.Ticket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Worker {
    private static final String host = "localhost";
    private static final int port = 4000;
    private Socket connection;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Random random = new Random();
    private List<ArrayList> Available = new ArrayList<>();
    private List<Integer> treatment_locations = Arrays.asList(1, 2, 3);
    private List<Integer> typesOfTreatments = Arrays.asList(1, 2, 3, 4, 5);

    @SuppressWarnings("unchecked")
    public void connect() throws IOException {
        try {
            connection = new Socket(host, port);
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            inputStream = new ObjectInputStream(connection.getInputStream());
            Available = Collections.unmodifiableList((ArrayList<ArrayList>) inputStream.readObject());
            System.out.println("received " + Available.size() + " elements");
            sendSales();
        } catch (SocketException e) {
            System.out.println("server is shut down");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null && connection.isConnected())
                connection.close();
        }
    }

    private static String generateCNP(){
        String AlphaNumericString = "0123456789";
        StringBuilder sb = new StringBuilder(13);
        for (int i = 0; i < 13; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    private static String generateName(){
        String AlphaNumericString = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    private Integer getRandom(List<Integer> list){
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public static Integer randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    private void sendSales() {
        try {
            String cnp = generateCNP();
            String name = generateName();
            Integer treatment_location = getRandom(treatment_locations);
            Integer type = getRandom(typesOfTreatments);
            Integer treatment_hour_start = randInt(10, 18);
            Integer treatment_minute_start = randInt(0, 60);
            Rezervare rezervare = new Rezervare(cnp, name, LocalDate.now(), treatment_location, type, "18-10-2023", treatment_hour_start, treatment_minute_start, 0, 0);
            System.out.println("Se trimite rezervare la server: " + rezervare.toString());
            outputStream.writeObject(rezervare);
            boolean ok = inputStream.readBoolean();
            String response = ok ? "Rezervare primita" : "Rezervare respinsa";
            System.out.println(response + "\n");
            if (ok) {
                Plata plata = new Plata(cnp, LocalDate.now(), suma);
                outputStream.writeObject(plata);
                boolean ok1 = inputStream.readBoolean();
                String raspuns = ok1 ? "Plata primita" : "Plata respinsa";
                System.out.println(raspuns + "\n");
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            connection.close();
        } catch (IllegalArgumentException e) {
            System.out.println("Nu sunt locuri disponibile!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
