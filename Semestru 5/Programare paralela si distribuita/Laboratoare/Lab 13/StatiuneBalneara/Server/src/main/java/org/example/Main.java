package org.example;
import org.example.server.Repo;
import org.example.server.RpcServer;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static Integer NUMBER_OF_THREADS = 10;
    private static final String RESERVATION_DATE = "15-01-2023";
    private static final Integer OPENING_HOUR = 10;
    private static final Integer CLOSING_HOUR = 18;

    private static final Integer VERIFY_TIMEOUT = 10000; // serverul verifica integritatea datelor din 5 in 5 secunde
    private static final Integer SERVER_RUNTIME = 180000; // serverul merge 3 minute
    private static final Integer NUMBER_OF_STATIONS = 3;
    private static final Integer NUMBER_OF_TREATMENTS = 5;
    private static final ArrayList<Integer> COSTS_OF_TREATMENTS = new ArrayList<>(Arrays.asList(50, 20, 40, 100, 30));
    private static final ArrayList<Integer> DURATIONS_OF_TREATMENTS = new ArrayList<>(Arrays.asList(120, 20, 30, 60, 30));
    private static final ArrayList<Integer> CAPACITY_OF_FIRST_STATION = new ArrayList<>(Arrays.asList(3, 1, 1, 2, 1));
    private static ArrayList<ArrayList<Integer>> CAPACITIES_OF_STATIONS = new ArrayList<>();
    private static Integer port = 4000;

    public static void main(String[] args) {

        Repo repo = new Repo(RESERVATION_DATE, OPENING_HOUR, CLOSING_HOUR, NUMBER_OF_STATIONS, NUMBER_OF_TREATMENTS, COSTS_OF_TREATMENTS, DURATIONS_OF_TREATMENTS, CAPACITY_OF_FIRST_STATION);
        //MyServer showMyServer = new MyServer(port, NUMBER_OF_THREADS, repo, 2, VERIFY_TIMEOUT, SERVER_RUNTIME);   // port, nrThreads, repo, time to sleep intre 2 verificari
        //showMyServer.start();
        RpcServer rpcServer = new RpcServer(port, NUMBER_OF_THREADS,repo, 2, VERIFY_TIMEOUT);

        try {
            rpcServer.start();
        } catch (Exception e) {
            System.err.println("Error starting the server" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                rpcServer.stop();
            } catch (Exception e) {
                System.err.println("Error stopping server "+e.getMessage());
                e.printStackTrace();
            }
        }
    }
}