package com.example.baschetgui.cs.repositories;

import com.example.baschetgui.cs.models.Entity;
import com.example.baschetgui.cs.models.Loc;
import com.example.baschetgui.cs.models.Spectacol;
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

public class LocHbRepository implements LocRepository{

    private static final Logger logger = LogManager.getLogger();

    public LocHbRepository(Properties props) {
        logger.info("Initializing LocHbRepository with properties: {} ", props);
//        logger.info("Hei spectatorule: {} ", props);
        initialize();
        logger.info("Initializare completa!");
    }

    @Override
    public boolean save(Loc loc) {

        logger.traceEntry("saving loc{} ", loc);

        try(Session session = sessionFactory.openSession()) {

            Transaction tx = null;

            try {
                tx = session.beginTransaction();
//                Message message = new Message("New Hello World " + (new Date()));
//                session.save(spectator);
                session.persist(loc);

                tx.commit();

                logger.trace("Saved 1 instance");

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error at adding Loc " + loc + ": " + ex);
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

        logger.traceEntry("deleting loc with id {}", ide.getId());

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

//                Ticket crit = session.createQuery("from Tickets where id=?", Ticket.class)
//                        .setMaxResults(1)
//                        .uniqueResult();

                Loc crit = new Loc();

                crit.setId(ide.getId());

                System.err.println("To delete loc " + crit.getId());

                session.delete(crit);
//                session.delete();
                tx.commit();
            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to delete loc " + ide.getId() + ": " +ex);
                if (tx != null)
                    tx.rollback();

                return false;
            }
        }

        return true;
    }


    @Override
    public boolean update(Entity e, Loc newLoc) {

        logger.traceEntry("Entering update loc with id {} and new loc {}", e.getId(), newLoc);

        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();

                Loc loc = session.load( Loc.class, e.getId() );

                loc.setName(newLoc.getName());
                loc.setEmail(newLoc.getEmail());
                loc.setSeat(newLoc.getSeat());
                loc.setReserved(newLoc.getReserved());
                loc.setPayed(newLoc.getPayed());

                session.update(loc);
                tx.commit();

            } catch(RuntimeException ex){
                System.err.println("Eroare la update loc: "+ex);
                if (tx!=null)
                    tx.rollback();
                return false;
            }
        }

        return true;
    }

    @Override
    public Loc find(Entity ide) {

        logger.traceEntry("Entering find loc with id {}", ide.getId());

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

//                Ticket crit = session.createQuery("from Tickets where id=?", Ticket.class)
//                        .setMaxResults(1)
//                        .uniqueResult();

                return session.get(Loc.class, ide.getId());

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to find loc " + ide.getId() + ": " + ex);
                if (tx != null)
                    tx.rollback();

                return null;
            }
        }
    }

    @Override
    public ArrayList<Loc> getAll() {

        logger.traceEntry();

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Loc> locs =
                        session.createQuery("from Loc as m", Loc.class).
                                //        session.createQuery("select m from Message as m join fetch m.nextMessage order by m.text asc", Message.class). //initializarea obiectelor asociate
                                        list();
                System.err.println(locs.size() + " loc(s) found:");
//                for (Ticket t : tickets) {
//                    System.out.println(t);
//                    //  System.out.println(m.getText() + ' ' + m.getId()+" textul urmatorului mesaj ["+m.getNextMessage().getText()+"]");
//                }
                tx.commit();

                System.err.println("Locuri citite: "  + locs);
                logger.traceExit(locs);

                return new ArrayList<>(locs);

            } catch (RuntimeException ex) {
                logger.error(ex);
                System.err.println("Error DB trying to get all locs " + "" + ": "  + ex);
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
