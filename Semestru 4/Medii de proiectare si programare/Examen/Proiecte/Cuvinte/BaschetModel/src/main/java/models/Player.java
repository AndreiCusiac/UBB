package models;

import java.util.Objects;

public class Player extends Entity{

    String name;

    String currentGame;

    public Player() {

    }

    public Player(String name, String currentGame) {
        this.name = name;
        this.currentGame = currentGame;
    }

    public Player(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(String currentGame) {
        this.currentGame = currentGame;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", currentGame='" + currentGame + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
