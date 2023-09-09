package ro.ubbcluj.map;

//import jdk.incubator.vector.VectorOperators;
import ro.ubbcluj.map.domain.MyModels.Friendship;
import ro.ubbcluj.map.domain.MyModels.User;

import org.junit.jupiter.api.Assertions;
import ro.ubbcluj.map.domain.MyValidators.FriendshipRepoValidator;
import ro.ubbcluj.map.domain.MyValidators.FriendshipValidator;
import ro.ubbcluj.map.domain.MyValidators.UserRepoValidator;
import ro.ubbcluj.map.domain.MyValidators.UserValidator;
import ro.ubbcluj.map.MyRepos.FriendshipFileRepo;
import ro.ubbcluj.map.MyRepos.FriendshipRepo;
import ro.ubbcluj.map.MyRepos.UserFileRepo;
import ro.ubbcluj.map.MyRepos.UserRepo;
import ro.ubbcluj.map.ui.UI;

public class Main {

    public static void testUser() {
        User user = new User("1", "Andrei", "Cusiac");
        Assertions.assertEquals(user.getFirstName(), "Andrei");
        Assertions.assertEquals(user.getLastName(), "Cusiac");
        Assertions.assertEquals(user.getId(), "1");
        user.setId("123");
        Assertions.assertEquals(user.getId(), "123");

        Assertions.assertEquals((double) user.getFriends().size(), 0);

        User user1 = new User("2","Prenume", "Nume", "email@eu.com");
        user1.setId("111");
        user.addFriend(user1);

        User user2 = new User("3","Prenume", "Nume");

        var friends =  user.getFriends();

        Assertions.assertEquals(user.getFriends().size(), 1);
        Assertions.assertEquals(user.getFriends().get(0).getFirstName(), "Prenume");

        user.setId("222");

        Assertions.assertEquals(user.hasFriend(user1), true);
        Assertions.assertEquals(user.hasFriend(user2), false);
    }

    public static void main(String[] args) throws UserRepoValidator, FriendshipRepoValidator {
        teste();

        System.out.println("\nStart\n");

        // creare validatori
        // creare repo
        // creare service
        // service primeste in constructor valid si repo
        // creare ui
        // ui primeste service

        UserValidator userValidator = new UserValidator();
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        UserRepo userRepo = new UserRepo();

        UserFileRepo userFileRepo;
        try {
            userFileRepo = new UserFileRepo("data/user.csv");
        } catch (UserRepoValidator e) {
            System.out.println(e.getMessage());
            return;
        }
        FriendshipRepo friendshipRepo = new FriendshipRepo();

        FriendshipFileRepo friendshipFileRepo;
        try {
            friendshipFileRepo = new FriendshipFileRepo("data/friendship.csv");
        } catch (FriendshipRepoValidator e) {
            System.out.println(e.getMessage());
            return;
        }


        UI ui = new UI(userValidator, friendshipValidator, userFileRepo, friendshipFileRepo);
        ui.meniu();

        System.out.println("Final");
    }

    private static void teste() throws UserRepoValidator {
        testUser();
        testUserValidator();
        testUserRepo();
        testFriendship();
    }

    private static void testFriendship() {
        User user = new User("1", "Andrei", "Cusiac");
        User user1 = new User("2", "Andrei", "Spanac");
        User user2 = new User("3", "Andrei", "Spanac");
        User user3 = new User("4", "Andrei", "Spanac");
        Friendship friendship = new Friendship(user1, user);
        Friendship friendship1 = new Friendship(user, user1);
        Friendship friendship2 = new Friendship(user2, user1);
        Friendship friendship3 = new Friendship(user3, user1);

        Assertions.assertEquals(friendship, friendship1);
        Assertions.assertNotEquals(friendship2, friendship3);
    }

    private static void testUserRepo() throws UserRepoValidator {
        UserRepo userRepo = new UserRepo();

        Assertions.assertEquals(userRepo.getAll().size(), 0);

        userRepo.add(new User("1","Andrei","Cusiac"));
        userRepo.add(new User("2","Andrei","Buimac"));
        userRepo.add(new User("3","Andrei","Spanac"));

        Assertions.assertEquals(userRepo.getAll().size(), 3);
        Assertions.assertEquals(userRepo.getAll().get(1).getLastName(), "Buimac");

        userRepo.delete("1");
        Assertions.assertEquals(userRepo.getAll().size(), 2);

        try {
            userRepo.delete("1");
            Assertions.assertTrue(false);
        } catch (UserRepoValidator e) {
            Assertions.assertTrue(true);
        }

    }

    private static void testUserValidator(){
        UserValidator validator = new UserValidator();

        User user = new User("", "Ana", "Bala", "123");

        try {
            validator.validateUser(user);
            Assertions.assertTrue(false);
        } catch (Exception exception) {
            System.out.println(user);
            System.out.println(exception.getMessage());
            Assertions.assertTrue(true);
        }

        User user1 = new User("abc", "Ana", "Bala", "123");

        try {
            validator.validateUser(user1);
            Assertions.assertTrue(false);
        } catch (Exception exception) {
            System.out.println(user1);
            System.out.println(exception.getMessage());
            Assertions.assertTrue(true);
        }

        User user2 = new User("abc", "Ana", "Bala", "");

        try {
            validator.validateUser(user2);
            Assertions.assertTrue(false);
        } catch (Exception exception) {
            System.out.println(user2);
            System.out.println(exception.getMessage());
            Assertions.assertTrue(true);
        }

        User user3 = new User("123", "na-", "ana", "123");

        try {
            validator.validateUser(user3);
            Assertions.assertTrue(false);
        } catch (Exception exception) {
            System.out.println(user3);
            System.out.println(exception.getMessage());
            Assertions.assertTrue(true);
        }

        User user5 = new User("123", "Ana", "Maria Bogdan ");

        try {
            validator.validateUser(user5);
            Assertions.assertTrue(false);
        } catch (Exception exception) {
            System.out.println(user5);
            System.out.println(exception.getMessage());
            Assertions.assertTrue(true);
        }

        User user4 = new User("123", "Ana-Maria", "Stimimia");

        try {
            validator.validateUser(user4);
            System.out.println(user4);
            Assertions.assertTrue(true);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            Assertions.assertTrue(false);
        }
    }
}
