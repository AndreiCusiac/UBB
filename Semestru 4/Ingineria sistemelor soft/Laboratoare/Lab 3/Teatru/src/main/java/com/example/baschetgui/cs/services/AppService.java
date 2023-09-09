package com.example.baschetgui.cs.services;

import com.example.baschetgui.cs.Observable;
import com.example.baschetgui.cs.models.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AppService implements Observable {

    Spectator currentSpectator;

    public Spectator getCurrentSpectator() {
        return currentSpectator;
    }

    public void setCurrentSpectator(Spectator currentSpectator) {
        this.currentSpectator = currentSpectator;
    }

    Regizor currentRegizor;

    public Regizor getCurrentRegizor() {
        return currentRegizor;
    }

    public void setCurrentRegizor(Regizor currentRegizor) {
        this.currentRegizor = currentRegizor;
    }

    SpectatorService spectatorService;
    RegizorService regizorService;
    SpectacolService spectacolService;
    LocService locService;

    public AppService(RegizorService regizorService, SpectatorService spectatorService, SpectacolService spectacolService, LocService locService) {
        this.regizorService = regizorService;
        this.spectatorService = spectatorService;
        this.spectacolService = spectacolService;
        this.locService = locService;
    }

    public Spectator getSpectatorByName(String name) {
        Predicate<Spectator> spectatorPredicate = x -> x.getName().equals(name);

        return spectatorService.getAll().
                stream().
                filter(spectatorPredicate).collect(Collectors.toList()).
                get(0);
    }

    public Regizor getRegizorByName(String name) {
        Predicate<Regizor> regizorPredicate = x -> x.getName().equals(name);

        return regizorService.getAll().
                stream().
                filter(regizorPredicate).collect(Collectors.toList()).
                get(0);
    }

    public boolean hasSpectatorByName (String name) {
        Predicate<Spectator> spectatorPredicate = x -> x.getName().equals(name);

        return spectatorService.getAll().
                stream().
                anyMatch(spectatorPredicate);
    }

    public boolean hasRegizorByName (String name) {
        Predicate<Regizor> regizorPredicate = x -> x.getName().equals(name);

        return regizorService.getAll().
                stream().
                anyMatch(regizorPredicate);
    }

    public boolean isAuthSpectatorValid(String currentName, String currentPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {

        if (!hasSpectatorByName(currentName)) {
            return false;
        }

        Spectator spectatorByName = getSpectatorByName(currentName);

        return spectatorByName.getPassword().equals(currentPassword);
    }

    public boolean isAuthRegizorValid(String currentName, String currentPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {

        if (!hasRegizorByName(currentName)) {
            return false;
        }

        Regizor regizorByName = getRegizorByName(currentName);

        return regizorByName.getPassword().equals(currentPassword);
    }

    public ArrayList<Spectacol> getAllSpectacols() {
        return spectacolService.getAll();
    }

    public ArrayList<Spectacol> getAllSpectacolsAccepted() {
        Predicate<Spectacol> spectacolPredicate = x -> {
            assert false;
            return x.getAccepted().equals(true);
        };

        return (ArrayList<Spectacol>) spectacolService.getAll().
                stream().
                filter(spectacolPredicate).collect(Collectors.toList());
    }

    public ArrayList<Spectacol> getAllSpectacolsSuggested() {
        Predicate<Spectacol> spectacolPredicate = x -> {
            assert false;
            return x.getAccepted().equals(false);
        };

        return (ArrayList<Spectacol>) spectacolService.getAll().
                stream().
                filter(spectacolPredicate).collect(Collectors.toList());
    }

    public boolean voteSpectacol(Spectacol spectacolToBeVoted) {
        Spectacol newSpectacol = new Spectacol(spectacolToBeVoted);
        newSpectacol.setVotes(newSpectacol.getVotes() + 1);
        Entity entity = new Entity();
        entity.setId(spectacolToBeVoted.getId());

        var x= spectacolService.update(entity, newSpectacol);

        this.notifyObservers();

        return x;
    }

    public boolean updateSpectacol(Spectacol spectacolUpdated) {
        Spectacol newSpectacol = new Spectacol(spectacolUpdated);
        //newSpectacol.setVotes(newSpectacol.getVotes() + 1);
        Entity entity = new Entity();
        entity.setId(spectacolUpdated.getId());
        System.out.println(spectacolUpdated);

        var x= spectacolService.update(entity, spectacolUpdated);

        this.notifyObservers();

        return x;
    }

    public boolean saveSuggestedSpectacol(Spectacol spectacol) {
        Comparator<Spectacol> idComparator = Comparator.comparing(Spectacol::getId).reversed();

        int numberOfSpectacols;
        //numberOfSpectacols = spectacolService.getAll().size();

        numberOfSpectacols = Integer.parseInt(spectacolService.getAll()
                .stream()
                .sorted(idComparator)
                .collect(Collectors.toList())
                .get(0)
                .getId());

        numberOfSpectacols += 1;
        spectacol.setId(Integer.toString(numberOfSpectacols));

        var x = spectacolService.save(spectacol);

        this.notifyObservers();

        return x;
    }

    public boolean deleteSpectacol(Spectacol spectacol) {
        var x= spectacolService.delete(spectacol);

        this.notifyObservers();

        return x;
    }

    public ArrayList<Loc> getAllLocsReserved() {
        Predicate<Loc> locPredicate = x -> {
            assert false;
            return x.getReserved().equals(true);
        };

        return (ArrayList<Loc>) locService.getAll().
                stream().
                filter(locPredicate).collect(Collectors.toList());
    }

    public Loc getLocBySeat(String seat) {
        Predicate<Loc> locPredicate = x -> x.getSeat().equals(seat);

        return locService.getAll().
                stream().
                filter(locPredicate).collect(Collectors.toList()).
                get(0);
    }

    public void reserveSeats(String name, String email, ArrayList<String> seatsToUpdate) {
        for (var s : seatsToUpdate) {
            Loc locToUpdate = getLocBySeat(s);

            locToUpdate.setName(name);
            locToUpdate.setEmail(email);
            locToUpdate.setReserved(true);
            locToUpdate.setPayed(false);

            Entity e = new Entity();
            e.setId(locToUpdate.getId());

            locService.update(e, locToUpdate);
        }

        this.notifyObservers();
    }

    public void paySeats(String name, String email, ArrayList<String> seatsToUpdate) {
        for (var s : seatsToUpdate) {
            Loc locToUpdate = getLocBySeat(s);

            locToUpdate.setName(name);
            locToUpdate.setEmail(email);
            locToUpdate.setReserved(true);
            locToUpdate.setPayed(true);

            Entity e = new Entity();
            e.setId(locToUpdate.getId());

            locService.update(e, locToUpdate);
        }
        this.notifyObservers();
    }
}
