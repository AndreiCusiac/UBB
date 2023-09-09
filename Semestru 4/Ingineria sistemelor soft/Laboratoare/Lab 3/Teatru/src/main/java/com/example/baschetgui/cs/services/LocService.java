package com.example.baschetgui.cs.services;

import com.example.baschetgui.cs.models.Entity;
import com.example.baschetgui.cs.models.Loc;
import com.example.baschetgui.cs.models.Spectacol;
import com.example.baschetgui.cs.repositories.LocRepository;
import com.example.baschetgui.cs.repositories.SpectacolRepository;

import java.util.ArrayList;

public class LocService {

    LocRepository locRepository;

    public LocService(LocRepository locRepository) {
        this.locRepository = locRepository;
    }

    public boolean save (Loc loc) {
        return locRepository.save(loc);
    }

    public boolean update (Entity e, Loc newLoc) {
        return locRepository.update(e, newLoc);
    }

    public boolean delete (Loc loc) {
        Entity entity = new Entity();
        entity.setId(loc.getId());

        return locRepository.delete(entity);
    }

    public Loc find (Loc loc) {
        Entity entity = new Entity();
        entity.setId(loc.getId());

        return locRepository.find(entity);
    }

    public ArrayList<Loc> getAll () {
        return locRepository.getAll();
    }
}
