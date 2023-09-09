package org.example.client;

import java.io.IOException;

public class MainClient {

    private static final Integer CLIENT_REQUEST_TIMEOUT = 2000;

    /*public static void main(String[] args) {
        Worker clientWorker = new Worker();
        while (true) {
            try {
                clientWorker.connect();
                Thread.sleep(CLIENT_REQUEST_TIMEOUT);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    public static void main(String[] args) throws Exception {
        //Worker clientWorker = new Worker();
        Proxy serverProxy = new Proxy();
        Client client = new Client(serverProxy);
        client.login();
        while (true) {
            try {
                client.sendResevation();
                Thread.sleep(CLIENT_REQUEST_TIMEOUT);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}