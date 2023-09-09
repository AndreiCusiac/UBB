package org.example.common;

import java.io.Serializable;
import java.time.LocalDate;

public class Rezervare implements Serializable {
    private String cnp;
    private String name;
    private LocalDate date;
    private Integer treatment_location;
    private Integer type;
    private String treatment_date;
    private Integer treatment_hour_start;
    private Integer treatment_minute_start;
    private Integer treatment_hour_end;
    private Integer treatment_minute_end;
    private Boolean anulare;

    public Rezervare(String cnp, String name, LocalDate date, Integer treatment_location, Integer type, String treatment_date, Integer treatment_hour_start, Integer treatment_minute_start, Integer treatment_hour_end, Integer treatment_minute_end, Boolean anulare) {
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
        this.anulare = anulare;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTreatment_location(Integer treatment_location) {
        this.treatment_location = treatment_location;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setTreatment_date(String treatment_date) {
        this.treatment_date = treatment_date;
    }

    public void setTreatment_hour_start(Integer treatment_hour_start) {
        this.treatment_hour_start = treatment_hour_start;
    }

    public void setTreatment_minute_start(Integer treatment_minute_start) {
        this.treatment_minute_start = treatment_minute_start;
    }

    public void setTreatment_hour_end(Integer treatment_hour_end) {
        this.treatment_hour_end = treatment_hour_end;
    }

    public void setTreatment_minute_end(Integer treatment_minute_end) {
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

    public Integer getType() {
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

    public Boolean getAnulare() {
        return anulare;
    }

    public void setAnulare(Boolean anulare) {
        this.anulare = anulare;
    }

    @Override
    public String toString() {
        return "Rezervare{" +
                "cnp='" + cnp + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", treatment_location=" + treatment_location +
                ", type=" + type +
                ", treatment_date='" + treatment_date + '\'' +
                ", treatment_hour_start=" + treatment_hour_start +
                ", treatment_minute_start=" + treatment_minute_start +
                ", treatment_hour_end=" + treatment_hour_end +
                ", treatment_minute_end=" + treatment_minute_end +
                ", anulare=" + anulare +
                '}';
    }
}
