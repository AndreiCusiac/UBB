package com.example.baschetgui.cs.models;

import java.util.Date;
import java.util.Objects;

public class Spectacol extends Entity {

    String name;
    String author;
    String suggestions;

    Date date;
    Integer votes;

    String actors;
    Boolean accepted;

    public Spectacol () {

    }

    public Spectacol(String name, String author, String suggestions, Date date, Integer votes, String actors, Boolean accepted) {
        this.name = name;
        this.author = author;
        this.suggestions = suggestions;
        this.date = date;
        this.votes = votes;
        this.actors = actors;
        this.accepted = accepted;
    }

    public Spectacol(String name, String author, String suggestions) {
        this.name = name;
        this.author = author;
        this.suggestions = suggestions;
    }

    public Spectacol(String name, String author, String suggestions, Integer votes) {
        this.name = name;
        this.author = author;
        this.suggestions = suggestions;
        this.votes = votes;
    }

    public Spectacol(String name, String author, Date date, String actors) {
        this.name = name;
        this.author = author;
        this.date = date;
        this.actors = actors;
    }

    public Spectacol(String name, String author, Date date, String actors, Boolean accepted) {
        this.name = name;
        this.author = author;
        this.date = date;
        this.actors = actors;
        this.accepted = accepted;
    }

    public Spectacol(Spectacol newSpectacol) {
        this.name = newSpectacol.getName();
        this.author = newSpectacol.getAuthor();
        this.suggestions = newSpectacol.getSuggestions();
        this.date = newSpectacol.getDate();
        this.votes = newSpectacol.getVotes();
        this.actors = newSpectacol.getActors();
        this.accepted = newSpectacol.getAccepted();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spectacol spectacol = (Spectacol) o;
        return Objects.equals(name, spectacol.name) && Objects.equals(author, spectacol.author) && Objects.equals(suggestions, spectacol.suggestions) && Objects.equals(date, spectacol.date) && Objects.equals(votes, spectacol.votes) && Objects.equals(actors, spectacol.actors) && Objects.equals(accepted, spectacol.accepted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, suggestions, date, votes, actors, accepted);
    }

    @Override
    public String toString() {
        return "Spectacol{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", suggestions='" + suggestions + '\'' +
                ", date=" + date +
                ", votes=" + votes +
                ", actors='" + actors + '\'' +
                ", accepted=" + accepted +
                '}';
    }
}
