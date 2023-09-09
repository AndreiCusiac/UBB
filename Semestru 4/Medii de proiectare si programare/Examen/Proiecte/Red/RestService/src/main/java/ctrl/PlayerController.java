package ctrl;

import models.Entity;
import models.Match;
import models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.PlayerRepository;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/players")
public class PlayerController {

    private static final String template = "Hello, %s!";

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping("/greeting")
    public  String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @RequestMapping("/greeting/{name}")
    public  String greetingName(@PathVariable String name) {
        return String.format(template, name);
    }


    @RequestMapping( method=RequestMethod.GET)
    public ArrayList<Player> getAll(){
        System.out.println("Get all matches ...");
        return playerRepository.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){
        System.out.println("Get by id "+id);

        Entity e = new Entity();
        e.setId(id);

        Player player = playerRepository.find(e);
        if (player == null)
            return new ResponseEntity<String>("Player not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Player>(player , HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Player create(@RequestBody Player player){
        playerRepository.save(player);
        return player;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Player update(@PathVariable String id, @RequestBody Player player) {
        System.out.println("Updating player ...");
        System.out.println("Id: " + id);
        System.out.println("Match for update: " + player);

        Entity e = new Entity();
        e.setId(id);
        player.setId(id);

        System.out.println("Match for update: " + player);

        playerRepository.update(e,player);
        return player;

    }
    // @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        System.out.println("Deleting match  ... "+ id);
        try {
            Entity e = new Entity();
            e.setId(id);
            playerRepository.delete(e);
            return new ResponseEntity<Match>(HttpStatus.OK);
        }catch (Exception ex){
            System.out.println("Ctrl Delete match exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/{id}/name")
    public String name(@PathVariable String id){
        Entity e = new Entity();
        e.setId(id);
        Player result= playerRepository.find(e);
        System.out.println("Result ..."+result);

        return result.getName();
    }


/*
    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }*/
}
