package ro.ubbcluj.map;

//import jdk.incubator.vector.VectorOperators;
import ro.ubbcluj.map.pb1.Student;
import ro.ubbcluj.map.pb1.TestPb1;
import ro.ubbcluj.map.pb2.MyMap;
import ro.ubbcluj.map.pb2.TestPb2;
import ro.ubbcluj.map.pb3.domain.Utilizator;
import ro.ubbcluj.map.pb3.domain.validators.UtilizatorValidator;
import ro.ubbcluj.map.pb3.repository.Repository;
import ro.ubbcluj.map.pb3.repository.file.UtilizatorFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import ro.ubbcluj.map.pb3.repository.memory.InMemoryRepository;

public class Main {

    public static void testUtilizator() {
        Utilizator utilizator = new Utilizator("Andrei", "Cusiac");
        Assertions.assertEquals(utilizator.getFirstName(), "Andrei");
        Assertions.assertEquals(utilizator.getLastName(), "Cusiac");
        Assertions.assertEquals(utilizator.getId(), null);
        utilizator.setId(Long.parseLong("123"));
        Assertions.assertEquals(utilizator.getId(), 123);

        //Assertions.assertEquals((double) utilizator.getFriends().size(), null);

        Utilizator utilizator1 = new Utilizator("Prenume", "Nume");
        utilizator1.setId(111L);
        utilizator.addFriend(utilizator1);

        Utilizator utilizator2 = new Utilizator("Prenume", "Nume");

        var friends =  utilizator.getFriends();

        Assertions.assertEquals(utilizator.getFriends().size(), 1);
        Assertions.assertEquals(utilizator.getFriends().get(0).getFirstName(), "Prenume");


        utilizator2.setId(222L);

        Assertions.assertEquals(utilizator.hasFriend(utilizator1), true);
        Assertions.assertEquals(utilizator.hasFriend(utilizator2), false);
    }

    public static void main(String[] args) {
        Student s1 = new Student("Dan", 4.5f);
        Student s2 = new Student("Ana", 8.5f);
        Student s3 = new Student("Dan", 4.5f);
        Student s4 = new Student("Andrei", 1.1f);
        Student s5 = new Student("Barbu", 10.0f);

        Set<Student> set1 = new HashSet<>();
        set1.addAll(Arrays.asList(s1, s2, s3, s4, s5));
        //System.out.println(set1);

        TestPb1 t1 = new TestPb1();
        //t.main(args);

        TestPb2 t2 = new TestPb2();
        //t2.main();


        Repository<Long, Utilizator> repository = new UtilizatorFile("data/users.csv", new UtilizatorValidator());
        Utilizator utilizator = new Utilizator("Abombănitoarei", "Gică");
        utilizator.setId(4L);

        repository.save(utilizator);

        //testUtilizator();

    }
}
