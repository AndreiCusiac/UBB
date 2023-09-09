package com.example.baschetgui.cs.services;

import com.example.baschetgui.cs.models.Entity;
import com.example.baschetgui.cs.models.Regizor;
import com.example.baschetgui.cs.models.Spectator;
import com.example.baschetgui.cs.repositories.RegizorRepository;
import com.example.baschetgui.cs.repositories.SpectatorRepository;

import java.util.ArrayList;

public class RegizorService {

    RegizorRepository regizorRepository;

    public RegizorService(RegizorRepository regizorRepository) {
        this.regizorRepository = regizorRepository;
    }

    public boolean save (Regizor regizor) {
        return regizorRepository.save(regizor);
    }

    public boolean delete (Regizor regizor) {
        Entity entity = new Entity();
        entity.setId(regizor.getId());

        return regizorRepository.delete(entity);
    }

    public Regizor find (Regizor regizor) {
        Entity entity = new Entity();
        entity.setId(regizor.getId());

        return regizorRepository.find(entity);
    }

    public ArrayList<Regizor> getAll () {
        return regizorRepository.getAll();
    }
}
