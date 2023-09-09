package org.example.server;

import org.example.common.Request;
import org.example.common.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.*;

public class MyServer {

    private int port;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;
    private Repo repo;
    private int noSecondsIdle;
    public static ObjectOutputStream stream;

    private Integer verifyTimeout; // serverul verifica integritatea datelor din 5 in 5 secunde
    private  Integer serverRuntime; // serverul merge 3 minute

    private long startTime;

    public MyServer(int port, int noThreads, Repo repo, int noSecondsIdle, Integer verifyTimeout, Integer serverRuntime) {
        this.port = port;
        this.repo = repo;
        this.noSecondsIdle = noSecondsIdle;
        threadPool = Executors.newFixedThreadPool(noThreads);
        this.verifyTimeout = verifyTimeout;
        this.serverRuntime = serverRuntime;
    }

    public void start() {
        startTime = System.nanoTime();

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server has started...");
            threadPool.execute(new Verifier(repo, verifyTimeout));
            for (List list : repo.getReservationInfos()) {
                for (Object el : list) {
                    //System.out.println(el);
                }
            }
            while (true) {
                if (1 == 0)
                    break;
                System.out.println("Waiting for clients...");
                Socket client = serverSocket.accept();
                System.out.println("Client connected...");
//                stream = new ObjectOutputStream(client.getOutputStream());
//                Request request = new Request.Builder().type(RequestType.GET_INFO).data(repo.getReservationInfos()).build();
//                System.out.println("To send to client: " + request);
//                stream.writeObject(request);
//                stream.reset();
//                System.out.println("Sent locations info to client...");
                processRequest(client);
            }
            stream.flush();
            stream.close();
            threadPool.awaitTermination(noSecondsIdle, TimeUnit.SECONDS);
            serverSocket.close();
            System.out.println("Shutting down server...");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processRequest(Socket client) {
        Future<Response> future = threadPool.submit(new MyCallable(client));
        Thread thread = new Thread(new MyWorker(future, client));
        thread.setDaemon(false);
        thread.start();
    }

    private class MyCallable implements Callable<Response>{
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
            return repo.resolveRequest(request);
        }
    }
}
