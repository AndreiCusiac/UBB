package common;

import java.io.Serializable;
import java.time.LocalDate;

public class Rezervare implements Serializable {
    private String cnp;
    private String name;
    private LocalDate date;
    private Integer treatment_location;
    private String type;
    private String treatment_date;
    private Integer treatment_hour_start;
    private Integer treatment_minute_start;
    private Integer treatment_hour_end;
    private Integer treatment_minute_end;

    public Rezervare(String cnp, String name, LocalDate date, Integer treatment_location, String type, String treatment_date, Integer treatment_hour_start, Integer treatment_minute_start, Integer treatment_hour_end, Integer treatment_minute_end) {
        this.cnp = cnp;
        this.name = name;
        this.date = date;
        this.treatment_location = treatment_location;
        this.type = type;
        this.treatment_date = treatment_date;
        this.treatment_hour_start = treatment_hour_start;
        this.treatment_minute_start = treatment_minute_start;
        this.treatment_hour_end = treatment_hour_end;
        this.treatment_minute_end = treatment_minute_end;
    }

    public String getCnp() {
        return cnp;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getTreatment_location() {
        return treatment_location;
    }

    public String getType() {
        return type;
    }

    public String getTreatment_date() {
        return treatment_date;
    }

    public Integer getTreatment_hour_start() {
        return treatment_hour_start;
    }

    public Integer getTreatment_minute_start() {
        return treatment_minute_start;
    }

    public Integer getTreatment_hour_end() {
        return treatment_hour_end;
    }

    public Integer getTreatment_minute_end() {
        return treatment_minute_end;
    }

    @Override
    public String toString() {
        return "Rezervare{" +
                "cnp='" + cnp + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", treatment_location='" + treatment_location + '\'' +
                ", type='" + type + '\'' +
                ", treatment_date='" + treatment_date + '\'' +
                ", treatment_hour_start=" + treatment_hour_start +
                ", treatment_minute_start=" + treatment_minute_start +
                ", treatment_hour_end=" + treatment_hour_end +
                ", treatment_minute_end=" + treatment_minute_end +
                '}';
    }
}
