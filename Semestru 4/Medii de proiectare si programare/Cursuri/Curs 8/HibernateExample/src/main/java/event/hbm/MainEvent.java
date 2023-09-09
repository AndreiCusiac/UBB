package event.hbm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Date;
import java.util.List;

public class MainEvent {
    private static SessionFactory sessionFactory;

    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exceptie "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public static void main(String ... arg) {
        initialize();

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(new Event("First Event 14 Aprilie 2022", new Date()));
            session.save(new Event("Second Event 14 Aprilie 2022", new Date()));
            session.getTransaction().commit();
        }


         try(Session  session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Event> result = session.createQuery("from Event", Event.class).list();
            for (Event event :  result) {
                System.out.println("Event (" + event.getDate() + ") : " + event.getTitle());
            }
            session.getTransaction().commit();
        }

        close();
    }

}
