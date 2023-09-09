package repositories;

import models.Entity;
import models.Player;
import models.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PlayerHbRepository implements PlayerRepository{

    private static final Logger logger = LogManager.getLogger();

    public PlayerHbRepository(Properties props) {
        logger.info("Initializing PlayerHbRepository with properties: {} ", props);
        initialize();
    }

    @Override
    public boolean save(Player player) {

        logger.traceEntry("saving player{} ", player);

        try(Session session = sessionFactory.openSession()) {

            Transaction tx = null;

            try {
                tx = session.beginTransaction();
//                Message message = new Message("New Hello World " + (new Date()));
//                session.save(player);
                session.persist(player);

                tx.commit();

                logger.trace("Saved 1 instance");

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error at adding Player " + player + ": " + ex);
                if (tx != null)
                    tx.rollback();

                return false;
            }
            logger.traceExit();

            return true;
        }
    }

    @Override
    public boolean delete(Entity ide) {

        logger.traceEntry("deleting player with id {}", ide.getId());

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

//                Ticket crit = session.createQuery("from Tickets where id=?", Ticket.class)
//                        .setMaxResults(1)
//                        .uniqueResult();

                Player crit = new Player();

                crit.setId(ide.getId());

                System.err.println("To delete player " + crit.getId());

                session.delete(crit);
//                session.delete();
                tx.commit();
            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to delete player " + ide.getId() + ": " +ex);
                if (tx != null)
                    tx.rollback();

                return false;
            }
        }

        return true;
    }

    @Override
    public boolean update(Entity e, Player newE) {
        logger.traceEntry("Entering update player with id {} and new player {}", e.getId(), newE);

        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();

                Player player = session.load( Player.class, e.getId() );

                player.setName(newE.getName());

                session.update(player);
                tx.commit();

            } catch(RuntimeException ex){
                System.err.println("Eroare la update spectacol: "+ex);
                if (tx!=null)
                    tx.rollback();
                return false;
            }
        }

        return true;
    }

    @Override
    public Player find(Entity ide) {

        logger.traceEntry("Entering find player with id {}", ide.getId());

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

//                Ticket crit = session.createQuery("from Tickets where id=?", Ticket.class)
//                        .setMaxResults(1)
//                        .uniqueResult();

                return (Player) session.get(Player.class, ide.getId());

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to find player " + ide.getId() + ": " + ex);
                if (tx != null)
                    tx.rollback();

                return null;
            }
        }
    }

    @Override
    public ArrayList<Player> getAll() {

        logger.traceEntry();

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Player> players =
                        session.createQuery("from Player as m", Player.class).
                                //        session.createQuery("select m from Message as m join fetch m.nextMessage order by m.text asc", Message.class). //initializarea obiectelor asociate
                                        list();

                System.out.println(players.size() + " player(s) found:");

//                for (Ticket t : players) {
//                    System.out.println(t);
//                    //  System.out.println(m.getText() + ' ' + m.getId()+" textul urmatorului mesaj ["+m.getNextMessage().getText()+"]");
//                }
                tx.commit();

                logger.traceExit(players);
                return new ArrayList<>(players);

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to get all player " + "" + ": "  + ex);
                if (tx != null)
                    tx.rollback();

                logger.traceExit();
            }
        }

        return null;
    }

    static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }
}
