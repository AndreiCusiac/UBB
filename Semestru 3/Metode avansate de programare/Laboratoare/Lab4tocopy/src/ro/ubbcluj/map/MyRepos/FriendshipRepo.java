package ro.ubbcluj.map.MyRepos;

import ro.ubbcluj.map.domain.MyModels.Friendship;
import ro.ubbcluj.map.domain.MyModels.User;
import ro.ubbcluj.map.domain.MyValidators.FriendshipRepoValidator;

import java.util.*;

public class FriendshipRepo {

    private ArrayList<Friendship> friendships;

    /**
     * Constructor FriendshipRepo
     */
    public FriendshipRepo() {
        this.friendships = new ArrayList<>();
    }

    /**
     * Verifica daca un Friendship exista in obiectul curent
     * @param friendship
     * @return true, daca friendship exista, false altfel
     */
    private boolean exists(Friendship friendship) {
        for (var x : friendships) {
            if ((x.getId1().equals(friendship.getId1()) && x.getId2().equals(friendship.getId2())) || (x.getId2().equals(friendship.getId1()) && x.getId1().equals(friendship.getId2()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * adauga un Friendship in obiectul curent
     * @param friendship
     * @throws FriendshipRepoValidator, daca Friendship-ul exista deja
     */
    public void add(Friendship friendship) throws FriendshipRepoValidator {
        if (exists(friendship)) {
            throw new FriendshipRepoValidator("\nPrietenia exista deja!\n");
        }

        friendships.add(friendship);
    }

    /**
     * sterge un Friendship din obiectul curent, pe baza ID-urilor userilor
     * @param ID1
     * @param ID2
     * @throws FriendshipRepoValidator, daca Friendship-ul nu exista
     */
    public void delete(String ID1, String ID2) throws FriendshipRepoValidator {
        ArrayList<Friendship> friendships1 = new ArrayList<>();
        for (var x : friendships) {
            if (!(x.hasUserByID(ID1) && x.hasUserByID(ID2))) {
                friendships1.add(x);
            }
        }

        if (friendships == friendships1) {
            throw new FriendshipRepoValidator("\nPrietenia dintre " + ID1 + " si " + ID2 + " nu exista!\n");
        }
        else
        {
            friendships = friendships1;
        }
    }

    /**
     * verifica daca un Friendship exista in obiectul curent
     * @param friendship
     * @return true, daca exista, false altfel
     */
    public boolean has(Friendship friendship) {
        return exists(friendship);
    }

    /**
     * returneaza toate prieteniile memorate
     * @return ArrayList<Friendship>
     */
    public ArrayList<Friendship> getAll() {
        return friendships;
    }

    /**
     * obtine si returneaza toate prieteniile care contin un User
     * @param user
     * @return ArrayList<Friendship>
     */
    public ArrayList<Friendship> getAllOf(User user) {

        ArrayList<Friendship> friendshipsOfUser = new ArrayList<>();

        for (var x : friendships) {
            if (x.hasUserByID(user.getId())) {
                friendshipsOfUser.add(x);
            }
        }

        return friendshipsOfUser;
    }

    public void deleteFlagged(String ID) throws FriendshipRepoValidator {
        Map<String, String> friendshipsToDelete = new HashMap<>();

        for (var y : friendships) {
            if (y.hasUserByID(ID) == true) {
                friendshipsToDelete.put(y.getId1(), y.getId2());
            }
        }

        for (var x : friendshipsToDelete.entrySet()) {
            delete(x.getKey(),x.getValue());
        }
    }
}
