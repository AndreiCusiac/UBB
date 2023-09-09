package repositories;

import models.Entity;
import models.Player;
import models.MyString;
import models.Word;
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

public class WordHbRepository implements WordRepository{

    private static final Logger logger = LogManager.getLogger();

    public WordHbRepository(Properties props) {
        logger.info("Initializing WordHbRepository with properties: {} ", props);
        initialize();
    }

    @Override
    public boolean save(Word myString) {

        logger.traceEntry("saving word{} ", myString);

        try(Session session = sessionFactory.openSession()) {

            Transaction tx = null;

            try {
                tx = session.beginTransaction();
//                Message message = new Message("New Hello World " + (new Date()));
//                session.save(word);
                session.persist(myString);

                tx.commit();

                logger.trace("Saved 1 instance");

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error at adding Word " + myString + ": " + ex);
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

        logger.traceEntry("deleting word with id {}", ide.getId());

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

//                Ticket crit = session.createQuery("from Tickets where id=?", Ticket.class)
//                        .setMaxResults(1)
//                        .uniqueResult();

                Player crit = new Player();

                crit.setId(ide.getId());

                System.err.println("To delete word " + crit.getId());

                session.delete(crit);
//                session.delete();
                tx.commit();
            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to delete word " + ide.getId() + ": " +ex);
                if (tx != null)
                    tx.rollback();

                return false;
            }
        }

        return true;
    }

    @Override
    public boolean update(Entity e, Word newE) {
        logger.traceEntry("Entering update word with id {} and new word {}", e.getId(), newE);

        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();

                Word myString = session.load( Word.class, e.getId() );

                myString.setWords(newE.getWords());
                myString.setLetters(newE.getLetters());

                session.update(myString);
                tx.commit();

            } catch(RuntimeException ex){
                System.err.println("Eroare la update word: "+ex);
                if (tx!=null)
                    tx.rollback();
                return false;
            }
        }

        return true;
    }

    @Override
    public Word find(Entity ide) {

        logger.traceEntry("Entering find word with id {}", ide.getId());

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

//                Ticket crit = session.createQuery("from Tickets where id=?", Ticket.class)
//                        .setMaxResults(1)
//                        .uniqueResult();

                return (Word) session.get(Word.class, ide.getId());

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to find word " + ide.getId() + ": " + ex);
                if (tx != null)
                    tx.rollback();

                return null;
            }
        }
    }

    @Override
    public ArrayList<Word> getAll() {

        logger.traceEntry();

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Word> myStrings =
                        session.createQuery("from Word as m", Word.class).
                                //        session.createQuery("select m from Message as m join fetch m.nextMessage order by m.text asc", Message.class). //initializarea obiectelor asociate
                                        list();

                System.out.println(myStrings.size() + " word(s) found:");

//                for (Ticket t : words) {
//                    System.out.println(t);
//                    //  System.out.println(m.getText() + ' ' + m.getId()+" textul urmatorului mesaj ["+m.getNextMessage().getText()+"]");
//                }
                tx.commit();

                logger.traceExit(myStrings);
                return new ArrayList<>(myStrings);

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to get all words " + "" + ": "  + ex);
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
