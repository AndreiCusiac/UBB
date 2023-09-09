package ro.ubbcluj.map.service;

import ro.ubbcluj.map.domain.MyModels.Friendship;
import ro.ubbcluj.map.domain.MyUtils.LongestRoad;
import ro.ubbcluj.map.domain.MyModels.User;
import ro.ubbcluj.map.domain.MyValidators.FriendshipRepoValidator;
import ro.ubbcluj.map.domain.MyValidators.FriendshipValidator;
import ro.ubbcluj.map.domain.MyValidators.UserRepoValidator;
import ro.ubbcluj.map.domain.MyValidators.UserValidator;
import ro.ubbcluj.map.MyRepos.FriendshipRepo;
import ro.ubbcluj.map.MyRepos.UserRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetworkService {
    private UserValidator userValidator;
    private FriendshipValidator friendshipValidator;
    private UserRepo userRepo;
    private FriendshipRepo friendshipRepo;

    /**
     * Constructor NetworkService
     * @param userValidator
     * @param friendshipValidator
     * @param userRepo
     * @param friendshipRepo
     */
    public NetworkService(UserValidator userValidator, FriendshipValidator friendshipValidator, UserRepo userRepo, FriendshipRepo friendshipRepo) {
        this.userValidator = userValidator;
        this.friendshipValidator = friendshipValidator;
        this.userRepo = userRepo;
        this.friendshipRepo = friendshipRepo;
    }

    /**
     * adauga un User pe baza parametrilor sai
     * @param ID
     * @param Prenume
     * @param Nume
     * @param Email
     * @throws Exception, daca apar erori la validare sau adaugare
     */
    public void addUser(String ID, String Prenume, String Nume, String Email) throws Exception {

        User user;

        switch (Email) {
            case "":
                user = new User(ID, Prenume, Nume);
                break;
            default:
                user = new User(ID, Prenume, Nume, Email);
        }

        userValidator.validateUser(user);
        userRepo.add(user);
    }

    public void setFlagsForDeletionOfUser(String ID) throws FriendshipRepoValidator {
        for (var x : friendshipRepo.getAll()) {
            if (x.getId1().equals(ID) || x.getId2().equals(ID)) {
                x.setToBeDeletedFlag(1);
            }
        }
    }

    /**
     * sterge un User pe baza ID-ului sau
     * @param ID
     * @throws UserRepoValidator, daca User-ul nu exista
     */
    public void deleteUser(String ID) throws UserRepoValidator, FriendshipRepoValidator {

        //setFlagsForDeletionOfUser(ID);

        friendshipRepo.deleteFlagged(ID);
        userRepo.delete(ID);
    }

    /**
     * modifica un User pe baza parametrilor sai si a ID-ului vechi
     * @param IDvechi
     * @param IDNou
     * @param Prenume
     * @param Nume
     * @param Email
     * @throws Exception, daca apar erori la stergere, validare sau adaugare
     */
    public void modifyUser(String IDvechi, String IDNou, String Prenume, String Nume, String Email) throws Exception {

        User user;

        switch (Email) {
            case "":
                user = new User(IDNou, Prenume, Nume);
                break;
            default:
                user = new User(IDNou, Prenume, Nume, Email);
        }

        userValidator.validateUser(user);
        userRepo.delete(IDvechi);
        userRepo.add(user);
    }

    /**
     * obtine si returneaza toti Userii existenti
     * @return ArrayList<User>
     */
    public ArrayList<User> getUsers () throws UserRepoValidator {
        return userRepo.getAll();
    }

    /**
     * adauga o prietenie pe baza ID-urilor celor doi Useri
     * @param ID1
     * @param ID2
     * @throws Exception, daca apar erori la validare sau adaugare
     */
    public void addFriendship(String ID1, String ID2) throws Exception {

        User user1 = userRepo.getUser(ID1);
        User user2 = userRepo.getUser(ID2);

        Friendship friendship = new Friendship(user1, user2);

        friendshipValidator.validateFriendship(friendship);
        friendshipRepo.add(friendship);
        //user1.addFriend(user2);
        //user2.addFriend(user1);
    }

    /**
     * sterge prietenie pe baza ID-urilor celor doi Useri
     * @param ID1
     * @param ID2
     * @throws FriendshipRepoValidator
     */
    public void deleteFriendsip(String ID1, String ID2) throws FriendshipRepoValidator {
        friendshipRepo.delete(ID1, ID2);

        //User user1 = userRepo.getUser(ID1);
        //User user2 = userRepo.getUser(ID2);

        //user1.deleteFriend(user2);
        //user2.deleteFriend(user1);
    }

    /**
     * modifica o prietenie pe baza parametrilor User-ilor
     * @param ID1vechi
     * @param ID2vechi
     * @param ID1Nou
     * @param ID2Nou
     * @throws Exception, daca apar erori la stergere, validare sau adaugare
     */
    public void modifyFriendship(String ID1vechi, String ID2vechi, String ID1Nou, String ID2Nou) throws Exception {
        friendshipRepo.delete(ID1vechi, ID2vechi);

        User user1 = userRepo.getUser(ID1vechi);
        User user2 = userRepo.getUser(ID2vechi);

        //user1.deleteFriend(user2);
        //user2.deleteFriend(user1);

        User user1N = userRepo.getUser(ID1Nou);
        User user2N = userRepo.getUser(ID2Nou);

        Friendship friendship = new Friendship(user1N, user2N);

        friendshipValidator.validateFriendship(friendship);
        friendshipRepo.add(friendship);

        //user1N.addFriend(user2N);
        //user2N.addFriend(user1N);
    }

    /**
     * obtine si returneaza toate prieteniile returnate
     * @return
     */
    public ArrayList<Friendship> getFriendships () throws FriendshipRepoValidator {
        return friendshipRepo.getAll();
    }

    /*public ArrayList<ArrayList<User>> getCommunities() {

        var users = userRepo.getAll();

        if (users.size() == 0) {
            return null;
        }

        Map<User, Integer> nodes = new HashMap<>();
        Integer k = 0;

        for (var x : users) {
            nodes.put(x, 0);
        }

        for (var x : users) {
            if (nodes.get(x) == 0) {
                k++;
                DFS(x, nodes, k);
            }
        }

        ArrayList<ArrayList<User>> communities = new ArrayList<>(k);
        for (Integer x = 0 ; x < k ; x++) {
            communities.get(x) = new ArrayList<>();
        }

        for (var x : users) {
            var y = nodes.get(x);
            communities.add(y, );
        }
    }*/

    /**
     * calculeaza si returneaza numarul de comunitati (componente conexe pe baza prieteniilor)
     * @return Integer, nr. de comunitati
     */
    public Integer getNoOfCommunities() throws UserRepoValidator, FriendshipRepoValidator {

        var users = userRepo.getAll();

        if (users.size() < 2) {
            return users.size();
        }

        Map<String, Integer> nodes = new HashMap<>();
        Integer k = 0;

        for (var x : users) {
            nodes.put(x.getId(), 0);
        }

        for (var x : users) {
            if (nodes.get(x.getId()) == 0) {
                k++;
                DFS(x.getId(), nodes, k);
            }
        }

        return k;
    }

    public ArrayList<String> getMostSocialCommunity() throws UserRepoValidator, FriendshipRepoValidator {
        LongestRoad longestRoad = new LongestRoad(userRepo.getAll(), friendshipRepo.getAll());

        return longestRoad.calculate();
    }

    /**
     * functie ajutatoare, parcurge in adancime un graf pe baza relatiilor de prietenie (muchii) intre Useri (noduri)
     * @param userID
     * @param nodes
     * @param k
     */
    private void DFS  (String userID, Map<String, Integer> nodes, Integer k) throws FriendshipRepoValidator {
        nodes.put(userID, k);

        var friendships = friendshipRepo.getAllOfUserID(userID);

        String y;

        for (var x : friendships) {
            if (x.getId1().equals(userID)) {
                y = x.getId2();
            }
            else
            {
                y = x.getId1();
            }

            if (nodes.get(y) == 0) {
                DFS(y, nodes, k);
            }
        }
    }
}
