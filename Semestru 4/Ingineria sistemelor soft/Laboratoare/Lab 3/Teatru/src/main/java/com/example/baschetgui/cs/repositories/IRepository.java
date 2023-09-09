package com.example.baschetgui.cs.repositories;

import com.example.baschetgui.cs.models.Entity;

import java.util.ArrayList;

public interface IRepository<E> {
    boolean save(E e);

    boolean delete(Entity e);

    E find(Entity e);

    boolean update(Entity e, E newE);

    ArrayList<E> getAll();
}
