package main.java.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Future;

import static main.java.server.MyServer.stream;

public class MyWorker implements Runnable {
    private Socket client;
    private DataOutputStream outputStream;
    private Future<Boolean> future;

    public MyWorker(Future<Boolean> future, Socket client) {
        this.future = future;
        this.client = client;
        try {
            outputStream = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Boolean response = future.get();
            boolean responseMessage = response;
            System.out.println("ResponseMessage to send: " + responseMessage);
            stream.writeBoolean(responseMessage);
            stream.reset();
            client.close();
        } catch (Exception e) {
            try {
                client.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}