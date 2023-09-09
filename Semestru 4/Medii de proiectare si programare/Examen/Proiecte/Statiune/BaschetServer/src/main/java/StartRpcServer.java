import repositories.*;
import server.ServerAppService;
import services.IService;
import utils.AbstractServer;
import utils.RpcConcurrentServer;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort = 55555;
    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();

        Properties serverProps=new Properties();

        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/chatserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }

        GameRepository gameRepository = new GameHbRepository(serverProps);
        PozitieRepository pozitieRepository = new PozitieHbRepository(serverProps);

        RoundRepository roundRepository = new RoundHbRepository(serverProps);
        MoveRepository moveRepository = new MoveHbRepository(serverProps);

        MatchRepository matchRepository = new MatchDbRepository(serverProps);
        TicketBoothRepository ticketBoothRepository = new TicketBoothDbRepository(serverProps);
        TicketRepository ticketRepository = new TicketDbRepository(serverProps);
        TicketRepository ticketRepositoryH = new TicketHbRepository(serverProps);

        PlayerRepository playerRepositoryH = new PlayerHbRepository(serverProps);

//        IService serverApp = new ServerAppService(matchRepository, ticketBoothRepository, ticketRepository);
        IService serverApp = new    ServerAppService(gameRepository, pozitieRepository, roundRepository, moveRepository, matchRepository, playerRepositoryH, ticketRepositoryH);

        int chatServerPort = defaultPort;

        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }

        System.out.println("Starting server on port: "+chatServerPort);

        AbstractServer server = new RpcConcurrentServer(chatServerPort, serverApp);

        try {
            server.start();
        } catch (Exception e) {
            System.err.println("Error starting the server" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                server.stop();
            } catch (Exception e) {
                System.err.println("Error stopping server "+e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
