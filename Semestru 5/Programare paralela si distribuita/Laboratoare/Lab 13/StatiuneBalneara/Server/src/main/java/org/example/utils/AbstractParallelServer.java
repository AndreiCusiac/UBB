package org.example.utils;

import java.net.Socket;

public abstract class AbstractParallelServer extends AbstractServer{

    public AbstractParallelServer(int port) {
        super(port);
        System.out.println("Parallel AbstractServer");
    }

    /*protected void processRequest(Socket client) {
        Thread thread = createWorker(client);
        thread.start();
    }*/

    protected abstract void processRequest(Socket client);
}
