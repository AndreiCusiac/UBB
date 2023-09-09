package ro.ubbcluj.map.domain.MyModels;

import java.util.ArrayList;

public class Network_Ignora_ma {
    private ArrayList<User> users;
    private ArrayList<Friendship> friendships;


    public Network_Ignora_ma() {
        this.users = new ArrayList<>();
        this.friendships = new ArrayList<>();
    }

    public void addUser(User user) {

        if (!users.contains(user)) {
            users.add(user);
        }

        for (var i : user.getFriends()) {
            Long key;
            Long value;
            //friendships.(user.getId(), i.getId());
        }
    }

    public boolean hasUser(User user) {
        return users.contains(user);
    }

    public void deleteUser(User user) {
        users.remove(user);
    }

    //public void add
}
