package org.example.server;

import org.example.common.Request;
import org.example.common.Response;
import org.example.utils.AbstractParallelServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RpcServer extends AbstractParallelServer {

    private Repo server;
    private Integer noSecondsIdle;
    private Integer verifyTimeout; // serverul verifica integritatea datelor din 5 in 5 secunde

    private ExecutorService threadPool;

    public RpcServer(int port, int noThreads, Repo repo, Integer noSecondsIdle, Integer verifyTimeout) {
        super(port);
        threadPool = Executors.newFixedThreadPool(noThreads);
        this.server = repo;
        this.noSecondsIdle = noSecondsIdle;
        this.verifyTimeout = verifyTimeout;
        System.out.println("RpcConcurrentServer");
        threadPool.execute(new Verifier(repo, verifyTimeout));
    }

    protected void processRequest(Socket client) {
        Future<Response> future = threadPool.submit(new MyCallable(client));
        Thread thread = new Thread(new MyWorker(future, client));
        thread.setDaemon(false);
        thread.start();
    }

    private class MyCallable implements Callable<Response> {
        private ObjectInputStream inputStream;

        public MyCallable(Socket client) {
            try {
                inputStream = new ObjectInputStream(client.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public Response call() throws Exception {
            Request request = (Request) inputStream.readObject();
            System.out.println("Request to call: " + request);
            return server.resolveRequest(request);
        }
    }

    @Override
    public void stop() throws Exception {
//        super.stop();
        System.out.println("Stopping services.");
    }
}
