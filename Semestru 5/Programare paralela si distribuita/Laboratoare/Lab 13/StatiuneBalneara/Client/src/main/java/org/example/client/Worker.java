package org.example.client;

import org.example.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker {
    private static final String host = "localhost";
    private static final int port = 4000;
    private Socket connection;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Random random = new Random();
    private List<ArrayList<Integer>> liste = new ArrayList<ArrayList<Integer>>();
    private List<Integer> treatment_locations = new ArrayList<>();
    private List<Integer> typesOfTreatments = new ArrayList<>();
    private List<Integer> sume = new ArrayList<>();
    private List<Integer> OreDeFunctionare = new ArrayList<>();


    public void connect() throws IOException {
        try {
            System.out.println("Host: " + host + " Port: " + port);
            connection = new Socket(host, port);
            System.out.println("Host: " + host + " Port: " + port);
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            System.out.println("Host: " + host + " Port: " + port);
            inputStream = new ObjectInputStream(connection.getInputStream());

            System.out.println("Init client ok");

            Request requestInfo = new Request.Builder().type(RequestType.GET_INFO).data().build();
            outputStream.writeObject(requestInfo);
            outputStream.flush();
            Response response = (Response) inputStream.readObject();

            System.out.println("responseInfo: " + response);
            liste = (List<ArrayList<Integer>>) response.data();
            //liste = Collections.unmodifiableList((List<ArrayList<Integer>>) inputStream.readObject());
            System.out.println("received " + liste.size() + " elements");
            treatment_locations = liste.get(0);
            typesOfTreatments = liste.get(1);
            sume = liste.get(2);
            OreDeFunctionare = liste.get(3);
            send();
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

    private static Integer randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min)) + min;
    }

    public static int getIndex(List<Integer> list, Integer type){
        int index = list.indexOf(type);
        return index;
    }
    private void send() {
        try {
            System.out.println("Am ajuns in send");
            String cnp = generateCNP();
            String name = generateName();
            Integer treatment_location = getRandom(treatment_locations);
            Integer type = getRandom(typesOfTreatments);

            System.out.println("Am generat random");

            Integer openingHour = OreDeFunctionare.get(0);
            Integer closingHour = OreDeFunctionare.get(1);
            Integer suma = sume.get(getIndex(typesOfTreatments, type));
            Integer treatment_hour_start = randInt(openingHour, closingHour);
            Integer treatment_minute_start = randInt(0, 60);
            Boolean anulare = false;
            Rezervare rezervare = new Rezervare(cnp, name, LocalDate.now(), treatment_location, type, "18-10-2023", treatment_hour_start, treatment_minute_start, 0, 0, anulare);
            System.out.println("Se trimite rezervare la server: " + rezervare);

            Request request = new Request.Builder().type(RequestType.REZERVARE).data(rezervare).build();

            System.out.println("Se trimite requestul: " + request);
            outputStream.writeObject(request);
            boolean ok = inputStream.readBoolean();
            String response = ok ? "Rezervare primita" : "Rezervare respinsa";
            System.out.println(response + "\n");
            if (ok) {
                Plata plata = new Plata(cnp, LocalDate.now(), suma, treatment_location, type, rezervare);
                Request requestPlata = new Request.Builder().type(RequestType.PLATA).data(plata).build();

                System.out.println("Se trimite requestul: " + requestPlata);
                outputStream.writeObject(requestPlata);
                boolean ok1 = inputStream.readBoolean();
                String raspuns = ok1 ? "Plata primita" : "Plata respinsa";
                System.out.println(raspuns + "\n");
                if (ok1){
                    anulare = random.nextBoolean();
                    if (anulare){
                        rezervare.setAnulare(anulare);
                        Request requestAnulare = new Request.Builder().type(RequestType.ANULARE).data(rezervare).build();

                        System.out.println("Se trimite requestul: " + requestAnulare);
                        outputStream.writeObject(requestAnulare);
                        boolean ok2 = inputStream.readBoolean();
                        String response2 = ok2 ? "Returnare suma reusita" : "Returnare suma respinsa";
                        System.out.println(response2 + "\n");
                    }
                }
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
