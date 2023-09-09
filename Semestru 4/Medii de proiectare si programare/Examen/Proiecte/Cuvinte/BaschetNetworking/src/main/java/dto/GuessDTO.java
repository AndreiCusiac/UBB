package dto;

import java.io.Serializable;

public class GuessDTO implements Serializable {

    String punctaj;
    String guess;

    public GuessDTO(String punctaj, String guess) {
        this.punctaj = punctaj;
        this.guess = guess;
    }

    public String getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(String punctaj) {
        this.punctaj = punctaj;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
}
