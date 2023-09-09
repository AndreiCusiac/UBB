package protobuffprotocol;

import dto.DTOUtils;
import dto.SellTicketDTO;
import models.Match;
import models.TicketBooth;
import rpcprotocol.Response;
import rpcprotocol.ResponsesType;
import services.IObserver;
import services.IService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ProtoWorker implements Runnable, IObserver {

    private IService server;
    private Socket connection;

    private InputStream input;
    private OutputStream output;
    private volatile boolean connected;

    public ProtoWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;

        try {
            output=connection.getOutputStream() ;//new ObjectOutputStream(connection.getOutputStream());
            input=connection.getInputStream(); //new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (connected) {
            try {
                // Object request=input.readObject();
                System.out.println("Waiting requests ...");
                MyProtobufs.Request request = MyProtobufs.Request.parseDelimitedFrom(input);
                System.out.println("Request received: " + request);
                MyProtobufs.Response response = handleRequest(request);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    private MyProtobufs.Response handleRequest(MyProtobufs.Request request) {
        MyProtobufs.Response response = null;

        switch (request.getType()){
            case Login:{
                System.out.println("Login request ...");
                TicketBooth ticketBooth = ProtoUtils.getTicketBooth(request);

                try {
                    var tryLogIn = server.isAuthValid(ticketBooth.getName(), ticketBooth.getPassword(), this);

                    System.out.println("Try LogIn? " + ticketBooth);

                    if (tryLogIn) {
                        return ProtoUtils.createOkResponse();
                    }
                    else {
                        return ProtoUtils.createErrorResponse("Eroare la autentificare (nume si/ sau parola invalide)!");
                    }
                } catch (Exception e) {
                    connected = false;
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case Logout:{
                System.out.println("Logout request");

                TicketBooth ticketBooth = ProtoUtils.getTicketBooth(request);

                try {
                    server.logout(ticketBooth, this);
                    connected = false;
                    return ProtoUtils.createOkResponse();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            case SellTicket:{
                System.out.println("Worker handle sell ticket.");

                SellTicketDTO sellTicketDTO = ProtoUtils.getSellTicketDTO(request);
                Match match = new Match(sellTicketDTO.getHomeTeam(), sellTicketDTO.getAwayTeam(), sellTicketDTO.getTicketPrice(), sellTicketDTO.getTotalSeats(), sellTicketDTO.getAvailableSeats());
                match.setSoldOut(sellTicketDTO.getSoldOut());

                try {
                    server.sellTicket(match, sellTicketDTO.getName(), sellTicketDTO.getSeats());

                    return ProtoUtils.createOkResponse();
                } catch (Exception e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }

            case GetMatches:{
                System.out.println("Get All Matches Request.");

                try {
                    var allMatches = server.getAllMatches();
                    return ProtoUtils.createGetMatchesResponse(allMatches);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            case GetMatchesSorted:{
                System.out.println("Get All Matches Request.");

                try {
                    var allMatches = server.getMatchesSortedByAvailableSeatsDesc();
                    return ProtoUtils.createGetMatchesResponse(allMatches);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    private void sendResponse(MyProtobufs.Response response) throws IOException {
        System.out.println("sending response "+response);
        response.writeDelimitedTo(output);
        //output.writeObject(response);
        output.flush();
    }

    @Override
    public void ticketSold() throws Exception {
        return;
    }

    @Override
    public void ticketSold(ArrayList<Match> matches) throws Exception {
        System.out.println("Updated matches (available tickets) list: " + matches);

        try {
            sendResponse(ProtoUtils.createSoldTicketResponse(matches));
        } catch (IOException e) {
            throw new Exception("Sending error: "+e);
        }
    }
}
