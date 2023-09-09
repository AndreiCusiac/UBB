package org.example.utils;

import java.net.Socket;

public abstract class AbstractConcurrentServer extends AbstractServer{

    public AbstractConcurrentServer(int port) {
        super(port);
        System.out.println("Concurrent AbstractServer");
    }

    protected void processRequest(Socket client) {
        Thread thread = createWorker(client);
        thread.start();
    }

    protected abstract Thread createWorker(Socket client);
}
