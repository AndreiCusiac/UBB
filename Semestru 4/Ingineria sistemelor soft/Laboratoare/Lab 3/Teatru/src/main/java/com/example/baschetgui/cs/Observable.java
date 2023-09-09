package com.example.baschetgui.cs;

import java.util.ArrayList;

public interface Observable {
    ArrayList<Observer> toNotify = new ArrayList<>();

    default void addObserver(Observer observer) {
        toNotify.add(observer);
    }

    default void removeObserver(Observer observer) {
        toNotify.remove(observer);
    }

    default void notifyObservers() {
        toNotify.forEach(Observer::update);
    }
}
