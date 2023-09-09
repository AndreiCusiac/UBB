import models.Entity;
import models.Regizor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repositories.RegizorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RegizorHbRepository implements RegizorRepository {

    private static final Logger logger = LogManager.getLogger();

    public RegizorHbRepository(Properties props) {
        logger.info("Initializing RegizorHbRepository with properties: {} ", props);
        initialize();
    }

    @Override
    public boolean save(Regizor regizor) {

        logger.traceEntry("saving regizor{} ", regizor);

        try(Session session = sessionFactory.openSession()) {

            Transaction tx = null;

            try {
                tx = session.beginTransaction();
//                Message message = new Message("New Hello World " + (new Date()));
//                session.save(spectator);
                session.persist(regizor);

                tx.commit();

                logger.trace("Saved 1 instance");

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error at adding Regizor " + regizor + ": " + ex);
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

        logger.traceEntry("deleting regizor with id {}", ide.getId());

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

//                Ticket crit = session.createQuery("from Tickets where id=?", Ticket.class)
//                        .setMaxResults(1)
//                        .uniqueResult();

                Regizor crit = new Regizor();

                crit.setId(ide.getId());

                System.err.println("To delete regizor " + crit.getId());

                session.delete(crit);
//                session.delete();
                tx.commit();
            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to delete regizor " + ide.getId() + ": " +ex);
                if (tx != null)
                    tx.rollback();

                return false;
            }
        }

        return true;
    }

    @Override
    public boolean update(Entity e, Regizor newE) {
        return false;
    }

    @Override
    public Regizor find(Entity ide) {

        logger.traceEntry("Entering find regizor with id {}", ide.getId());

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

//                Ticket crit = session.createQuery("from Tickets where id=?", Ticket.class)
//                        .setMaxResults(1)
//                        .uniqueResult();

                return (Regizor) session.get(Regizor.class, ide.getId());

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to find regizor " + ide.getId() + ": " + ex);
                if (tx != null)
                    tx.rollback();

                return null;
            }
        }
    }

    @Override
    public ArrayList<Regizor> getAll() {

        logger.traceEntry();

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Regizor> regizors =
                        session.createQuery("from Regizor as m", Regizor.class).
                                //        session.createQuery("select m from Message as m join fetch m.nextMessage order by m.text asc", Message.class). //initializarea obiectelor asociate
                                        list();

                System.out.println(regizors.size() + " regizor(s) found:");

//                for (Ticket t : tickets) {
//                    System.out.println(t);
//                    //  System.out.println(m.getText() + ' ' + m.getId()+" textul urmatorului mesaj ["+m.getNextMessage().getText()+"]");
//                }
                tx.commit();

                logger.traceExit(regizors);
                return new ArrayList<>(regizors);

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to get all regizors " + "" + ": "  + ex);
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
