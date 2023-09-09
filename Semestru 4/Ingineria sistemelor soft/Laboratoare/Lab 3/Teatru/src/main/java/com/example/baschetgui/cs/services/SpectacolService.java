package com.example.baschetgui.cs.services;

import com.example.baschetgui.cs.models.Entity;
import com.example.baschetgui.cs.models.Spectacol;
import com.example.baschetgui.cs.models.Spectator;
import com.example.baschetgui.cs.repositories.SpectacolRepository;

import java.util.ArrayList;

public class SpectacolService {

    SpectacolRepository spectacolRepository;

    public SpectacolService(SpectacolRepository spectacolRepository) {
        this.spectacolRepository = spectacolRepository;
    }

    public boolean save (Spectacol spectacol) {
        return spectacolRepository.save(spectacol);
    }

    public boolean update (Entity e, Spectacol newSpectacol) {
        return spectacolRepository.update(e, newSpectacol);
    }

    public boolean delete (Spectacol spectacol) {
        Entity entity = new Entity();
        entity.setId(spectacol.getId());

        return spectacolRepository.delete(entity);
    }

    public Spectacol find (Spectacol spectacol) {
        Entity entity = new Entity();
        entity.setId(spectacol.getId());

        return spectacolRepository.find(entity);
    }

    public ArrayList<Spectacol> getAll () {
        return spectacolRepository.getAll();
    }
}
