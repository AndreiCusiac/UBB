package com.example.baschetgui.cs.repositories;

import com.example.baschetgui.cs.models.Entity;
import com.example.baschetgui.cs.models.Spectacol;
import com.example.baschetgui.cs.models.Spectator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class SpectacolHbRepository implements SpectacolRepository{

    private static final Logger logger = LogManager.getLogger();

    public SpectacolHbRepository(Properties props) {
        logger.info("Initializing SpectacolHbRepository with properties: {} ", props);
//        logger.info("Hei spectatorule: {} ", props);
        initialize();
        logger.info("Initializare completa!");
    }

    @Override
    public boolean save(Spectacol spectacol) {

        logger.traceEntry("saving spectacol{} ", spectacol);

        try(Session session = sessionFactory.openSession()) {

            Transaction tx = null;

            try {
                tx = session.beginTransaction();
//                Message message = new Message("New Hello World " + (new Date()));
//                session.save(spectator);
                session.persist(spectacol);

                tx.commit();

                logger.trace("Saved 1 instance");

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error at adding Spectacol " + spectacol + ": " + ex);
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

        logger.traceEntry("deleting spectacol with id {}", ide.getId());

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

//                Ticket crit = session.createQuery("from Tickets where id=?", Ticket.class)
//                        .setMaxResults(1)
//                        .uniqueResult();

                Spectacol crit = new Spectacol();

                crit.setId(ide.getId());

                System.err.println("To delete spectacol " + crit.getId());

                session.delete(crit);
//                session.delete();
                tx.commit();
            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to delete spectacol " + ide.getId() + ": " +ex);
                if (tx != null)
                    tx.rollback();

                return false;
            }
        }

        return true;
    }


    @Override
    public boolean update(Entity e, Spectacol newSpectacol) {

        logger.traceEntry("Entering update spectacol with id {} and new spectacol {}", e.getId(), newSpectacol);

        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();

                Spectacol spectacol = session.load( Spectacol.class, e.getId() );

                spectacol.setName(newSpectacol.getName());
                spectacol.setAuthor(newSpectacol.getAuthor());
                spectacol.setDate(newSpectacol.getDate());
                spectacol.setSuggestions(newSpectacol.getSuggestions());
                spectacol.setVotes(newSpectacol.getVotes());
                spectacol.setActors(newSpectacol.getActors());
                spectacol.setAccepted(newSpectacol.getAccepted());

                session.update(spectacol);
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
    public Spectacol find(Entity ide) {

        logger.traceEntry("Entering find spectacol with id {}", ide.getId());

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

//                Ticket crit = session.createQuery("from Tickets where id=?", Ticket.class)
//                        .setMaxResults(1)
//                        .uniqueResult();

                return session.get(Spectacol.class, ide.getId());

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to find spectacol " + ide.getId() + ": " + ex);
                if (tx != null)
                    tx.rollback();

                return null;
            }
        }
    }

    @Override
    public ArrayList<Spectacol> getAll() {

        logger.traceEntry();

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Spectacol> spectacols =
                        session.createQuery("from Spectacol as m", Spectacol.class).
                                //        session.createQuery("select m from Message as m join fetch m.nextMessage order by m.text asc", Message.class). //initializarea obiectelor asociate
                                        list();
                System.err.println(spectacols.size() + " spectacol(s) found:");
//                for (Ticket t : tickets) {
//                    System.out.println(t);
//                    //  System.out.println(m.getText() + ' ' + m.getId()+" textul urmatorului mesaj ["+m.getNextMessage().getText()+"]");
//                }
                tx.commit();

                System.err.println("Spectacole citite: "  + spectacols);
                logger.traceExit(spectacols);

                return new ArrayList<>(spectacols);

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to get all spectacols " + "" + ": "  + ex);
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
