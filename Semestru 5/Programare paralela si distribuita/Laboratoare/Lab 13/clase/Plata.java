package common;

import java.io.Serializable;
import java.time.LocalDate;

public class Plata implements Serializable {
    private String cnp;
    private LocalDate date;
    private Integer sum;

    public Plata(String cnp, LocalDate date, Integer sum) {
        this.cnp = cnp;
        this.date = date;
        this.sum = sum;
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

    @Override
    public String toString() {
        return "Plata{" +
                "cnp='" + cnp + '\'' +
                ", date=" + date +
                ", sum=" + sum +
                '}';
    }
}
