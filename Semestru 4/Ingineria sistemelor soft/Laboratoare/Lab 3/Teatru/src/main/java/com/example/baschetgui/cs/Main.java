package com.example.baschetgui.cs;

import com.example.baschetgui.cs.tests.MatchTests;
import com.example.baschetgui.cs.tests.TicketBoothsTests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
	// write your code here
        MatchTests matchTests = new MatchTests();
        matchTests.matchTests();

        TicketBoothsTests ticketBoothsTests = new TicketBoothsTests();
        ticketBoothsTests.ticketBoothsTests();

        System.out.println("Merge");

        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

//        MatchDbRepository matchDbRepository = new MatchDbRepository(props);
//        TicketDbRepository ticketDbRepository = new TicketDbRepository(props);

//        var allMatches = matchDbRepository.getAll();
/*
        for (Match match : allMatches) {
            System.out.println(match);
        }*/

        /*for (Match match : allMatches) {
            Ticket ticket = new Ticket(String.valueOf(ticketDbRepository.getAll().size() + 1), match.getId(), "Andrei", 2);
            ticketDbRepository.save(ticket);

            Ticket ticket1 = new Ticket(String.valueOf(ticketDbRepository.getAll().size() + 1), match.getId(), "Ardei", 3);
            ticketDbRepository.save(ticket1);
        }*/

//        var allTickets = ticketDbRepository.getAll();
/*
        for (Ticket ticket : allTickets) {
            System.out.println(ticket);
        }*/

        /*Entity entity = new Entity();

        entity.setId("1");
        ticketDbRepository.delete(entity);

        entity.setId("2");
        ticketDbRepository.delete(entity);

        entity.setId("3");
        ticketDbRepository.delete(entity);

        entity.setId("4");
        ticketDbRepository.delete(entity);

        allTickets = ticketDbRepository.getAll();

        for (Ticket ticket : allTickets) {
            System.out.println(ticket);
        }*/

//        Match match = new Match("Home", "Away", 20d, 300);

    }
}
