package org.example.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private int port;
    private ServerSocket serverSocket = null;

    public AbstractServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        try {
            serverSocket = new ServerSocket(port);

            System.out.println("S-a creat un nou ServerSocket.");

            while (true) {
                System.out.println("Waiting for clients... ");

                Socket client = serverSocket.accept();

                System.out.println("Client connected.");

                processRequest(client);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            System.out.println("Serverul se va opri.");
            stop();
        }
    }

    protected abstract void processRequest(Socket client);

    public void stop() throws Exception {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

}
