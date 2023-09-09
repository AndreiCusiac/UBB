package org.example.server;

import org.example.common.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Future;

import static org.example.server.MyServer.stream;

public class MyWorker implements Runnable {
    private Socket client;
    private DataOutputStream outputStream;
    private Future<Response> future;

    public MyWorker(Future<Response> future, Socket client) {
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
            Response response = future.get();
            //boolean responseMessage = response;
            System.out.println("Responseto send: " + response);
            stream.writeObject(response);
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