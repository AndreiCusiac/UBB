package ro.ubbcluj.map.domain.MyModels;

import java.util.Objects;

public class Friendship {

    private User user1;
    private String id1;
    private User user2;
    private String id2;
    private Integer toBeDeletedFlag;

    /**
     * Constructor Friendship
     * @param user1
     * @param user2
     */
    public Friendship(User user1, User user2) {
        this.user1 = user1;
        this.id1 = user1.getId();
        this.user2 = user2;
        this.id2 = user2.getId();
        this.toBeDeletedFlag = 0;
    }

    public Friendship(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
        this.toBeDeletedFlag = 0;
    }

    public boolean hasUserByID (String ID) {
        return id1.equals(ID) || id2.equals(ID);
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public Integer getToBeDeletedFlag() {
        return toBeDeletedFlag;
    }

    public void setToBeDeletedFlag(Integer toBeDeletedFlag) {
        this.toBeDeletedFlag = toBeDeletedFlag;
    }

    /**
     * getter User1
     * @return user
     */
    public User getUser1() {
        return user1;
    }

    /**
     * setter User1
     * @param user1
     */
    public void setUser1(User user1) {
        this.user1 = user1;
    }

    /**
     * getter User2
     * @return user
     */
    public User getUser2() {
        return user2;
    }

    /**
     * setter User2
     * @param user2
     */
    public void setUser2(User user2) {
        this.user2 = user2;
    }

    /**
     * stabilirea Friendship-urilor identice
     * @param o
     * @return true, daca Friendship o este identic cu obiectul curent, false altfel
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(user1, that.user1) && Objects.equals(user2, that.user2) || Objects.equals(user2, that.user1) && Objects.equals(user1, that.user2);
    }

    /**
     * stabileste si returneaza hash-ul Friendship-ului curent
     * @return int, hash-ul obiectului curent
     */
    @Override
    public int hashCode() {
        return Objects.hash(user1, user2);
    }
}
