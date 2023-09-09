package server;

import models.*;
import repositories.*;
import services.IObserver;
import services.IService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServerAppService implements IService {

    private MatchRepository matchRepository;
    private PlayerRepository playerRepository;

    private RoundRepository roundRepository;

    private MoveRepository moveRepository;

    private GameRepository gameRepository;
    private PozitieRepository pozitieRepository;
    private TicketRepository ticketRepository;

    private Map<String, IObserver> loggedPlayers;

    private Map<String, String> playerInWhichGame;

    private Map<String, String> currentRoundInEachGames;

    private ArrayList<Player> alreadyInGamePlayers;

    private Map<String, Integer> numberOfMovesMadeInCurrentRoundOfGame;

    private final int defaultThreadsNo=5;

    private final int playersInAGame = 3;

    private final int cardsInAGame = 3;

    public ServerAppService(GameRepository gameRepository, PozitieRepository pozitieRepository, RoundRepository roundRepository, MoveRepository moveRepository, MatchRepository matchRepository, PlayerRepository playerRepository, TicketRepository ticketRepository) {
        this.gameRepository = gameRepository;
        this.pozitieRepository = pozitieRepository;

        this.roundRepository = roundRepository;
        this.moveRepository = moveRepository;

        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
        this.ticketRepository = ticketRepository;

        loggedPlayers = new ConcurrentHashMap<>();

        alreadyInGamePlayers = new ArrayList<>();
        numberOfMovesMadeInCurrentRoundOfGame = new ConcurrentHashMap<>();
        playerInWhichGame = new ConcurrentHashMap<>();
        currentRoundInEachGames = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized ArrayList<Match> getAllMatches() throws Exception {
        return matchRepository.getAll();
    }

    @Override
    public synchronized ArrayList<Match> getMatchesSortedByAvailableSeatsDesc() throws Exception {
//        Comparator<Match> comparatorByAvailableSeatsDesc = Comparator.comparing(Match::getAvailableSeats).reversed();
//
//        var list = matchService.getAll().stream().sorted(comparatorByAvailableSeatsDesc).collect(Collectors.toList());
//        ArrayList<Match> matchesSorted = new ArrayList<>();
//        matchesSorted.addAll(list);
//        return matchesSorted;

        Comparator<Match> comparatorByAvailableSeatsDesc = Comparator.comparing(Match::getAvailableSeats).reversed();

        return matchRepository.getAll().stream().sorted(comparatorByAvailableSeatsDesc).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public synchronized void sellTicket(Match match, String name, Integer seats) throws Exception {

        Integer numberOfTickets = ticketRepository.getAll().size();
        numberOfTickets += 1;

        Ticket ticket = new Ticket(numberOfTickets.toString(), match.getId(), name, seats);
        ticketRepository.save(ticket);

        Entity entity = new Entity();
        entity.setId(match.getId());

        matchRepository.updateAvailableSeats(entity, match, seats);

//        Iterable<TicketBooth> allTicketBooths = playerRepository.getAll();
        Iterable<TicketBooth> allTicketBooths = new ArrayList<>();

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        for(TicketBooth ticketBooth : allTicketBooths){
            IObserver loggedTBooth = loggedPlayers.get(ticketBooth.getName());

            if (loggedTBooth!=null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying ["+ticketBooth.getId()+ " - " + ticketBooth.getName() + "] ticket sold.");
//                        loggedTBooth.ticketSold();
                        loggedTBooth.ticketSold(matchRepository.getAll());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        }
        executor.shutdown();
    }

    @Override
    public Player findPlayer(Player player) {
        return playerRepository.find(player);
    }

    @Override
    public Player getPlayerByName(String name) {
        Predicate<Player> playerName = x -> x.getName().equals(name);

        return playerRepository.getAll().
                stream().
                filter(playerName).collect(Collectors.toList()).
                get(0);
    }

    @Override
    public boolean hasPlayerByName(String name) {
        Predicate<Player> playerName = x -> x.getName().equals(name);

        System.out.println(playerRepository.getAll());

        return playerRepository.getAll().
                stream().
                anyMatch(playerName);
    }

    @Override
    public synchronized boolean isAuthValid(String currentName, IObserver client) throws Exception {

        System.out.println("Player wanting to auth: " + currentName);

        if (hasPlayerByName(currentName) == false) {
            throw new Exception("Authentication failed (Player name not found).");
        }

        Player player = getPlayerByName(currentName);

//        if (player.getPassword().equals(currentPassword) == false) {
//            throw new Exception("Authentication failed (Password is incorrect).");
//        }

        if(loggedPlayers.get(player.getName())!=null)
            throw new Exception("Player already logged in!");

        loggedPlayers.put(player.getName(), client);

        return true;
    }

    private void createGames(Player player) throws Exception{
        var allPozities = pozitieRepository.getAll();
        int randomNum = ThreadLocalRandom.current().nextInt(0, allPozities.size());

        Pozitie pozitieCurrent = allPozities.get(randomNum);

        Integer numberOfGames = gameRepository.getAll().size();
        numberOfGames += 1;

        Game game = new Game();
        game.setId(numberOfGames.toString());
        game.setPlayer(player.getId());
        game.setGuessed("0");
        game.setPunctaj("0");
        game.setTimp(LocalDateTime.now().toString());
        game.setPozitie(pozitieCurrent.getId());

        setGameOfPlayer(game, player);
        gameRepository.save(game);

        playerInWhichGame.put(player.getId(), game.getId());

        System.out.println("Game in createGame: " + game);

        //matchRepository.updateAvailableSeats(entity, match, seats);

//        Iterable<TicketBooth> firstPlayersWaiting = playerRepository.getAll();
        //Iterable<Player> firstPlayersWaiting = playersForGame;

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        IObserver loggedPlayer = loggedPlayers.get(player.getName());

        if (loggedPlayer!=null)
            executor.execute(() -> {
                try {
                    System.out.println("Notifying ["+player.getId()+ " - " + player.getName() + "] game start with player: " + player + " and pozities: " + pozitieCurrent);
//                        loggedPlayer.ticketSold();
                    loggedPlayer.gameStarted(pozitieCurrent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        executor.shutdown();
    }

    @Override
    public boolean startGame(String currentName) throws Exception {

        Player player = new Player(currentName);
        player.setId(getPlayerByName(currentName).getId());

        if (alreadyInGamePlayers.contains(player)) {
            throw new Exception("Jucator deja in joc!");
//            return false;
        }

        alreadyInGamePlayers.add(player);

        System.out.println("Current in_game players: " + alreadyInGamePlayers);

        createGames(player);

        return true;
    }

    private boolean saveMove(String id_game, String id_round, String id_player, String id_card) {

        System.out.println("saveMove: " + id_game + id_round + id_player + id_card);

        Integer m = moveRepository.getAll().size();
        m += 1;

        Move move = new Move(id_game, id_player, id_card);
        move.setId(m.toString());

        moveRepository.save(move);

        return true;
    }

    private String getDist (Pozitie pozitie, String guess) {
        return "-1";
    }

    @Override
    public boolean giveGuess(String currentName, String currentGuess) throws Exception {
        System.out.println("Entered giveGuess service");

        Player player = new Player(currentName);
        player.setId(getPlayerByName(currentName).getId());

        System.out.println("Player: " + player);

        String gameId = getGameIdOfPlayer(player);

        System.out.println("GameID: " + gameId);

        Entity e = new Entity();
        e.setId(gameId);
        Game currentGameOfCurrentPlayer = gameRepository.find(e);

        System.out.println("Game: " + currentGameOfCurrentPlayer);

        e.setId(currentGameOfCurrentPlayer.getPozitie());
        Pozitie pozitieCurrent = pozitieRepository.find(e);

        String guess;
        String punctaj;

        if (currentGuess.equals(pozitieCurrent.getPozitie1()) ||
                currentGuess.equals(pozitieCurrent.getPozitie2()) ||
                currentGuess.equals(pozitieCurrent.getPozitie3())) {
                guess = "B";
                punctaj = "5";
        } else {
            guess = getDist(pozitieCurrent, currentGuess);
            punctaj = "-3";
        }

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        IObserver loggedPlayer = loggedPlayers.get(player.getName());

        if (loggedPlayer!=null)
            executor.execute(() -> {
                try {
                    System.out.println("Notifying ["+player.getId()+ " - " + player.getName() + "] move made guess: " + guess + " and punct: " + punctaj);
//                        loggedPlayer.ticketSold();
                    loggedPlayer.movesMade(guess, punctaj);

                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            });

        executor.shutdown();

        return true;
    }
//    private void shareCards() {
//        var allCards = pozitieRepository.getAll();
//
//        Integer numberOfGames = gameRepository.getAll().size();
//        numberOfGames += 1;
//
//        ArrayList<Player> playersForGame = new ArrayList<>();
//
//        Game game = new Game();
//        game.setId(numberOfGames.toString());
//
//        for (int i = 0; i < playersInAGame; i++){
//            playersForGame.add(alreadyInGamePlayers.get(i));
//        }
//
//        game.setPlayer1(alreadyInGamePlayers.get(0).getName());
//        game.setPlayer2(alreadyInGamePlayers.get(1).getName());
//        game.setPlayer3(alreadyInGamePlayers.get(2).getName());
//
//        gameRepository.save(game);
//
//
//        currentRoundInEachGames.put(game.getId(), "0");
//        numberOfMovesMadeInCurrentRoundOfGame.put(game.getId(), 0);
//
//        //matchRepository.updateAvailableSeats(entity, match, seats);
//
////        Iterable<TicketBooth> firstPlayersWaiting = playerRepository.getAll();
//        Iterable<Player> firstPlayersWaiting = playersForGame;
//
//        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
//
//        for(Player player : firstPlayersWaiting){
//            IObserver loggedPlayer = loggedPlayers.get(player.getName());
//
//            ArrayList<Card> cardsForAPlayer = new ArrayList<>();
//
//            for (int i = 0; i < cardsInAGame; i++) {
//
//                int index = (int)(Math.random() * allCards.size());
//                cardsForAPlayer.add(allCards.remove(index));
//            }
//
//            setGameOfPlayer(game, player);
//            inGamePlayers.put(game, player);
//
//            if (loggedPlayer!=null)
//                executor.execute(() -> {
//                    try {
//
//                        System.out.println("Notifying ["+player.getId()+ " - " + player.getName() + "] game start with players: " + playersForGame + " and cards: " + cardsForAPlayer);
////                        loggedPlayer.ticketSold();
//                        loggedPlayer.gameStarted(playersForGame, cardsForAPlayer);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//        }
//        executor.shutdown();
//    }

    private String getGameIdOfPlayer(Player player) {

//        var play = getPlayerByName(player.getName());

//        return play.getCurrentGame();
        return playerInWhichGame.get(player.getId());
    }

    private boolean setGameOfPlayer(Game game, Player player) {

        System.out.println("Entered setGameOfPlayer with " + game + player);

        var play = getPlayerByName(player.getName());

        play.setCurrentGame(game.getId());

        Entity e = new Entity();
        e.setId(play.getId());

        System.out.println("To update player " + play + " with id " + e);

        playerRepository.update(e, play);

        System.out.println("Update done!");
//
        return true;
    }

    private boolean getMovedOfPlayer(Player player) {
//        System.out.println("Made it to getMovedOfPlayer");
//
//        Entity e = new Entity();
//
//        e.setId(getPlayerByName(player.getName()).getId());
//
//        var play = playerRepository.find(e);
//
//        return play.getMoved();
        return true;
    }

    private boolean setMovedOfPlayer(Player player, Boolean value) {
//        System.out.println("Made it to setMovedOfPlayer");
//
//        Entity e = new Entity();
//
//        e.setId(getPlayerByName(player.getName()).getId());
//
//        var play = playerRepository.find(e);
//
//        play.setMoved(value);
//
//        e.setId(player.getId());
//
//        playerRepository.update(e, play);

        return true;
    }

    @Override
    public void logout(Player player, IObserver client) throws Exception {
//        loggedTicketBooths.put(player.getId(), client);

        System.out.println("Am ajuns la server cu cererea de logout a " + player);
        System.out.println("Acum logate: " + loggedPlayers);

        loggedPlayers.remove(player.getName());
    }
}
