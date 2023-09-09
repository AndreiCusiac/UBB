package repositories;

import models.Entity;
import models.Player;
import models.Pozitie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PozitieHbRepository implements PozitieRepository{

    private static final String bigName = "Pozitie";
    private static final String smallName = "pozitie";

    private static final Logger logger = LogManager.getLogger();

    public PozitieHbRepository(Properties props) {
        logger.info("Initializing " + bigName + "HbRepository with properties: {} ", props);
        initialize();
    }

    @Override
    public boolean save(Pozitie entityToBeSaved) {

        logger.traceEntry("saving " + smallName + "{} ", entityToBeSaved);

        try(Session session = sessionFactory.openSession()) {

            Transaction tx = null;

            try {
                tx = session.beginTransaction();
//                Message message = new Message("New Hello World " + (new Date()));
//                session.save(entityToBeSaved);
                session.persist(entityToBeSaved);

                tx.commit();

                logger.trace("Saved 1 instance");

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error at adding " + bigName + " " + entityToBeSaved + ": " + ex);
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

        logger.traceEntry("deleting " + smallName + " with id {}", ide.getId());

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

//                Ticket crit = session.createQuery("from Tickets where id=?", Ticket.class)
//                        .setMaxResults(1)
//                        .uniqueResult();

                Player crit = new Player();

                crit.setId(ide.getId());

                System.err.println("To delete " + smallName + " " + crit.getId());

                session.delete(crit);
//                session.delete();
                tx.commit();
            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to delete " + smallName + " " + ide.getId() + ": " +ex);
                if (tx != null)
                    tx.rollback();

                return false;
            }
        }

        return true;
    }

    @Override
    public boolean update(Entity e, Pozitie newE) {
        logger.traceEntry("Entering update " + smallName + " with id {} and new " + smallName + " {}", e.getId(), newE);

        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();

                Pozitie entityToBeUpdated = session.load( Pozitie.class, e.getId() );

                entityToBeUpdated.setPozitie1(newE.getPozitie1());
                entityToBeUpdated.setPozitie2(newE.getPozitie2());
                entityToBeUpdated.setPozitie3(newE.getPozitie3());

                session.update(entityToBeUpdated);
                tx.commit();

            } catch(RuntimeException ex){
                System.err.println("Eroare la update " + smallName + ": "+ex);
                if (tx!=null)
                    tx.rollback();
                return false;
            }
        }

        return true;
    }

    @Override
    public Pozitie find(Entity ide) {

        logger.traceEntry("Entering find " + smallName + " with id {}", ide.getId());

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

//                Ticket crit = session.createQuery("from Tickets where id=?", Ticket.class)
//                        .setMaxResults(1)
//                        .uniqueResult();

                return session.get(Pozitie.class, ide.getId());

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to find " + smallName + " " + ide.getId() + ": " + ex);
                if (tx != null)
                    tx.rollback();

                return null;
            }
        }
    }

    @Override
    public ArrayList<Pozitie> getAll() {

        logger.traceEntry();

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Pozitie> allEntities =
                        session.createQuery("from " + bigName + " as m", Pozitie.class).
                                //        session.createQuery("select m from Message as m join fetch m.nextMessage order by m.text asc", Message.class). //initializarea obiectelor asociate
                                        list();

                //System.out.println(allEntities.size() + " player(s) found:");

//                for (Ticket t : allEntities) {
//                    System.out.println(t);
//                    //  System.out.println(m.getText() + ' ' + m.getId()+" textul urmatorului mesaj ["+m.getNextMessage().getText()+"]");
//                }
                tx.commit();

                logger.traceExit(allEntities);
                return new ArrayList<>(allEntities);

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to get all " + smallName + "(s): "  + ex);
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
