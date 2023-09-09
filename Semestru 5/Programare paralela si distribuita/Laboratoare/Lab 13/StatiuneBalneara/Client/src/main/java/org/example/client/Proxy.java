package org.example.client;

import org.example.Main;
import org.example.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Proxy {
    private static String host = "localhost";
    private static int port = 4000;

//    private IObserver client;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    
    public Proxy() throws Exception {
        qresponses=new LinkedBlockingQueue<Response>();
    }

    private Response readResponse() {
        Response response=null;
        try{
            response = qresponses.take();
            System.out.println("Response received successfully.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void sendRequest(Request request) throws Exception {
        try {
            outputStream.writeObject(request);
            System.out.println("Written new request " + request);
            outputStream.flush();
            System.out.println(("OutputStream flush, request sent."));
        } catch (IOException e) {
            throw new Exception("Error sending object "+e);
        }
    }

    private void closeConnection() {
        finished=true;

        try {
            inputStream.close();
            outputStream.close();
            connection.close();
            //client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeConnection() throws Exception {
        try {
            connection=new Socket(host,port);
            System.out.println("New connection");
            outputStream=new ObjectOutputStream(connection.getOutputStream());
            System.out.println("New ObjOutStream");
            outputStream.flush();
            System.out.println("OutStream flush");
            inputStream=new ObjectInputStream(connection.getInputStream());
            System.out.println("New ObjInStream");
            finished=false;
            System.out.println("Finished = false");
//            outputStream.flush();
            startReader();
            System.out.println("Un nou thread a inceput.");
        } catch (IOException e) {
            System.out.println("Initializare conexiune failed:(");
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    private void startReader() {
        Thread tw=new Thread(new ReaderThread());
        System.out.println("Un nou thread va incepe");
        tw.start();
    }

    public List<ArrayList<Integer>> getInfo() throws Exception {
        Request req = new Request.Builder().type(RequestType.GET_INFO).data().build();
        sendRequest(req);
        Response response = readResponse();
        if (response.responseType() == ResponseType.BAD){
            String err = response.data().toString();
            throw new Exception(err);
        }

        return (List<ArrayList<Integer>>) response.data();
    }

    public boolean sendReservation(Rezervare newRezervare) throws Exception {
        Request req = new Request.Builder().type(RequestType.REZERVARE).data(newRezervare).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.responseType() == ResponseType.OK){
            return true;
        } else if (response.responseType() == ResponseType.BAD) {
            return false;
        } else {
            throw new Exception("Exception at sendReservation. Unknown response");
        }
    }

    public Boolean sendPlata(Plata newPlata) throws Exception {
        Request requestPlata = new Request.Builder().type(RequestType.PLATA).data(newPlata).build();
        sendRequest(requestPlata);
        Response response = readResponse();
        if (response.responseType() == ResponseType.OK){
            return true;
        } else if (response.responseType() == ResponseType.BAD) {
            return false;
        } else {
            throw new Exception("Exception at sendReservation. Unknown response");
        }
    }

    public Boolean sendAnulation(Rezervare newRezervare) throws Exception {
        Request requestAnulare = new Request.Builder().type(RequestType.ANULARE).data(newRezervare).build();
        sendRequest(requestAnulare);
        Response response = readResponse();
        if (response.responseType() == ResponseType.OK){
            return true;
        } else if (response.responseType() == ResponseType.BAD) {
            return false;
        } else {
            throw new Exception("Exception at sendReservation. Unknown response");
        }
    }

    public void login() throws Exception {
        initializeConnection();
    }

    public void logout() {
        closeConnection();
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response = inputStream.readObject();
                    System.out.println("response received "+response);
                    try {
                        System.out.println("Putting response to q: " + response );
                        qresponses.put((Response)response);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
