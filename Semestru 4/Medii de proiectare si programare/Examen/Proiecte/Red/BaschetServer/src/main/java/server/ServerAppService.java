package server;

import models.*;
import repositories.*;
import services.IObserver;
import services.IService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServerAppService implements IService {

    private MatchRepository matchRepository;
    private PlayerRepository playerRepository;

    private WordRepository wordRepository;

    private MoveRepository moveRepository;

    private GameRepository gameRepository;
    private CardRepository cardRepository;
    private TicketRepository ticketRepository;

    private Map<String, IObserver> loggedPlayers;

    private Map<Game, Player> inGamePlayers;

    private Map<Player, Word> whichWordIsPlayerGuessing;

    private ArrayList<Player> playingPlayers;

    private Map<String, Integer> numberOfMovesMadeInCurrentRoundOfGame;

    private final int defaultThreadsNo=5;

    private final int playersInAGame = 3;

    private final int cardsInAGame = 3;

    public ServerAppService(GameRepository gameRepository, CardRepository cardRepository, WordRepository wordRepository, MoveRepository moveRepository, MatchRepository matchRepository, PlayerRepository playerRepository, TicketRepository ticketRepository) {
        this.gameRepository = gameRepository;
        this.cardRepository = cardRepository;

        this.wordRepository = wordRepository;
        this.moveRepository = moveRepository;

        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
        this.ticketRepository = ticketRepository;

        loggedPlayers = new ConcurrentHashMap<>();

        playingPlayers = new ArrayList<>();
        numberOfMovesMadeInCurrentRoundOfGame = new ConcurrentHashMap<>();
        inGamePlayers = new ConcurrentHashMap<>();
        whichWordIsPlayerGuessing = new ConcurrentHashMap<>();
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
//
//        if (player.getPassword().equals(currentPassword) == false) {
//            throw new Exception("Authentication failed (Password is incorrect).");
//        }

        if(loggedPlayers.get(player.getName())!=null)
            throw new Exception("Player already logged in!");

        loggedPlayers.put(player.getName(), client);

        return true;
    }

    private void createGames() throws Exception{
        var allCards = cardRepository.getAll();

        Integer numberOfGames = gameRepository.getAll().size();
        numberOfGames += 1;

        ArrayList<Player> playersForGame = new ArrayList<>();

        Game game = new Game();
        game.setId(numberOfGames.toString());


        for (int i = 0; i < playersInAGame; i++){
            playersForGame.add(playingPlayers.get(i));
        }

        game.setPlayer1(playingPlayers.get(0).getName());
        game.setPlayer2(playingPlayers.get(1).getName());
        game.setPlayer3(playingPlayers.get(2).getName());

        gameRepository.save(game);

        System.out.println("Game in createGame: " + game);

//        whichWordIsPlayerGuessing.put(game.getId(), "0");
        numberOfMovesMadeInCurrentRoundOfGame.put(game.getId(), 0);

        //matchRepository.updateAvailableSeats(entity, match, seats);

//        Iterable<TicketBooth> firstPlayersWaiting = playerRepository.getAll();

    }

    @Override
    public boolean startGame(String currentName) throws Exception {

        Player player = new Player(currentName);
        player.setId(getPlayerByName(currentName).getId());

        //setMovedOfPlayer(player, false);

        if (playingPlayers.contains(player)) {
            throw new Exception("Jucator deja in joc!");
//            return false;
        }

        playingPlayers.add(player);

        System.out.println("Current playing players: " + playingPlayers);

        var all = wordRepository.getAll();

        Integer index = (int)(Math.random() * all.size());

        String lettersToPlayer = all.get(index).getLetters();
        whichWordIsPlayerGuessing.put(player, all.get(index));

        Iterable<Player> firstPlayersWaiting = playingPlayers;

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        IObserver loggedPlayer = loggedPlayers.get(player.getName());

        if (loggedPlayer!=null)
            executor.execute(() -> {
                try {
                    System.out.println("Notifying ["+player.getId()+ " - " + player.getName() + "] game start with letters: " + lettersToPlayer);
//                        loggedPlayer.ticketSold();
                    loggedPlayer.gameStarted(lettersToPlayer);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        executor.shutdown();

        return true;
    }

    private boolean saveMove(String id_game, String id_round, String id_player, String id_card) {

        System.out.println("saveMove: " + id_game + id_round + id_player + id_card);

        Integer m = moveRepository.getAll().size();
        m += 1;

        Move move = new Move(id_game, id_round, id_player, id_card);
        move.setId(m.toString());

        moveRepository.save(move);

        return true;
    }


    @Override
    public boolean giveGuess(String currentName, String currentGuess) throws Exception {
        Player player = new Player(currentName);
        player.setId(getPlayerByName(currentName).getId());

        var word = whichWordIsPlayerGuessing.get(player);

        var words = word.getWords();

        if (words.contains(currentGuess)) {
            //Iterable<Player> firstPlayersWaiting = playingPlayers;

            ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

            IObserver loggedPlayer = loggedPlayers.get(player.getName());

            Integer v = currentGuess.length();

            String s = v.toString() + "/" + v.toString();

            if (loggedPlayer!=null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying ["+player.getId()+ " - " + player.getName() + "] game start with result: " + s);
//                        loggedPlayer.ticketSold();
                        loggedPlayer.movesMade(s);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            executor.shutdown();

            return true;
        } else {
            String[] arrOfStr = words.split(",");

            Integer max = 0;

            for (var i : arrOfStr) {
                Integer cur = 0;
                for (int j = 0; j < i.length(); j++) {
                    if (j >= currentGuess.length()) {
                        break;
                    }
                    if (currentGuess.charAt(j) == i.charAt(j) ) {
                        cur += 1;
                    }
                }

                if (cur > max) {
                    max = cur;
                }
            }

            ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

            IObserver loggedPlayer = loggedPlayers.get(player.getName());

            Integer v = currentGuess.length();

            String s = max.toString() + "/" + v.toString();

            if (loggedPlayer!=null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying ["+player.getId()+ " - " + player.getName() + "] game start with result: " + s);
//                        loggedPlayer.ticketSold();
                        loggedPlayer.movesMade(s);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            executor.shutdown();
        }

        return true;
    }
    private void shareCards() {
        var allCards = cardRepository.getAll();

        Integer numberOfGames = gameRepository.getAll().size();
        numberOfGames += 1;

        ArrayList<Player> playersForGame = new ArrayList<>();

        Game game = new Game();
        game.setId(numberOfGames.toString());

//        for (int i = 0; i < playersInAGame; i++){
//            playersForGame.add(waitingPlayers.get(i));
//        }
//
//        game.setPlayer1(waitingPlayers.get(0).getName());
//        game.setPlayer2(waitingPlayers.get(1).getName());
//        game.setPlayer3(waitingPlayers.get(2).getName());

        gameRepository.save(game);


//        whichWordIsPlayerGuessing.put(game.getId(), "0");
        numberOfMovesMadeInCurrentRoundOfGame.put(game.getId(), 0);

        //matchRepository.updateAvailableSeats(entity, match, seats);

//        Iterable<TicketBooth> firstPlayersWaiting = playerRepository.getAll();
        Iterable<Player> firstPlayersWaiting = playersForGame;

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        for(Player player : firstPlayersWaiting){
            IObserver loggedPlayer = loggedPlayers.get(player.getName());

            ArrayList<Card> cardsForAPlayer = new ArrayList<>();

            for (int i = 0; i < cardsInAGame; i++) {

                int index = (int)(Math.random() * allCards.size());
                cardsForAPlayer.add(allCards.remove(index));
            }

            setGameOfPlayer(game, player);
            inGamePlayers.put(game, player);

            if (loggedPlayer!=null)
                executor.execute(() -> {
                    try {

                        System.out.println("Notifying ["+player.getId()+ " - " + player.getName() + "] game start with players: " + playersForGame + " and cards: " + cardsForAPlayer);
//                        loggedPlayer.ticketSold();
//                        loggedPlayer.gameStarted(playersForGame, cardsForAPlayer);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        }
        executor.shutdown();
    }

    private String getGameIdOfPlayer(Player player) {
        var play = getPlayerByName(player.getName());

//        return play.getCurrentGame();
        return null;
    }

    private boolean setGameOfPlayer(Game game, Player player) {

        System.out.println("Entered setGameOfPlayer with " + game + player);

        var play = getPlayerByName(player.getName());

//        play.setCurrentGame(game.getId());

        Entity e = new Entity();
        e.setId(play.getId());

        System.out.println("To update player " + play + " with id " + e);

        playerRepository.update(e, play);

        System.out.println("Update done!");

        return true;
    }

    private boolean getMovedOfPlayer(Player player) {
        System.out.println("Made it to getMovedOfPlayer");

        Entity e = new Entity();

        e.setId(getPlayerByName(player.getName()).getId());

        var play = playerRepository.find(e);

//        return play.getMoved();
        return false;
    }

    private boolean setMovedOfPlayer(Player player, Boolean value) {
        System.out.println("Made it to setMovedOfPlayer");

        Entity e = new Entity();

        e.setId(getPlayerByName(player.getName()).getId());

        var play = playerRepository.find(e);

//        play.setMoved(value);

        e.setId(player.getId());

        playerRepository.update(e, play);

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
