package server;

import models.Entity;
import models.Match;
import models.Ticket;
import models.TicketBooth;
import repositories.MatchRepository;
import repositories.TicketBoothRepository;
import repositories.TicketRepository;
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
    private TicketBoothRepository ticketBoothRepository;
    private TicketRepository ticketRepository;

    private Map<String, IObserver> loggedTicketBooths;

    private final int defaultThreadsNo=5;

    public ServerAppService(MatchRepository matchRepository, TicketBoothRepository ticketBoothRepository, TicketRepository ticketRepository) {
        this.matchRepository = matchRepository;
        this.ticketBoothRepository = ticketBoothRepository;
        this.ticketRepository = ticketRepository;

        loggedTicketBooths=new ConcurrentHashMap<>();
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

        Iterable<TicketBooth> allTicketBooths = ticketBoothRepository.getAll();

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        for(TicketBooth ticketBooth : allTicketBooths){
            IObserver loggedTBooth = loggedTicketBooths.get(ticketBooth.getId());

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
    public TicketBooth findTicketBooth(TicketBooth ticketBooth) {
        return ticketBoothRepository.find(ticketBooth);
    }

    @Override
    public TicketBooth getTicketBoothByName(String name) {
        Predicate<TicketBooth> ticketBoothName = x -> x.getName().equals(name);

        return ticketBoothRepository.getAll().
                stream().
                filter(ticketBoothName).collect(Collectors.toList()).
                get(0);
    }

    @Override
    public boolean hasTicketBoothByName(String name) {
        Predicate<TicketBooth> ticketBoothName = x -> x.getName().equals(name);

        System.out.println(ticketBoothRepository.getAll());

        return ticketBoothRepository.getAll().
                stream().
                anyMatch(ticketBoothName);
    }

    @Override
    public synchronized boolean isAuthValid(String currentName, String currentPassword, IObserver client) throws Exception {

        System.out.println("TicketBooth wanting to auth: " + currentName);
        System.out.println("With password: " + currentPassword);

        if (hasTicketBoothByName(currentName) == false) {
            throw new Exception("Authentication failed (TicketBooth name not found).");
        }

        TicketBooth ticketBooth = getTicketBoothByName(currentName);

        if (ticketBooth.getPassword().equals(currentPassword) == false) {
            throw new Exception("Authentication failed (Password is incorrect).");
        }

        if(loggedTicketBooths.get(ticketBooth.getId())!=null)
            throw new Exception("TicketBooth already logged in!");

        loggedTicketBooths.put(ticketBooth.getId(), client);

        /*SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        System.out.println("Salt = " + toHex(salt) + " " + toHex(salt).getClass());
        System.out.println("Salt = " + toHex(salt).getBytes() + " " + toHex(salt).getBytes().getClass() + " " + toHex(salt).getBytes().toString().getBytes());

        KeySpec spec = new PBEKeySpec(currentPassword.toCharArray(), "[B@2e517bd9".getBytes(), 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();

        System.out.println("Hash = " + toHex(hash));
        System.out.println("\n");
        System.out.println(hash.length);

        return false;
/**/
        //String currentStoredSalt = getSaltByEmail(currentEmail);
        //String currentStoredSalt = "[B@7d9f52cd";
        //String currentStoredHash = getHashByEmail(currentEmail);

        //var generatedHash = generateHashForCurrent(currentPassword, currentStoredSalt.getBytes());

        //System.out.println("Hash = " + generatedHash.toString());
        //var z = generatedHash;

        //if (generatedHash.equals(currentStoredHash) == false) {
        //    return false;
        //}

        return true;
    }

    @Override
    public void logout(TicketBooth ticketBooth, IObserver client) throws Exception {
//        loggedTicketBooths.put(ticketBooth.getId(), client);
        loggedTicketBooths.remove(ticketBooth.getId());
    }
}
