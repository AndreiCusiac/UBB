package protobuffprotocol;

import dto.DTOUtils;
import dto.MatchDTO;
import models.Match;
import models.TicketBooth;
import rpcprotocol.Request;
import rpcprotocol.RequestsType;
import rpcprotocol.Response;
import rpcprotocol.ResponsesType;
import services.IObserver;
import services.IService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoProxy implements IService {

    private String host;
    private int port;

    private IObserver client;

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<MyProtobufs.Response> qresponses;
    private volatile boolean finished;

    public ProtoProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<MyProtobufs.Response>();
    }

    private void sendRequest(MyProtobufs.Request request) throws Exception {
        try {
            System.out.println("Sending request ... " + request);
            //request.writeTo(output);
            request.writeDelimitedTo(output);
            output.flush();
            System.out.println("Request sent.");
        } catch (IOException e) {
            throw new Exception("Error sending object "+e);
        }
    }

    private MyProtobufs.Response readResponse() throws Exception{
        MyProtobufs.Response response = null;

        try{
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public ArrayList<Match> getAllMatches() throws Exception {

        //Request req=new Request.Builder().type(RequestsType.GetMatches).data().build();
        sendRequest(ProtoUtils.createGetMatchesRequest());
        MyProtobufs.Response response = readResponse();

        if (response.getType() == MyProtobufs.Response.Type.Error){
            String err = ProtoUtils.getError(response);
            throw new Exception(err);
        }

        return ProtoUtils.getMatches(response);
    }

    @Override
    public ArrayList<Match> getMatchesSortedByAvailableSeatsDesc() throws Exception {
        //Request req=new Request.Builder().type(RequestsType.GetMatchesSorted).data().build();
        sendRequest(ProtoUtils.createGetMatchesSortedRequest());
        MyProtobufs.Response response = readResponse();

        if (response.getType() == MyProtobufs.Response.Type.Error){
            String err = ProtoUtils.getError(response);
            throw new Exception(err);
        }

        return ProtoUtils.getMatches(response);
    }

    @Override
    public void sellTicket(Match match, String name, Integer seats) throws Exception {

        sendRequest(ProtoUtils.createSellTicketRequest(match, name, seats));
        MyProtobufs.Response response = readResponse();

        if (response.getType() == MyProtobufs.Response.Type.Error){
            String errorText=ProtoUtils.getError(response);
            throw new Exception(errorText);
        }
    }

    @Override
    public TicketBooth findTicketBooth(TicketBooth ticketBooth) {
        return null;
    }

    @Override
    public TicketBooth getTicketBoothByName(String name) {
        return null;
    }

    @Override
    public boolean hasTicketBoothByName(String name) {
        return false;
    }

    @Override
    public boolean isAuthValid(String currentName, String currentPassword, IObserver client) throws Exception {
        initializeConnection();

        TicketBooth ticketBooth = new TicketBooth(currentName, currentPassword);
        System.out.println("TicketBooth who wants to log in " + ticketBooth);
        sendRequest(ProtoUtils.createLoginRequest(ticketBooth));
        MyProtobufs.Response response = readResponse();

        if (response.getType() == MyProtobufs.Response.Type.Ok){
            this.client = client;
            System.out.println("LogIn all good.");
            return true;
        }

        if (response.getType() == MyProtobufs.Response.Type.Error){
            String errorText = ProtoUtils.getError(response);
            closeConnection();
            throw new Exception(errorText);
        }

        return false;
    }

    @Override
    public void logout(TicketBooth ticketBooth, IObserver client) throws Exception {
        sendRequest(ProtoUtils.createLogoutRequest(ticketBooth));
        MyProtobufs.Response response = readResponse();
        closeConnection();

        if (response.getType() == MyProtobufs.Response.Type.Error){
            String errorText = ProtoUtils.getError(response);
            throw new Exception(errorText);
        }
    }

    private void initializeConnection() throws Exception{
        try {
            connection = new Socket(host,port);
            output = connection.getOutputStream();
            //output.flush();
            input = connection.getInputStream();     //new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        finished = true;

        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(MyProtobufs.Response updateResponse){
        switch (updateResponse.getType()){
            case SoldTicket:{
                try {
                    client.ticketSold(ProtoUtils.getMatches(updateResponse));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    MyProtobufs.Response response = MyProtobufs.Response.parseDelimitedFrom(input);
                    System.out.println("response received "+response);

                    if (isUpdateResponse(response.getType())){
                        handleUpdate(response);
                    }else{
                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }

        private boolean isUpdateResponse(MyProtobufs.Response.Type type){
            switch (type){
                case SoldTicket:  return true;
            }
            return false;
        }
    }
}
