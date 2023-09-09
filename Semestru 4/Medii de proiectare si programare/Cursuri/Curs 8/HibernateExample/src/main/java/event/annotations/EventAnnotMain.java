package event.annotations;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Date;
import java.util.List;

public class EventAnnotMain {
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
        // create a couple of events...
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(new EventAnnot("First EventAnnot 14 aprilie 2022", new Date()));
            session.save(new EventAnnot("Second EventAnnot 14 aprilie 2022", new Date()));
            session.getTransaction().commit();
        }
        // now lets pull events from the database and list them
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<EventAnnot> result = session.createQuery("from EventAnnot",EventAnnot.class).list();
            for (EventAnnot event :  result) {
                System.out.println("EventAnnot (" + event.getDate() + ") : " + event.getTitle());
            }
            session.getTransaction().commit();
        }
        close();
    }

}
