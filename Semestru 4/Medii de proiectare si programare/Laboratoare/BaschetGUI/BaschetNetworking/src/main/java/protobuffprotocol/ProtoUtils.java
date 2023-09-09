package protobuffprotocol;

import dto.SellTicketDTO;
import models.Match;
import models.TicketBooth;

import java.util.ArrayList;

public class ProtoUtils {
    public static MyProtobufs.Request createLoginRequest(TicketBooth ticketBooth){
        MyProtobufs.TicketBooth ticketBoothDTO = MyProtobufs.TicketBooth.newBuilder().setName(ticketBooth.getName()).setPassword(ticketBooth.getPassword()).build();
        MyProtobufs.Request request = MyProtobufs.Request.newBuilder().setType(MyProtobufs.Request.Type.Login)
                .setTicketBooth(ticketBoothDTO).build();
        return request;
    }
    public static MyProtobufs.Request createLogoutRequest(TicketBooth ticketBooth){
        MyProtobufs.TicketBooth ticketBoothDTO = MyProtobufs.TicketBooth.newBuilder().setName(ticketBooth.getName()).build();
        MyProtobufs.Request request= MyProtobufs.Request.newBuilder().setType(MyProtobufs.Request.Type.Logout)
                .setTicketBooth(ticketBoothDTO).build();
        return request;
    }

    public static MyProtobufs.Request createSellTicketRequest(Match match, String name, Integer seats){
        MyProtobufs.SellTicket sellTicketDTO = MyProtobufs.SellTicket.newBuilder().
                setHomeTeam(match.getHomeTeam())
                .setAwayTeam(match.getAwayTeam())
                .setTicketPrice(match.getTicketPrice())
                .setTotalSeats(match.getTotalSeats())
                .setAvailableSeats(match.getAvailableSeats())
                .setSoldOut(match.getSoldOut())
                .setName(name)
                .setSeats(seats)
                .setIdMatch(match.getId()).build();

//                setSenderId(message.getSender().getId())
//                .setReceiverId(message.getReceiver().getId())
//                .setText(message.getText()).build();
        MyProtobufs.Request request = MyProtobufs.Request.newBuilder()
                .setType(MyProtobufs.Request.Type.SellTicket)
                .setSellTicket(sellTicketDTO).build();
        return request;
    }

    public static MyProtobufs.Request createGetMatchesRequest(){

        MyProtobufs.Request request = MyProtobufs.Request.newBuilder()
                .setType(MyProtobufs.Request.Type.GetMatches)
                .build();
        return request;
    }

    public static MyProtobufs.Request createGetMatchesSortedRequest(){

        MyProtobufs.Request request = MyProtobufs.Request.newBuilder()
                .setType(MyProtobufs.Request.Type.GetMatchesSorted)
                .build();
        return request;
    }


    public static MyProtobufs.Response createOkResponse(){
        MyProtobufs.Response response=MyProtobufs.Response.newBuilder()
                .setType(MyProtobufs.Response.Type.Ok).build();
        return response;
    }

    public static MyProtobufs.Response createErrorResponse(String text){
        MyProtobufs.Response response=MyProtobufs.Response.newBuilder()
                .setType(MyProtobufs.Response.Type.Error)
                .setError(text).build();
        return response;
    }

    public static MyProtobufs.Response createSoldTicketResponse(ArrayList<Match> matches){
        MyProtobufs.Response.Builder response = MyProtobufs.Response.newBuilder()
                .setType(MyProtobufs.Response.Type.SoldTicket);

        for (Match match : matches){
            MyProtobufs.Match matchDTO = MyProtobufs.Match.newBuilder()
                    .setHomeTeam(match.getHomeTeam())
                    .setAwayTeam(match.getAwayTeam())
                    .setTicketPrice(match.getTicketPrice())
                    .setTotalSeats(match.getTotalSeats())
                    .setAvailableSeats(match.getAvailableSeats())
                    .setSoldOut(match.getSoldOut())
                    .setId(match.getId())
                    .build();

            response.addMatches(matchDTO);
        }

        return response.build();
    }

    public static MyProtobufs.Response createGetMatchesResponse(ArrayList<Match> matches){

        MyProtobufs.Response.Builder response = MyProtobufs.Response.newBuilder()
                .setType(MyProtobufs.Response.Type.GetMatches);

        for (Match match : matches){
            MyProtobufs.Match matchDTO = MyProtobufs.Match.newBuilder()
                    .setHomeTeam(match.getHomeTeam())
                    .setAwayTeam(match.getAwayTeam())
                    .setTicketPrice(match.getTicketPrice())
                    .setTotalSeats(match.getTotalSeats())
                    .setAvailableSeats(match.getAvailableSeats())
                    .setSoldOut(match.getSoldOut())
                    .setId(match.getId())
                    .build();

            response.addMatches(matchDTO);
        }

        return response.build();
    }

    public static MyProtobufs.Response createGetMatchesSortedResponse(ArrayList<Match> matches){

        MyProtobufs.Response.Builder response = MyProtobufs.Response.newBuilder()
                .setType(MyProtobufs.Response.Type.GetMatchesSorted);

        for (Match match : matches){
            MyProtobufs.Match matchDTO = MyProtobufs.Match.newBuilder()
                    .setHomeTeam(match.getHomeTeam())
                    .setAwayTeam(match.getAwayTeam())
                    .setTicketPrice(match.getTicketPrice())
                    .setTotalSeats(match.getTotalSeats())
                    .setAvailableSeats(match.getAvailableSeats())
                    .setSoldOut(match.getSoldOut())
                    .setId(match.getId())
                    .build();

            response.addMatches(matchDTO);
        }

        return response.build();
    }

    public static String getError(MyProtobufs.Response response){
        String errorMessage=response.getError();
        return errorMessage;
    }

    public static ArrayList<Match> getMatches (MyProtobufs.Response response){
        ArrayList<Match> matches = new ArrayList<>();

        for(int i=0; i<response.getMatchesCount(); i++){
            MyProtobufs.Match matchDTO = response.getMatches(i);

            Match match = new Match(matchDTO.getHomeTeam(),
                matchDTO.getAwayTeam(),
                matchDTO.getTicketPrice(),
                matchDTO.getTotalSeats(),
                matchDTO.getAvailableSeats());
            match.setSoldOut(matchDTO.getSoldOut());
            match.setId(matchDTO.getId());

            matches.add(match);
        }

        return matches;
    }
    public static TicketBooth getTicketBooth (MyProtobufs.Request request){
        TicketBooth ticketBooth = new TicketBooth(request.getTicketBooth().getName(),
                request.getTicketBooth().getPassword());

        return ticketBooth;
    }

    public static SellTicketDTO getSellTicketDTO (MyProtobufs.Request request){
        Match match = new Match(request.getSellTicket().getHomeTeam(),
                request.getSellTicket().getAwayTeam(),
                request.getSellTicket().getTicketPrice(),
                request.getSellTicket().getTotalSeats(),
                request.getSellTicket().getAvailableSeats());

        match.setSoldOut(request.getSellTicket().getSoldOut());
        match.setId(request.getSellTicket().getIdMatch());

        return new SellTicketDTO(match, request.getSellTicket().getName(), request.getSellTicket().getSeats());
    }
}
