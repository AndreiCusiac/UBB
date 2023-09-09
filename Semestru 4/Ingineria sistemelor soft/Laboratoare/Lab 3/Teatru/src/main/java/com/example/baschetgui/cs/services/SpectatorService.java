package com.example.baschetgui.cs.services;

import com.example.baschetgui.cs.models.Entity;
import com.example.baschetgui.cs.models.Spectator;
import com.example.baschetgui.cs.repositories.SpectatorRepository;

import java.util.ArrayList;

public class SpectatorService {

    SpectatorRepository spectatorRepository;

    public SpectatorService(SpectatorRepository spectatorRepository) {
        this.spectatorRepository = spectatorRepository;
    }

    public boolean save (Spectator spectator) {
        return spectatorRepository.save(spectator);
    }

    public boolean delete (Spectator spectator) {
        Entity entity = new Entity();
        entity.setId(spectator.getId());

        return spectatorRepository.delete(entity);
    }

    public Spectator find (Spectator spectator) {
        Entity entity = new Entity();
        entity.setId(spectator.getId());

        return spectatorRepository.find(entity);
    }

    public ArrayList<Spectator> getAll () {
        return spectatorRepository.getAll();
    }
}
