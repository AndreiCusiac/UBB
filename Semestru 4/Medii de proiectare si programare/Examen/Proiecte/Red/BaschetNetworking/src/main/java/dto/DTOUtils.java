package dto;

import models.*;

import java.util.ArrayList;

public class DTOUtils {
    public static Match getMatchFromDTO (MatchDTO matchDTO) {
        String id = matchDTO.getId();
        String homeTeam = matchDTO.getHomeTeam();
        String awayTeam = matchDTO.getAwayTeam();
        Double ticketPrice = matchDTO.getTicketPrice();
        Integer totalSeats = matchDTO.getTotalSeats();
        Integer availableSeats = matchDTO.getAvailableSeats();
        String soldOut = matchDTO.getSoldOut();

        Match match = new Match(id, homeTeam, awayTeam, ticketPrice, totalSeats, availableSeats);

        match.setSoldOut(soldOut);

        return match;
    }

    public static MatchDTO getMatchDTOFromMatch (Match match) {
        String id = match.getId();
        String homeTeam = match.getHomeTeam();
        String awayTeam = match.getAwayTeam();
        Double ticketPrice = match.getTicketPrice();
        Integer totalSeats = match.getTotalSeats();
        Integer availableSeats = match.getAvailableSeats();
        String soldOut = match.getSoldOut();

        MatchDTO matchDTO = new MatchDTO(id, homeTeam, awayTeam, ticketPrice, totalSeats, availableSeats);

        matchDTO.setSoldOut(soldOut);

        return matchDTO;
    }

    public static TicketBooth getTicketBoothFromDTO (TicketBoothDTO ticketBoothDTO) {
        String id = ticketBoothDTO.getId();
        String name = ticketBoothDTO.getName();
        String password = ticketBoothDTO.getPassword();
        String salt = ticketBoothDTO.getSalt();
        String hash = ticketBoothDTO.getHash();

        return new TicketBooth(id, name, password, salt, hash);
    }

    public static TicketBoothDTO getTicketBoothDTOFromTicketBoooth (TicketBooth ticketBooth) {
        String id = ticketBooth.getId();
        String name = ticketBooth.getName();
        String password = ticketBooth.getPassword();
        String salt = ticketBooth.getSalt();
        String hash = ticketBooth.getHash();

        return new TicketBoothDTO(id, name, password, salt, hash);
    }

    public static Ticket getTicketFromDTO (TicketDTO ticketDTO) {
        String id = ticketDTO.getId();
        String idMatch = ticketDTO.getIdMatch();
        String name = ticketDTO.getName();
        Integer seats = ticketDTO.getSeats();

        return new Ticket(id, idMatch, name, seats);
    }

    public static TicketDTO getTicketDTOFromTicket (Ticket ticket) {
        String id = ticket.getId();
        String idMatch = ticket.getIdMatch();
        String name = ticket.getName();
        Integer seats = ticket.getSeats();

        return new TicketDTO(id, idMatch, name, seats);
    }

    public static MatchDTO[] getDTOMatches(ArrayList<Match> allMatches) {
        MatchDTO[] mDTO = new MatchDTO[allMatches.size()];
        for(int i=0; i<allMatches.size(); i++)
            mDTO[i] = getMatchDTOFromMatch(allMatches.get(i));
        return mDTO;
    }

    public static ArrayList<Match> getMatchesFromDTOArray(MatchDTO[] allM) {
        ArrayList<Match> matches = new ArrayList<>();

        for(int i=0; i<allM.length; i++)
            matches.add(getMatchFromDTO(allM[i]));

        return matches;
    }

    public static StartMatchDTO getStartMatchDTOFromPlayersAndCards(ArrayList<Player> players, ArrayList<Card> cards) {

        return new StartMatchDTO(players, cards);
    }

    public static ArrayList<Card> getCardsFromStartMatchDTO(StartMatchDTO startMatchDTO) {
        ArrayList<Card> cards;

        cards = startMatchDTO.getCards();

//        for(int i=0; i<startMatchDTO.getPlayers().size(); i++)
//            cards.add(getMatchFromDTO(startMatchDTO.getPlayers().ge));

        return cards;
    }
    public static ArrayList<Player> getPlayersFromStartMatchDTO(StartMatchDTO startMatchDTO) {
        ArrayList<Player> players;

        players = startMatchDTO.getPlayers();

//        for(int i=0; i<startMatchDTO.getPlayers().size(); i++)
//            players.add(getMatchFromDTO(startMatchDTO.getPlayers().ge));

        return players;
    }

    public static GuessDTO getGuessDTOFromPlayerAndGuess(Player player, String guess) {
        return new GuessDTO(player, guess);
    }

    public static Player getPlayerFromGuessDTO(GuessDTO guessDTO) {
        return guessDTO.getPlayer();
    }

    public static String getGuessFromGuessDTO(GuessDTO guessDTO) {
        return guessDTO.getGuess();
    }


    public static CardsDTO getCardsDTOFromCards(ArrayList<Card> cards) {
        return new CardsDTO(cards);
    }

    public static ArrayList<Card> getCardsFromCardsDTO(CardsDTO cardsDTO) {
        return cardsDTO.getCards();
    }
}
