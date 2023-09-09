package models;

public class Pozitie extends Entity{

    String pozitie1;

    String pozitie2;

    String pozitie3;

    public Pozitie() {

    }

    public Pozitie(String pozitie1, String pozitie2, String pozitie3) {
        this.pozitie1 = pozitie1;
        this.pozitie2 = pozitie2;
        this.pozitie3 = pozitie3;
    }

    public String getPozitie1() {
        return pozitie1;
    }

    public void setPozitie1(String pozitie1) {
        this.pozitie1 = pozitie1;
    }

    public String getPozitie2() {
        return pozitie2;
    }

    public void setPozitie2(String pozitie2) {
        this.pozitie2 = pozitie2;
    }

    public String getPozitie3() {
        return pozitie3;
    }

    public void setPozitie3(String pozitie3) {
        this.pozitie3 = pozitie3;
    }

    @Override
    public String toString() {
        return "Pozitie{" +
                "pozitie1='" + pozitie1 + '\'' +
                ", pozitie2='" + pozitie2 + '\'' +
                ", pozitie3='" + pozitie3 + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
