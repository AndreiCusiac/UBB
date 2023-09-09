package repositories;

import models.Entity;

import java.util.ArrayList;

public interface IRepository<E> {
    public boolean save(E e);

    public boolean delete(Entity e);

    public boolean update(Entity e, E newE);

    public E find(Entity e);

    public ArrayList<E> getAll();
}
