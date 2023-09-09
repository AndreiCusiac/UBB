package com.example.curs9.ubbcluj.map.service;

import com.example.curs9.AppController;
import com.example.curs9.ubbcluj.map.MyRepos.FriendshipRepo;
import com.example.curs9.ubbcluj.map.MyRepos.UserRepo;
import com.example.curs9.ubbcluj.map.domain.MyModels.Friendship;
import com.example.curs9.ubbcluj.map.domain.MyModels.User;
import com.example.curs9.ubbcluj.map.domain.MyUtils.LongestRoad;
import com.example.curs9.ubbcluj.map.domain.MyValidators.FriendshipRepoValidator;
import com.example.curs9.ubbcluj.map.domain.MyValidators.FriendshipValidator;
import com.example.curs9.ubbcluj.map.domain.MyValidators.UserRepoValidator;
import com.example.curs9.ubbcluj.map.domain.MyValidators.UserValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class NetworkService {
    private UserValidator userValidator;
    private FriendshipValidator friendshipValidator;
    private UserRepo userRepo;
    private FriendshipRepo friendshipRepo;
    private String idCurent;

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getIdCurent() {
        return idCurent;
    }

    public void setIdCurent(String idCurent) {
        this.idCurent = idCurent;
    }

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
     * obtine si returneaza toti Userii prieteni cu user
     * @return ArrayList<User>
     */
    public ArrayList<User> getFriendsOf (User user) throws UserRepoValidator, FriendshipRepoValidator {

        ArrayList<Friendship> userFriendships = constructAllFriendshipsWithUsers(getFriendships());

        ArrayList<User> friendsOfUser = new ArrayList<>();

        for (var x : userFriendships) {
            if (x.isStatus_Approved()) {
                if (x.getUser1().equals(user)) {
                    friendsOfUser.add(x.getUser2());
                }
                if (x.getUser2().equals(user)) {
                    friendsOfUser.add(x.getUser1());
                }
            }
        }

        return friendsOfUser;
    }

    /**
     * verifica daca un User exista dupa ID-ul sau
     * @return True/ False
     */
    public boolean hasUserByID (String ID) throws UserRepoValidator {
        Predicate<User> userID = x -> x.getId().equals(ID);

        return userRepo.getAll().
                stream().
                anyMatch(userID);
    }


    /**
     * verifica daca un User exista dupa email-ul sau
     * @return True/ False
     */
    public boolean hasUserByEmail (String email) throws UserRepoValidator {
        Predicate<User> userEmail = x -> x.getEmail().equals(email);

        return userRepo.getAll().
                stream().
                anyMatch(userEmail);
    }

    /**
     * verifica daca un User exista dupa email-ul sau
     * @return User cu email dat
     */
    public User getUserByEmail (String email) throws UserRepoValidator {
        Predicate<User> userEmail = x -> x.getEmail().equals(email);

        return userRepo.getAll().
                stream().
                filter(userEmail).
                toList().
                get(0);
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

    private Friendship constructFriendshipWithUsers (Friendship friendship) throws UserRepoValidator {

        var users = userRepo.getAll();

        User user1 = users.stream()
                .filter(x -> x.getId().equals(friendship.getId1()))
                .findFirst()
                .get();

        User user2 = users.stream()
                .filter(x -> x.getId().equals(friendship.getId2()))
                .findFirst()
                .get();

        return new Friendship(user1, user2, friendship.getDate(), friendship.getStatus());
    }

    private ArrayList<Friendship> constructAllFriendshipsWithUsers (ArrayList<Friendship> friendships) throws UserRepoValidator {

        var users = userRepo.getAll();

        var friendshipsWithUsers = friendships.stream()
                .map(x -> {
            try {
                return constructFriendshipWithUsers(x);
            } catch (UserRepoValidator e) {
                e.printStackTrace();
                return null;
            }
        }).toList();

        return new ArrayList<Friendship>(friendshipsWithUsers);

    }

    public ArrayList<Friendship> getFriendshipsOf (String ID) throws FriendshipRepoValidator, UserRepoValidator {

        Predicate<Friendship> hasUserByID = x -> x.hasUserByID(ID);
        Predicate<Friendship> isApproved = x -> x.isStatus_Approved();
        Predicate<Friendship> hasAll =hasUserByID.and(isApproved);

        var friendshipsOfID =  constructAllFriendshipsWithUsers(friendshipRepo.getAll())
                .stream()
                .filter(hasAll)
                .toList();

        return new ArrayList<Friendship>(friendshipsOfID);
    }

    public ArrayList<Friendship> getFriendshipsOfWithDate (String ID, Integer month) throws FriendshipRepoValidator, UserRepoValidator {

        Predicate<Friendship> hasUserByID = x -> x.hasUserByID(ID);
        Predicate<Friendship> isApproved = x -> x.isStatus_Approved();
        Predicate<Friendship> hasSameMonth = x -> x.getMonthOfFriendship() == month;
        Predicate<Friendship> hasAll =hasUserByID.and(hasSameMonth).and(isApproved);


        var friendshipsOfID =  constructAllFriendshipsWithUsers(friendshipRepo.getAll())
                .stream()
                .filter(hasAll)
                .toList();

        return new ArrayList<Friendship>(friendshipsOfID);
    }

    /**
     * adauga o prietenie pe baza ID-urilor celor doi Useri
     * @param ID1
     * @param ID2
     * @throws Exception, daca apar erori la validare sau adaugare
     */
    public void makeRequest(String ID1, String ID2) throws Exception {

        User user1 = userRepo.getUser(ID1);
        User user2 = userRepo.getUser(ID2);

        Friendship friendship = new Friendship(user1, user2);

        friendshipValidator.validateFriendship(friendship);

        friendship.setStatus_Pending();

        friendshipRepo.add(friendship);
        //user1.addFriend(user2);
        //user2.addFriend(user1);
    }

    /**
     * reaspunde cererii de prietenie dintre doi useri pe baza statusului
     * @param IDtoRespond
     * @param IDwhoSent
     * @throws FriendshipRepoValidator
     */
    public void answerRequest(String IDtoRespond, String IDwhoSent, String status) throws Exception {
        if (!(getPendingRequestsOf(IDtoRespond).contains(new Friendship(IDwhoSent, IDtoRespond))))
        {
            throw new FriendshipRepoValidator("\nCererea nu exista!\n");
        }

        friendshipRepo.delete(IDtoRespond, IDwhoSent);

        if (status.equals("approved")) {
            addFriendship(IDtoRespond, IDwhoSent);
        }
    }

    public ArrayList<Friendship> getPendingRequests() throws FriendshipRepoValidator {
        Predicate<Friendship> isPending = x -> x.isStatus_Pending();

        var pendingRequests = getFriendships().stream()
                .filter(isPending)
                .toList();

        return new ArrayList<>(pendingRequests);
    }

    public ArrayList<Friendship> getPendingRequestsOf(String ID) throws FriendshipRepoValidator {
        Predicate<Friendship> toUser = x -> x.getId2().equals(ID);

        var pendingRequestsOfUser = getPendingRequests().stream()
                .filter(toUser)
                .toList();

        return new ArrayList<>(pendingRequestsOfUser);
    }

    public ArrayList<Friendship> getPendingRequestsSentBy(String ID) throws FriendshipRepoValidator {
        Predicate<Friendship> byUser = x -> x.getId1().equals(ID);

        var pendingRequestsOfUser = getPendingRequests().stream()
                .filter(byUser)
                .toList();

        return new ArrayList<>(pendingRequestsOfUser);
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

    public void addObserver(AppController helloController) {
    }

    /**
     *
     */

}
