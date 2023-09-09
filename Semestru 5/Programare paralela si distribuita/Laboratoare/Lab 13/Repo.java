package server;
import common.*;

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

    private String reservationDate = "18-10-2023";
    private Integer openingHour = 10;
    private Integer closingHour = 18;

    private List<Integer> openHours = new ArrayList<>();

    private int numberOfStations= 3;
    private int numberOfTreatments = 5;
    private List<Integer> costsOfTreatments = new ArrayList<>();
    private List<Integer> durationsOfTreatments = new ArrayList<>();
    private List<List<Integer>> capacitiesOfTreatments = new ArrayList<>();

    private List<Integer> stations = new ArrayList<>();
    private List<Integer> treatments = new ArrayList<>();
    private List<Rezervare> rezervari = new ArrayList<>();
    private List<Plata> plati = new ArrayList<>();
    private int sum = 0;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static LocalDate lastDate = LocalDate.parse("01-01-2023", formatter);
    private static String salesFile = "sales.txt";
    private static String showsFile = "shows.txt";
    private static String ticketsFile = "tickets.txt";

    // parametri repository : nr locuri, nr spectacole, pret bilet pt fiecare spectacol, time to sleep intre 2 verificari
    public Repo (String reservationDate, int nrSeats, int noShows, Integer numberOfStations, Integer numberOfTreatments, ArrayList<Integer> prices, ArrayList<Integer> durationsOfTreatments, ArrayList<ArrayList<Integer>> capacitiesOfStations) {
        generateShows(noShows, prices);
        generateTickets(nrSeats);
    }

    public Repo(String reservationDate, Integer openingHour, Integer closingHour, Integer numberOfStations, Integer numberOfTreatments, List<Integer> costsOfTreatments, List<Integer> durationsOfTreatments, List<List<Integer>> capacitiesOfTreatments) {
        this.reservationDate = reservationDate;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.numberOfStations = numberOfStations;
        this.numberOfTreatments = numberOfTreatments;
        this.costsOfTreatments = costsOfTreatments;
        this.durationsOfTreatments = durationsOfTreatments;
        this.capacitiesOfTreatments = capacitiesOfTreatments;
        this.openHours.add(openingHour);
        this.openHours.add(closingHour);

        generateStations();
        generateTreatments();
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

    private void generateShows(int noShows, ArrayList<Integer> prices) {
        Random r = new Random();
        try {
            for (int i = 0; i < noShows; ++i) {
                String uniqueID = UUID.randomUUID().toString();
                int price = prices.get(i);
                Show show = new Show(uniqueID, lastDate.plusDays(1), "Show" + uniqueID, price, 0);
                shows.add(show);
                writeToFile(show, showsFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateTickets(int noSeats) {
        shows.forEach(each -> {
            for (int i = 0; i < noSeats; i++) {
                Ticket ticket = new Ticket(each.getId(), i);
                remainingTickets.add(ticket);
                try {
                    writeToFile(ticket, ticketsFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<List<Integer>> getReservationInfos() {
        List<List<Integer>> allInfos = new ArrayList<>();
        allInfos.add(stations);
        allInfos.add(treatments);
        allInfos.add(costsOfTreatments);
        allInfos.add(openHours);

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
        return currentCapacityAtGivenStartingHour >= capacitiesOfTreatments.get(station).get(treatment);
    }

    public synchronized boolean addReservation(Rezervare rezervare) {
        if (isBeforeClose(rezervare.getType(), rezervare.getTreatment_hour_start(), rezervare.getTreatment_minute_start()) &&
            isSpotFree(rezervare.getTreatment_location(), rezervare.getType(), rezervare.getTreatment_hour_start(), rezervare.getTreatment_minute_start())) {

            rezervare.setTreatment_hour_end(getEndHour(rezervare.getTreatment_hour_start(), rezervare.getTreatment_hour_start(), rezervare.getTreatment_minute_start()));
            rezervare.setTreatment_minute_end(getEndMinute(rezervare.getTreatment_hour_start(), rezervare.getTreatment_hour_start(), rezervare.getTreatment_minute_start()));

            rezervari.add(rezervare);

            return true;
        }

        return false;
    }

    public synchronized boolean addPlata(Plata plata) {
        plati.add(plata);

        return true;
    }

    public synchronized boolean deleteReservation(Rezervare rezervare) {
        Integer sumToReturn = costsOfTreatments.get(treatments.indexOf(rezervare.getType()));

        if (rezervari.contains(rezervare)) {
            Plata plataToReturn = new Plata(rezervare.getCnp(), rezervare.getDate(), sumToReturn * (-1), rezervare.getTreatment_location(), rezervare.getType());
            deletePlata(plataToReturn);
        }

        rezervari.remove(rezervare);
        return true;
    }

    public synchronized boolean deletePlata(Plata plata) {
        plati.add(plata);

        return true;
    }

    public synchronized List<Ticket> buyTicket(String showId, List<Integer> seats) throws IOException {
        int noSeats = seats.size();
        List<Ticket> ticketsAvailableForShow = remainingTickets
                .stream()
                .filter(each -> each.getShowId().equals(showId))
                .collect(Collectors.toList());
        if (ticketsAvailableForShow.size() < noSeats)   // nu am destule locuri
            return null;
        // am destule locuri
        List<Ticket> forClient = new ArrayList<>();
        for (int each : seats) {
            // verific daca sunt disponibile toate
            Ticket t = ticketsAvailableForShow.stream().filter(x -> x.getSeat() == each).findFirst().orElse(null);
            if (t == null)
                return null;
        }
        for (int each : seats) {
            // sunt disponibile; actualizez remainingTickets si soldTickets
            Ticket t = ticketsAvailableForShow.stream().filter(x -> x.getSeat() == each).findFirst().orElse(null);
            remainingTickets.remove(t);
            soldTickets.add(t);
            forClient.add(t);
        }
        // adaug o vanzare
        Show show = shows.stream().filter(each -> each.getId().equals(showId)).findFirst().get();
        Sale sale = new Sale(showId, show.getDate(), noSeats, show.getPrice() * noSeats);
        sales.add(sale);
        // adaug vanzarea si in fisier
        writeToFile(sale, salesFile);
        // actualizez soldul
        sum += show.getPrice() * noSeats;
        return forClient;
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

    void verify() {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("logs.txt", true)))) {
            printWriter.println(LocalDateTime.now());
            int total_sum = 0;
            for (int i = 0; i <= stations.size(); i++) {
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
                for(int k = 0; k <= rez.size(); k++){
                    printWriter.print(rez.get(k).toString());
                }

                for(Integer tratament : treatments) {
                    System.out.println(tratament);
                    printWriter.println(tratament);
                    Integer capacityOfTreatmentXAtStatiuneZ = capacitiesOfTreatments.get(stations.get(i)).get(treatments.indexOf(tratament));
                    System.out.println(capacityOfTreatmentXAtStatiuneZ);
                    printWriter.println(capacityOfTreatmentXAtStatiuneZ);
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
                        printWriter.print("Ora start tratament: " + start + "Ora sfarsit tratament: " + end + "Numar rezervari: "+ count+"\n");
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
    }

    public void writeToFile(Object o, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
        writer.write(o.toString() + "\n");
        writer.close();
    }
}
