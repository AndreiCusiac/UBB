package rpcprotocol;

import dto.*;
import models.Match;
import models.Ticket;
import models.TicketBooth;
import services.IObserver;
import services.IService;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.security.interfaces.RSAKey;
import java.util.ArrayList;

public class RpcReflexionWorker implements Runnable, IObserver {
    private IService server;
    private Socket connection;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private volatile boolean connected;

    public RpcReflexionWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;

        try {
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.flush();

            inputStream = new ObjectInputStream(connection.getInputStream());
            connected = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (connected) {
            try {
                Object request=inputStream.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("Sending response: " + response);
        outputStream.writeObject(response);
        outputStream.flush();
        System.out.println("Response sent: " + response);
    }

    private Response handleRequest(Request request) {
        Response response = null;
        String handlerName = "handle" + (request).requestsType();
        System.out.println("HandlerName: " + handlerName);

        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response)method.invoke(this, request);
            System.out.println("Method: " + handlerName + " invoked.");

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleLogin (Request request) {
        System.out.println("Login request " + request.requestsType());

//        TicketBoothDTO ticketBoothDTO = (TicketBoothDTO)request.data();
//        TicketBooth ticketBooth = DTOUtils.getTicketBoothFromDTO(ticketBoothDTO);

        TicketBooth ticketBooth = (TicketBooth)request.data();

        System.out.println("TicketBooth " + ticketBooth);

        try {
            var tryLogIn = server.isAuthValid(ticketBooth.getName(), ticketBooth.getPassword(), this);

            System.out.println("Try LogIn? " + ticketBooth);

            if (tryLogIn) {
                return new Response.Builder().type(ResponsesType.Ok).data().build();
            }
            else {
                return new Response.Builder().type(ResponsesType.Error).data("Eroare la autentificare (nume si/ sau parola invalide)!").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            connected=false;
            System.out.println("Eroare la handle log in:(");
            return new Response.Builder().type(ResponsesType.Error).data(e.getMessage()).build();
        }
    }

    private Response handleLogout (Request request) {
        System.out.println("Logout request.");
//        TicketBoothDTO ticketBoothDTO = (TicketBoothDTO)request.data();
//        TicketBooth ticketBooth = DTOUtils.getTicketBoothFromDTO(ticketBoothDTO);

        TicketBooth ticketBooth = (TicketBooth)request.data();

        try {
            server.logout(ticketBooth, this);
            connected=false;
            return okResponse;
        } catch (Exception e) {
            return new Response.Builder().type(ResponsesType.Error).data(e.getMessage()).build();
        }
    }

    private Response handleGetMatches (Request request) {
        System.out.println("Get All Matches.");
        //        TicketBoothDTO ticketBoothDTO = (TicketBoothDTO)request.data();
//        TicketBooth ticketBooth = DTOUtils.getTicketBoothFromDTO(ticketBoothDTO);

        TicketBooth ticketBooth = (TicketBooth)request.data();
        try {
            var allMatches = server.getAllMatches();
            MatchDTO[] allM = DTOUtils.getDTOMatches(allMatches);
            return new Response.Builder().type(ResponsesType.GetMatches).data(allM).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponsesType.Error).data(e.getMessage()).build();
        }
    }

    private Response handleGetMatchesSorted (Request request) {
        System.out.println("Get All Matches.");
        //        TicketBoothDTO ticketBoothDTO = (TicketBoothDTO)request.data();
//        TicketBooth ticketBooth = DTOUtils.getTicketBoothFromDTO(ticketBoothDTO);

        TicketBooth ticketBooth = (TicketBooth)request.data();
        try {
            var allMatches = server.getMatchesSortedByAvailableSeatsDesc();
            MatchDTO[] allM = DTOUtils.getDTOMatches(allMatches);
            return new Response.Builder().type(ResponsesType.GetMatchesSorted).data(allM).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponsesType.Error).data(e.getMessage()).build();
        }
    }

    private Response handleSellTicket (Request request) {
        System.out.println("Worker handle sell ticket.");
        SellTicketDTO sellTicketDTO = (SellTicketDTO) request.data();
//        Ticket ticket = DTOUtils.getTicketFromDTO(ticketDTO);

        Match match = new Match(sellTicketDTO.getId(), sellTicketDTO.getHomeTeam(), sellTicketDTO.getAwayTeam(), sellTicketDTO.getTicketPrice(), sellTicketDTO.getTotalSeats(), sellTicketDTO.getAvailableSeats());

        try {
            server.sellTicket(match, sellTicketDTO.getName(), sellTicketDTO.getSeats());

//            var allMatches = server.getAllMatches();
//            MatchDTO[] allM = DTOUtils.getDTOMatches(allMatches);
//
//            // return new Response.Builder().type(ResponsesType.Update).data().build();
//            return new Response.Builder().type(ResponsesType.Update).data(allM).build();

            return okResponse;
        } catch (Exception e) {
            return new Response.Builder().type(ResponsesType.Error).data(e.getMessage()).build();
        }
    }

    private static Response okResponse = new Response.Builder().type(ResponsesType.Ok).build();

    @Override
    public void ticketSold() throws Exception {
        Response resp = new Response.Builder().type(ResponsesType.Update).data().build();

        System.out.println("Updated matches (available tickets) list.");
        try {
            sendResponse(resp);
        } catch (IOException e) {
            throw new Exception("Sending error: "+e);
        }
    }

    @Override
    public void ticketSold(ArrayList<Match> matches) throws Exception {
        var allM = DTOUtils.getDTOMatches(matches);
        Response resp = new Response.Builder().type(ResponsesType.Update).data(allM).build();

        System.out.println("Updated matches (available tickets) list: " + allM);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            throw new Exception("Sending error: "+e);
        }
    }
}
