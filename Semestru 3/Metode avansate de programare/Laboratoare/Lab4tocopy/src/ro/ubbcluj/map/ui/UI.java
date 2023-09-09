package ro.ubbcluj.map.ui;

import ro.ubbcluj.map.domain.MyValidators.FriendshipRepoValidator;
import ro.ubbcluj.map.domain.MyValidators.FriendshipValidator;
import ro.ubbcluj.map.domain.MyValidators.UserRepoValidator;
import ro.ubbcluj.map.domain.MyValidators.UserValidator;
import ro.ubbcluj.map.MyRepos.FriendshipRepo;
import ro.ubbcluj.map.MyRepos.UserRepo;
import ro.ubbcluj.map.service.NetworkService;

import java.util.ArrayList;
import java.util.Scanner;

public class UI {

    private Scanner in;
    private NetworkService networkService;

    public UI(UserValidator userValidator, FriendshipValidator friendshipValidator, UserRepo userRepo, FriendshipRepo friendshipRepo) {
        this.in = new Scanner(System.in);
        this.networkService = new NetworkService(userValidator, friendshipValidator, userRepo, friendshipRepo);
    }

    private String optiuni() {
        ArrayList<String> optiuni = new ArrayList<>();
        optiuni.add(new String("1. Adaugati utilizator - tasta 1"));
        optiuni.add(new String("2. Modificati utilizator - tasta 2"));
        optiuni.add(new String("3. Stergeti utilizator - tasta 3"));
        optiuni.add(new String("4. Afisati utilizatori - tasta 4"));
        optiuni.add(new String("5. Adaugati prietenie - tasta 5"));
        optiuni.add(new String("6. Modificati prietenie - tasta 6"));
        optiuni.add(new String("7. Stergeti prietenie - tasta 7"));
        optiuni.add(new String("8. Afisati prietenii - tasta 8"));
        optiuni.add(new String("9. Afisati numarul de comunitati - tasta 9"));

        optiuni.add(new String("0. Exit - tasta 0"));

        String meniu = new String("");

        for (var x : optiuni) {
            meniu = meniu + x + "\n";
        }

        return meniu;
    }

    public void meniu() {
        String s = "";

        while (!s.equals("0")) {
            System.out.println("Doriti sa:");
            System.out.println(optiuni());
            System.out.println("Optiunea este: ");

            s = in.nextLine();

            switch (s) {
                case "1":
                    addUser();
                    break;
                case "2":
                    modifyUser();
                    break;
                case "3":
                    deleteUser();
                    break;
                case "4":
                    showUsers();
                    break;
                case "5":
                    addFriendship();
                    break;
                case "6":
                    modifyFriendship();
                    break;
                case "7":
                    deleteFriendship();
                    break;
                case "8":
                    showFriendships();
                    break;
                case "9":
                    showCommunities();
                    break;
                case "0":
                    System.out.println("\nSunteti sigur ca doriti sa iesiti?\n1.Da - tasta 1\n2.Nu - tasta 0 ");
                    s = in.nextLine();
                    switch (s) {
                        case "1":
                            //System.out.println("Final");
                            return;
                        default:
                            break;
                    }
                    break;
                default:
                    System.out.println("Optiunea data nu este valida!\n");
                    break;
            }
        }
    }

    private void showCommunities() {
        System.out.println("\nAfisare comunitati\n");

        System.out.println("\nNumarul de comunitati este: " + networkService.getNoOfCommunities());
        System.out.println("\n");
    }

