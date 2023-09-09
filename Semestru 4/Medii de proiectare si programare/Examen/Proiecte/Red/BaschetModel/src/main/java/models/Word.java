package models;

public class Word extends Entity{

    String words;
    String letters;

    public Word() {

    }

    public Word(String words, String letters) {
        this.words = words;
        this.letters = letters;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    @Override
    public String toString() {
        return "Word{" +
                "words='" + words + '\'' +
                ", letters='" + letters + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
