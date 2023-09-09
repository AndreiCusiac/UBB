package common;

import java.io.Serializable;
import java.time.LocalDate;

public class Plata implements Serializable {
    private String cnp;
    private LocalDate date;
    private Integer sum;
    private Integer treatment_location;
    private Integer type;
    private Rezervare rezervare;

    public Plata(String cnp, LocalDate date, Integer sum, Integer treatment_location, Integer type, Rezervare rezervare) {
        this.cnp = cnp;
        this.date = date;
        this.sum = sum;
        this.treatment_location = treatment_location;
        this.type = type;
        this.rezervare = rezervare;
    }

    public String getCnp() {
        return cnp;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getSum() {
        return sum;
    }

    public Integer getTreatment_location() {
        return treatment_location;
    }

    public Integer getType() {
        return type;
    }

    public Rezervare getRezervare() {
        return rezervare;
    }

    public void setRezervare(Rezervare rezervare) {
        this.rezervare = rezervare;
    }

    @Override
    public String toString() {
        return "Plata{" +
                "cnp='" + cnp + '\'' +
                ", date=" + date +
                ", sum=" + sum +
                ", treatment_location=" + treatment_location +
                ", type=" + type +
                ", rezervare=" + rezervare +
                '}';
    }
}
