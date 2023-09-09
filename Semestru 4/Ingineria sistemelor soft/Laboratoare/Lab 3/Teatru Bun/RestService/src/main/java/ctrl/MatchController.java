package ctrl;

import models.Entity;
import models.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.MatchRepository;

import java.util.ArrayList;

//@CrossOrigin
@RestController
@RequestMapping("/matches")
public class MatchController {

    private static final String template = "Hello, %s!";

    @Autowired
    private MatchRepository matchRepository;

    @RequestMapping("/greeting")
    public  String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }


    @RequestMapping( method=RequestMethod.GET)
    public ArrayList<Match> getAll(){
        System.out.println("Get all matches ...");
        return matchRepository.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){
        System.out.println("Get by id "+id);

        Entity e = new Entity();
        e.setId(id);

        Match match = matchRepository.find(e);
        if (match == null)
            return new ResponseEntity<String>("Match not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Match>(match , HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Match create(@RequestBody Match match){
        matchRepository.save(match);
        return match;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Match update(@PathVariable String id, @RequestBody Match match) {
        System.out.println("Updating match ...");
        System.out.println("Id: " + id);
        System.out.println("Match for update: " + match);

        Entity e = new Entity();
        e.setId(id);
        match.setId(id);

        System.out.println("Match for update: " + match);

        matchRepository.update(e,match);
        return match;

    }
    // @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        System.out.println("Deleting match  ... "+ id);
        try {
            Entity e = new Entity();
            e.setId(id);
            matchRepository.delete(e);
            return new ResponseEntity<Match>(HttpStatus.OK);
        }catch (Exception ex){
            System.out.println("Ctrl Delete match exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping("/{id}/home-team")
    public String name(@PathVariable String id){
        Entity e = new Entity();
        e.setId(id);
        Match result= matchRepository.find(e);
        System.out.println("Result ..."+result);

        return result.getHomeTeam();
    }

/*
    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }*/
}
