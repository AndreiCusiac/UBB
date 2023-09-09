package com.example.curs9.ubbcluj.observer;


public interface Observer<E extends Event> {
    void update(E e);
}