    private void addFriendship() {
        System.out.println("\nAdaugare prietenie\n");

        String ID1, ID2;

        var atribute = readingPrietenie();

        ID1 = atribute.get(0);
        ID2 = atribute.get(1);

        try {
            networkService.addFriendship(ID1, ID2);
            System.out.println("\nPrietenia " + ID1 + "-" + ID2 + " a fost adaugat cu succes!\n");
        } catch (FriendshipRepoValidator e) {
            System.out.println("\nPrietenia " + ID1 + "-" + ID2 + " exista deja!\n");
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage());
            System.out.println("Prietenia nu a fost adaugata!\n");
        }
    }

    private void modifyFriendship() {
        System.out.println("\nModificare prietenie\n");

        String ID1V, ID2V, ID1N, ID2N;

        System.out.println("Dati prietenia ce urmeaza sa fie modificata: ");
        System.out.println("ID1:");
        ID1V = in.nextLine();
        System.out.println("ID2:");
        ID2V = in.nextLine();

        System.out.println("\n");
        var atribute = readingPrietenie();

        ID1N = atribute.get(0);
        ID2N = atribute.get(1);

        try {
            networkService.modifyFriendship(ID1V, ID2V, ID1N, ID2N);
            System.out.println("\nPrietenia " + ID1N + "-" + ID2N + " a fost modificata cu succes!\n");
        } catch (FriendshipRepoValidator e) {
            System.out.println("\nPrietenia " + ID1V + "-" + ID2V + " nu exista!\n");
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }
    }

    private void deleteFriendship() {
        System.out.println("\nStergere prietenie\n");

        String ID1, ID2;

        System.out.println("Dati atributele pentru stergere: ");
        System.out.println("ID1:");
        ID1 = in.nextLine();
        System.out.println("ID2:");
        ID2 = in.nextLine();

        try {
            networkService.deleteFriendsip(ID1, ID2);
            System.out.println("\nStergerea prieteniei " + ID1 + "-" + ID2 + " s-a realizat cu succes!\n");
        } catch (FriendshipRepoValidator e) {
            System.out.println(e.getMessage());
        }
    }

    private void showFriendships() {
        System.out.println("\nAfisarea prieteniilor\n");

        if (networkService.getFriendships().size() == 0) {
            System.out.println("Nu exista prietenii!\n");
            return;
        }

        for (var x : networkService.getFriendships()) {
            System.out.println("ID1: " + x.getId1() + " | ID2: " + x.getId2());
        }

        System.out.println("\n");
    }

    private ArrayList<String> reading() {
        ArrayList<String> atribute = new ArrayList<>();

        System.out.println("Dati atributele utilizatorului:");
        System.out.println("ID:");
        atribute.add(in.nextLine());

        System.out.println("Prenume:");
        atribute.add(in.nextLine());

        System.out.println("Nume:");
        atribute.add(in.nextLine());

        System.out.println("Email?\n1. Automat - tasta 1\n2. Explicit - tasta 2");
        String s = in.nextLine();

        switch (s) {
            case "2":
                System.out.println("Email explicit:");
                atribute.add(in.nextLine());
            default:
                atribute.add("");
        }

        return atribute;
    }

    private ArrayList<String> readingPrietenie() {
        ArrayList<String> atribute = new ArrayList<>();

        System.out.println("Dati atributele prieteniei:");
        System.out.println("ID User #1:");
        atribute.add(in.nextLine());

        System.out.println("ID User #2:");
        atribute.add(in.nextLine());

        return atribute;
    }

    private void addUser() {
        System.out.println("\nAdaugare utilizator\n");

        String ID, Prenume, Nume;
        String Email = "";

        var atribute = reading();

        ID = atribute.get(0);
        Prenume = atribute.get(1);
        Nume = atribute.get(2);
        Email = atribute.get(3);

        try {
            networkService.addUser(ID, Prenume, Nume, Email);
            System.out.println("\nUtilizatorul " + ID + " a fost adaugat cu succes!\n");
        } catch (UserRepoValidator e) {
            System.out.println("\nUtilizatorul cu ID-ul " + ID + " exista deja!\n");
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage());
            System.out.println("Utilizatorul nu a fost adaugat!\n");
        }

    }

    private void modifyUser() {
        System.out.println("\nModificare utilizator\n");

        String IDV;

        System.out.println("Dati utilizatorul ce urmeaza sa fie modificat: ");
        System.out.println("ID:");

        IDV = in.nextLine();

        String ID, Prenume, Nume;
        String Email = "";

        System.out.println("\n");
        var atribute = reading();

        ID = atribute.get(0);
        Prenume = atribute.get(1);
        Nume = atribute.get(2);
        Email = atribute.get(3);

        try {
            networkService.modifyUser(IDV, ID, Prenume, Nume, Email);
            System.out.println("\nUtilizatorul " + ID + " a fost modificat cu succes!\n");
        } catch (UserRepoValidator e) {
            System.out.println("\nUtilizatorul " + IDV + " nu exista!\n");
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }
    }

    private void deleteUser() {
        System.out.println("\nStergere utilizator\n");

        String ID;

        System.out.println("Dati atributele pentru stergere: ");
        System.out.println("ID:");

        ID = in.nextLine();

        try {
            networkService.deleteUser(ID);
            System.out.println("\nStergerea utilizatorului " + ID + " s-a realizat cu succes!\n");
        } catch (UserRepoValidator | FriendshipRepoValidator e) {
            System.out.println(e.getMessage());
        }
    }

    private void showUsers() {
        System.out.println("\nAfisarea utilizatorilor\n");

        if (networkService.getUsers().size() == 0) {
            System.out.println("Nu exista utilizatori!\n");
            return;
        }

        for (var x : networkService.getUsers()) {
            System.out.println("ID: " + x.getId() + " | Prenume: " + x.getFirstName() + " | Nume: " + x.getLastName() + " | Email: " + x.getEmail());
        }

        System.out.println("\n");
    }
}
