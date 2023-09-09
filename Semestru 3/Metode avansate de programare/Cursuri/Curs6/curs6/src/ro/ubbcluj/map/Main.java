package ro.ubbcluj.map;

import ro.ubbcluj.map.domain.Utilizator;
import ro.ubbcluj.map.domain.validators.UtilizatorValidator;
import ro.ubbcluj.map.repository.Repository;
import ro.ubbcluj.map.repository.UtilizatorDbRepository;

public class Main {

    public static void main(String[] args) {
	// write your code here
        // jdbc:postgresql://localhost:5432/academic
        System.out.println("ok");

        Repository<Long, Utilizator> repoDb = new UtilizatorDbRepository("jdbc:postgresql://localhost:5432/academic","postgres", "1234", new UtilizatorValidator());

        //Utilizator utilizator = new Utilizator("f1", "l1");
        //repoDb.save(utilizator);

        // vezi update/ delete in Curs6
        repoDb.findAll().forEach(System.out::println);
    }
}
