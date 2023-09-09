package main.java.server;
import main.java.common.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Repo {

    private ArrayList<Integer> capacityOfFirstStation;
    private String reservationDate = "18-10-2023";
    private Integer openingHour = 10;
    private Integer closingHour = 18;

    private List<Integer> openHours = new ArrayList<>();

    private int numberOfStations;
    private int numberOfTreatments;
    private List<Integer> costsOfTreatments;
    private List<Integer> durationsOfTreatments;
    private ArrayList<ArrayList<Integer>> capacitiesOfTreatments = new ArrayList<ArrayList<Integer>>();

    private List<Integer> stations = new ArrayList<>();
    private List<Integer> treatments = new ArrayList<>();
    private List<Rezervare> rezervari = new ArrayList<>();
    private List<Plata> plati = new ArrayList<>();
    private int sum = 0;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static LocalDate lastDate = LocalDate.parse("01-01-2023", formatter);
    private static String rezervariFile = "rezervari.txt";
    private static String platiFile = "plati.txt";

    // parametri repository : nr locuri, nr spectacole, pret bilet pt fiecare spectacol, time to sleep intre 2 verificari

    public Repo(String reservationDate, Integer openingHour, Integer closingHour, Integer numberOfStations, Integer numberOfTreatments, List<Integer> costsOfTreatments, List<Integer> durationsOfTreatments, ArrayList<Integer> capacityOfFirstStation) {
        this.reservationDate = reservationDate;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.numberOfStations = numberOfStations;
        this.numberOfTreatments = numberOfTreatments;
        this.costsOfTreatments = costsOfTreatments;
        this.durationsOfTreatments = durationsOfTreatments;
        this.capacityOfFirstStation = capacityOfFirstStation;
        this.openHours.add(openingHour);
        this.openHours.add(closingHour);

        generateStations();
        generateTreatments();
        addCapacities();
    }

    private void addCapacities() {
        capacitiesOfTreatments.add(capacityOfFirstStation);

        for (int i = 2; i <= numberOfStations; i++) {
            ArrayList<Integer> CAPACITY_OF_STATION_I = new ArrayList<>();

            for (int j = 0; j < numberOfTreatments; j++){

                Integer newCapacity = capacityOfFirstStation.get(j) * (i - 1);

                CAPACITY_OF_STATION_I.add(newCapacity);
            }

            capacitiesOfTreatments.add(CAPACITY_OF_STATION_I);
        }
    }

    private void generateStations() {
        for (int i = 1; i <= numberOfStations; i++) {
            stations.add(i);
        }
    }

    private void generateTreatments() {
        for (int i = 1; i <= numberOfTreatments; i++) {
            treatments.add(i);
        }
    }

    private List<Rezervare> getAllRezervariOfStation(int station) {
        return rezervari.stream().filter(rezervare -> rezervare.getTreatment_location() == station).collect(Collectors.toList());
    }

    private List<Rezervare> getAllRezervariByStationAndTreatment(int station, int treatment) {
        List<Rezervare> allByStation = getAllRezervariOfStation(station);

        return allByStation.stream().filter(rezervare -> rezervare.getType() == treatment).collect(Collectors.toList());
    }

    public List<List<Integer>> getReservationInfos() {
        List<List<Integer>> allInfos = new ArrayList<>();
        allInfos.add(stations);
        allInfos.add(treatments);
        allInfos.add(costsOfTreatments);
        allInfos.add(openHours);

        System.out.println(allInfos);

        return allInfos;
    }

    private Integer getEndHour(Integer treatment, Integer startHour, Integer startMinute) {
        Integer indexOfTreatmentCost = treatments.indexOf(treatment);

        Integer durationOfTreatment = durationsOfTreatments.get(indexOfTreatmentCost);

        Integer finishMinute = (startMinute + durationOfTreatment);

        Integer finishHour = (startHour + finishMinute / 60);

        finishMinute = finishMinute % 60;

        return finishHour;
    }

    private Integer getEndMinute(Integer treatment, Integer startHour, Integer startMinute) {
        Integer indexOfTreatmentCost = treatments.indexOf(treatment);

        Integer durationOfTreatment = durationsOfTreatments.get(indexOfTreatmentCost);

        Integer finishMinute = (startMinute + durationOfTreatment);

        Integer finishHour = (startHour + finishMinute / 60);

        finishMinute = finishMinute % 60;

        return finishMinute;
    }

    private boolean isBeforeClose(Integer treatment, Integer startHour, Integer startMinute) {
        Integer indexOfTreatmentCost = treatments.indexOf(treatment);

        Integer durationOfTreatment = durationsOfTreatments.get(indexOfTreatmentCost);

        Integer finishMinute = (startMinute + durationOfTreatment);

        Integer finishHour = (startHour + finishMinute / 60);

        finishMinute = finishMinute % 60;

        if (finishHour >= 18) {
            return false;
        }

        return true;
    }

    private boolean doesTimeIntersectionExist(Integer startReservationHour, Integer startReservationMinute, Integer endReservationHour, Integer endReservationMinute, Integer startHour, Integer startMinute, Integer endHour, Integer endMinute) {

        if ((endHour < endReservationHour) ||
                (Objects.equals(endHour, endReservationHour) && endMinute < endReservationMinute) ||
                (startHour > startReservationHour) ||
                (startHour.equals(startReservationHour) && startMinute > startReservationMinute)) {
            return false;
        }

        return true;
    }

    private boolean isSpotFree(Integer station, Integer treatment, Integer startHour, Integer startMinute) {
        Integer currentCapacityAtGivenStartingHour = 0;
        Integer endHour = getEndHour(treatment, startHour, startMinute);
        Integer endMinute = getEndMinute(treatment, startHour, startMinute);

        for (Rezervare rezervare : rezervari) {
            if (doesTimeIntersectionExist(rezervare.getTreatment_hour_start(), rezervare.getTreatment_minute_start(), rezervare.getTreatment_hour_end(), rezervare.getTreatment_minute_end(), startHour, startMinute, endHour, endMinute)) {
                currentCapacityAtGivenStartingHour++;
            }
        }

        if (isCapacityAtMaximum(station, treatment, currentCapacityAtGivenStartingHour)) {
            return false;
        }

        return true;
    }

    private boolean isCapacityAtMaximum(Integer station, Integer treatment, Integer currentCapacityAtGivenStartingHour) {
        return currentCapacityAtGivenStartingHour >= capacitiesOfTreatments.get(station - 1).get(treatment - 1);
    }

    public synchronized boolean addReservation(Rezervare rezervare) {
        if (isBeforeClose(rezervare.getType(), rezervare.getTreatment_hour_start(), rezervare.getTreatment_minute_start()) &&
            isSpotFree(rezervare.getTreatment_location(), rezervare.getType(), rezervare.getTreatment_hour_start(), rezervare.getTreatment_minute_start())) {

            rezervare.setTreatment_hour_end(getEndHour(rezervare.getType(), rezervare.getTreatment_hour_start(), rezervare.getTreatment_minute_start()));
            rezervare.setTreatment_minute_end(getEndMinute(rezervare.getType(), rezervare.getTreatment_hour_start(), rezervare.getTreatment_minute_start()));

            rezervari.add(rezervare);
            addRezervareFile(rezervare);

            return true;
        }

        return false;
    }

    public synchronized Boolean resolveRequest(Request request) {
        RequestType requestType = request.requestsType();

        if (requestType.equals(RequestType.REZERVARE)) {
            Rezervare rezervare = (Rezervare)request.data();
            return addReservation(rezervare);
        } else if (requestType.equals(RequestType.PLATA)) {
            Plata plata = (Plata) request.data();
            return addPlata(plata);
        } else if (requestType.equals(RequestType.ANULARE)) {
            Rezervare rezervare = (Rezervare)request.data();
            return deleteReservation(rezervare);
        }

        return true;
    }

    public synchronized boolean addPlata(Plata plata) {
        plati.add(plata);

        return true;
    }

    public synchronized boolean deleteReservation(Rezervare rezervare) {
        Integer sumToReturn = costsOfTreatments.get(treatments.indexOf(rezervare.getType()));

        if (rezervari.contains(rezervare)) {
            Plata plataToReturn = new Plata(rezervare.getCnp(), rezervare.getDate(), sumToReturn * (-1), rezervare.getTreatment_location(), rezervare.getType(), rezervare);
            deletePlata(plataToReturn);
        }

        rezervari.remove(rezervare);
        return true;
    }

    public synchronized boolean deletePlata(Plata plata) {
        plati.add(plata);

        return true;
    }

    private List<Rezervare> getRezervariNeplatite(){
        List<Rezervare> rez = new ArrayList<>();
        for (Rezervare re : rezervari){
            for (Plata pla: plati){
                if (pla.getRezervare().equals(re)){
                    rez.add(re);
                    break;
                }
            }
        }
        return rez;
    }

    private List<Rezervare> getRezervariNeplatiteStatiuni(Integer statiune){
        return getRezervariNeplatite().stream().filter(rezervare -> rezervare.getTreatment_location().equals(statiune)).collect(Collectors.toList());
    }

    /*void verify() {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("logs.txt", true)))) {
            printWriter.println(LocalDateTime.now());
            int total_sum = 0;
            for (int i = 0; i < stations.size(); i++) {
                int sold_per_location = 0;
                printWriter.print(stations.get(i));
                for(Plata plata: plati){
                    if (plata.getTreatment_location().equals(stations.get(i))){
                        printWriter.println(plata);
                        sold_per_location += plata.getSum();
                    }
                }
                printWriter.println(sold_per_location);
                printWriter.println();
                total_sum += sold_per_location;
                List<Rezervare> rez = new ArrayList<>();
                rez = getRezervariNeplatiteStatiuni(stations.get(i));
                for(int k = 0; k < rez.size(); k++){
                    rez.get(k).toString();
                }

                for(Integer tratament : treatments) {
                    System.out.println(tratament);
                    Integer capacityOfTreatmentXAtStatiuneZ = capacitiesOfTreatments.get(i).get(treatments.indexOf(tratament));
                    System.out.println(capacityOfTreatmentXAtStatiuneZ);
                    for(Integer start = openingHour; start <= closingHour - 1; start++) {
                        Integer end = start + 1;
                        Integer count = 0;
                        for (Rezervare re : rezervari) {
                            if (re.getTreatment_location().equals(stations.get(i)) && re.getType().equals(tratament) &&
                                doesTimeIntersectionExist(start, 0, end, 0, re.getTreatment_hour_start(), re.getTreatment_minute_start(), re.getTreatment_hour_end(), re.getTreatment_minute_end())) {
                                count++;
                            }
                        }
                        System.out.println("Ora start tratament: " + start + "\nOra sfarsit tratament: " + end + "\nNumar rezervari: "+ count);
                    }
                }


            }
            printWriter.println("SOLD TOTAL SISTEM : " + sum + "  SOLD TOTAL CALCULAT : " + total_sum);
            if (sum == total_sum)
                printWriter.println("CORECT\n");
            else printWriter.println("INCORECT\n");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }*/

    void addRezervareFile(Rezervare rezervare) {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(rezervariFile, true)))) {
            printWriter.print(rezervare);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    synchronized void verify() {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("logs.txt", true)))) {
            printWriter.println(LocalDateTime.now());
            int total_sum = 0;
            for (int i = 0; i < stations.size(); i++) {
                int sold_per_location = 0;
                printWriter.println("LOCATIA: " + stations.get(i));
                for(Plata plata: plati){
                    if (plata.getTreatment_location().equals(stations.get(i))){
                        printWriter.println("Plata " + plata);
                        sold_per_location += plata.getSum();
                    }
                }
                printWriter.println("Sold per locatie: " + sold_per_location);
                printWriter.println();
                total_sum += sold_per_location;
                List<Rezervare> rez = new ArrayList<>();
                rez = getRezervariNeplatiteStatiuni(stations.get(i));
                for(int k = 0; k < rez.size(); k++){
                    printWriter.println("Rezervare neplatita: " + rez.get(k).toString());
                }

                for(Integer tratament : treatments) {
                    System.out.println("Tratament: " + tratament);
                    printWriter.println("Tratament: " + tratament);
                    Integer capacityOfTreatmentXAtStatiuneZ = capacitiesOfTreatments.get(i).get(treatments.indexOf(tratament));
//                    Integer capacityOfTreatmentXAtStatiuneZ = capacitiesOfTreatments.get(stations.get(i)-1).get(treatments.indexOf(tratament)-1);
                    System.out.println(capacityOfTreatmentXAtStatiuneZ);
                    printWriter.println("Capacity of treatment: " + capacityOfTreatmentXAtStatiuneZ);
                    printWriter.println();
                    for(Integer start = openingHour; start <= closingHour - 1; start++) {
                        Integer end = start + 1;
                        Integer count = 0;
                        for (Rezervare re : rezervari) {
                            if (re.getTreatment_location().equals(stations.get(i)) && re.getType().equals(tratament) &&
                                    doesTimeIntersectionExist(start, 0, end, 0, re.getTreatment_hour_start(), re.getTreatment_minute_start(), re.getTreatment_hour_end(), re.getTreatment_minute_end())) {
                                count++;
                            }
                        }
                        System.out.print("Ora start tratament: " + start + "Ora sfarsit tratament: " + end + "Numar rezervari: "+ count+"\n");
                        printWriter.println("Ora start tratament: " + start + "\nOra sfarsit tratament: " + end + "\nNumar rezervari: "+ count+"\n");
                    }
                }


            }
            printWriter.println("SOLD TOTAL SISTEM : " + sum + "  SOLD TOTAL CALCULAT : " + total_sum);
            if (sum == total_sum)
                printWriter.println("CORECT\n");
            else printWriter.println("INCORECT\n");
            printWriter.println();
            printWriter.println("------------------------------------------------------------");
            printWriter.println();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void writeToFile(Object o, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
        writer.write(o.toString() + "\n");
        writer.close();
    }


}
