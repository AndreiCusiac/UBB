package rpcprotocol;

import dto.*;
import models.Match;
import models.Player;
import models.MyString;
import services.IObserver;
import services.IService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RpcProxy implements IService {
    private String host;
    private int port;

    private IObserver client;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    
    public RpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }
    
    @Override
    public ArrayList<Match> getAllMatches() throws Exception {

        Request req=new Request.Builder().type(RequestsType.GetMatches).data().build();
        sendRequest(req);
        Response response=readResponse();
        if (response.responsesType()== ResponsesType.Error){
            String err = response.data().toString();
            throw new Exception(err);
        }
        MatchDTO[] mDTO=(MatchDTO[])response.data();

        return DTOUtils.getMatchesFromDTOArray(mDTO);
    }

    private Response readResponse() {
        Response response=null;
        try{
            response=qresponses.take();
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

    @Override
    public ArrayList<Match> getMatchesSortedByAvailableSeatsDesc() throws Exception {
        Request req=new Request.Builder().type(RequestsType.GetMatchesSorted).data().build();
        sendRequest(req);
        Response response=readResponse();
        if (response.responsesType()== ResponsesType.Error){
            String err = response.data().toString();
            throw new Exception(err);
        }
        MatchDTO[] mDTO=(MatchDTO[])response.data();

        return DTOUtils.getMatchesFromDTOArray(mDTO);
    }

    @Override
    public void sellTicket(Match match, String name, Integer seats) throws Exception {
        SellTicketDTO sellTicketDTO = new SellTicketDTO(match, name, seats);
        Request req = new Request.Builder().type(RequestsType.SellTicket).data(sellTicketDTO).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.responsesType() == ResponsesType.Error){
            String err=response.data().toString();
            throw new Exception(err);
        }
    }

    @Override
    public Player findPlayer(Player player) {
        return null;
    }

    @Override
    public Player getPlayerByName(String name) {
        return null;
    }

    @Override
    public boolean hasPlayerByName(String name) {
        return false;
    }

    @Override
    public boolean isAuthValid(String currentName, IObserver client) throws Exception {
        initializeConnection();
//        TicketBoothDTO ticketBoothDTO= DTOUtils.getTicketBoothDTOFromTicketBoooth(new TicketBooth(currentName, currentPassword));
        Player player = new Player(currentName);
        System.out.println("Player who wants to log in " + player);
        Request req = new Request.Builder().type(RequestsType.Login).data(player).build();

        System.out.println("LogIn Request created");

        sendRequest(req);
        Response response = readResponse();

        System.out.println("Read response: " + response);

        if (response.responsesType() == ResponsesType.Ok){
            this.client=client;
            System.out.println("LogIn all good.");
            return true;
        }
        if (response.responsesType() == ResponsesType.Error){
            String err=response.data().toString();
            closeConnection();
            throw new Exception(err);
        }

        return false;
    }

    @Override
    public boolean startGame(String currentName) throws Exception {
        Player player = new Player(currentName);

        Request req=new Request.Builder().type(RequestsType.StartGame).data(player).build();
        sendRequest(req);
        Response response = readResponse();
        //closeConnection();
        if (response.responsesType() == ResponsesType.Error){
            String err = response.data().toString();
            System.out.println("Eroare la startGame: " + currentName);
            throw new Exception(err);
            //return false;
        }
        return true;
    }

    @Override
    public boolean giveGuess(String currentName, String currentGuess) throws Exception {
        Player player = new Player(currentName);

        GuessDTO guessDTO = new GuessDTO(player, currentGuess);

        Request req=new Request.Builder().type(RequestsType.Move).data(guessDTO).build();
        sendRequest(req);
        Response response = readResponse();
        //closeConnection();
        if (response.responsesType() == ResponsesType.Error){
            String err = response.data().toString();
            System.out.println("Eroare la move: " + currentName);
            throw new Exception(err);
            //return false;
        }
        return true;
    }

    private void closeConnection() {
        finished=true;

        try {
            inputStream.close();
            outputStream.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logout(Player player, IObserver client) throws Exception {
//        TicketBoothDTO ticketBoothDTO = DTOUtils.getTicketBoothDTOFromTicketBoooth(player);
        Request req=new Request.Builder().type(RequestsType.Logout).data(player).build();
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        if (response.responsesType() == ResponsesType.Error){
            String err = response.data().toString();
            System.out.println("Opa, eroare la logout ;(");
            throw new Exception(err);
        }
    }

    private void initializeConnection() {
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
            startReader();
            System.out.println("Un nou thread a inceput.");
        } catch (IOException e) {
            System.out.println("Initializare conexiune failed:(");
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw=new Thread(new ReaderThread());
        System.out.println("Un nou thread va incepe");
        tw.start();
    }
    
    private void handleUpdate(Response response){
        System.out.println("Update to be made");
        if (response.responsesType() == ResponsesType.Update){
            try {
//                client.ticketSold();
                client.ticketSold(DTOUtils.getMatchesFromDTOArray((MatchDTO[]) response.data()));
                System.out.println("ticketSold called");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Ups, eroare la mânuit update-ul :p");
            }
        }
        if (response.responsesType() == ResponsesType.StartGame){
            try {
//                client.ticketSold();
//                client.ticketSold(DTOUtils.getMatchesFromDTOArray((MatchDTO[]) response.data()));
                var x = (MyString) response.data();
                String s = x.getValue();
                client.gameStarted(s);
                System.out.println("Game started called");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Ups, eroare la mânuit update-ul :p");
            }
        }
    }

    private boolean isUpdate(Response response){
        return response.responsesType() == ResponsesType.Update || response.responsesType() == ResponsesType.StartGame || response.responsesType() == ResponsesType.MoveMade;
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response = inputStream.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            System.out.println("Putting response to q: " + response );
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
