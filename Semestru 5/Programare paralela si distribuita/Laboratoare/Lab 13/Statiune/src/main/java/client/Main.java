package main.java.client;

import java.io.IOException;

public class Main {

    private static final Integer CLIENT_REQUEST_TIMEOUT = 2000;

    public static void main(String[] args) {
        Worker clientWorker = new Worker();
        while (true) {
            try {
                clientWorker.connect();
                Thread.sleep(CLIENT_REQUEST_TIMEOUT);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}