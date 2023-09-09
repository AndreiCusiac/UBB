package org.example.client;

import org.example.common.Plata;
import org.example.common.Rezervare;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Client {
    private Proxy serverProxy;
    private Random random = new Random();
    private List<ArrayList<Integer>> liste = new ArrayList<ArrayList<Integer>>();
    private List<Integer> treatment_locations = new ArrayList<>();
    private List<Integer> typesOfTreatments = new ArrayList<>();
    private List<Integer> sume = new ArrayList<>();
    private List<Integer> OreDeFunctionare = new ArrayList<>();

    Client(Proxy serverProxy) {
        this.serverProxy = serverProxy;
    }

    private void getInfo() throws Exception {
        System.out.println("Waiting for server info.");
        liste = serverProxy.getInfo();
        //liste = Collections.unmodifiableList((List<ArrayList<Integer>>) inputStream.readObject());
        System.out.println("received " + liste.size() + " elements");
        treatment_locations = liste.get(0);
        typesOfTreatments = liste.get(1);
        sume = liste.get(2);
        OreDeFunctionare = liste.get(3);
    }

    public void login() throws Exception {
        serverProxy.login();
    }

    private Rezervare createRandomReservation() {
        System.out.println("Am ajuns in createRandomReservation");
        String cnp = generateCNP();
        String name = generateName();
        Integer treatment_location = getRandom(treatment_locations);
        Integer type = getRandom(typesOfTreatments);

        System.out.println("Am generat random");

        Integer openingHour = OreDeFunctionare.get(0);
        Integer closingHour = OreDeFunctionare.get(1);
        Integer treatment_hour_start = randInt(openingHour, closingHour);
        Integer treatment_minute_start = randInt(0, 60);
        Boolean anulare = false;
        Rezervare rezervare = new Rezervare(cnp, name, LocalDate.now(), treatment_location, type, "18-10-2023", treatment_hour_start, treatment_minute_start, 0, 0, anulare);

        return rezervare;
    }

    public void sendResevation() throws Exception {
        getInfo();
        Rezervare newRezervare = createRandomReservation();

        System.out.println("Rezervare random creata: " + newRezervare);

        Boolean okRes = serverProxy.sendReservation(newRezervare);

        if (okRes) {
            System.out.println("Rezervare acceptata.");

            Plata newPlata = createNewPlata(newRezervare);

            System.out.println("Plata creata: " + newPlata);

            Boolean okPlata = serverProxy.sendPlata(newPlata);

            if (okPlata) {
                System.out.println("Plata acceptata.");

                Boolean anulez = random.nextBoolean();

                if (anulez) {
                    newRezervare.setAnulare(anulez);
                    Boolean okAnulare = serverProxy.sendAnulation(newRezervare);

                    if (okAnulare) {
                        System.out.println("Anulare acceptata.");
                    } else {
                        System.out.println("Anulare neacceptata.");
                    }
                }

            } else {
                System.out.println("Plata neacceptata.");
            }
        } else {
            System.out.println("Rezervare neacceptata.");
        }

        serverProxy.logout();
    }

    private Plata createNewPlata(Rezervare newRezervare) {
        Integer suma = sume.get(getIndex(typesOfTreatments, newRezervare.getType()));

        Plata plata = new Plata(newRezervare.getCnp(), LocalDate.now(), suma, newRezervare.getTreatment_location(), newRezervare.getType(), newRezervare);

        return plata;
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
}
