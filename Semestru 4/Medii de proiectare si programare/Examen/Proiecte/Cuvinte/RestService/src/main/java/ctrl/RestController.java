package ctrl;

import models.Entity;
import models.Player;
import models.Pozitie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.PozitieRepository;

import java.util.ArrayList;

@CrossOrigin
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/joc")
public class RestController {

    private static final String bigName = "Pozitie";
    private static final String smallName = "pozitie";

    private static final String template = "Hello, %s!";

    @Autowired
    private PozitieRepository pozitieRepository;

    @RequestMapping("/greeting")
    public  String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @RequestMapping("/greeting/{name}")
    public  String greetingName(@PathVariable String name) {
        return String.format(template, name);
    }


    @RequestMapping(method = RequestMethod.POST)
    public Pozitie create(@RequestBody Pozitie entity){

        String poz1 = entity.getPozitie1();
        String poz2 = entity.getPozitie2();
        String poz3 = entity.getPozitie3();

        pozitieRepository.save(entity);
        return entity;

    }

    @RequestMapping( method=RequestMethod.GET)
    public ArrayList<Pozitie> getAll(){
        System.out.println("Get all " + smallName + "(s) ...");
        return pozitieRepository.getAll();
    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public ResponseEntity<?> getById(@PathVariable String id){
//        System.out.println("Get by id "+id);
//
//        Entity e = new Entity();
//        e.setId(id);
//
//        Player entity = pozitieRepository.find(e);
//        if (entity == null)
//            return new ResponseEntity<String>(bigName + " not found", HttpStatus.NOT_FOUND);
//        else
//            return new ResponseEntity<Player>(entity , HttpStatus.OK);
//    }
//
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    public Player update(@PathVariable String id, @RequestBody Player entity) {
//        System.out.println("Updating " + smallName + " ...");
//        System.out.println("Id: " + id);
//        System.out.println(bigName + " for update: " + entity);
//
//        Entity e = new Entity();
//        e.setId(id);
//        entity.setId(id);
//
//        System.out.println(bigName + " for update: " + entity);
//
//        pozitieRepository.update(e,entity);
//        return entity;
//
//    }
//    // @CrossOrigin(origins = "http://localhost:3000")
//    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
//    public ResponseEntity<?> delete(@PathVariable String id){
//        System.out.println("Deleting " + smallName + "  ... "+ id);
//        try {
//            Entity e = new Entity();
//            e.setId(id);
//            pozitieRepository.delete(e);
//            return new ResponseEntity<Player>(HttpStatus.OK);
//        }catch (Exception ex){
//            System.out.println("Ctrl Delete " + smallName + " exception");
//            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @RequestMapping("/{id}/name")
//    public String name(@PathVariable String id){
//        Entity e = new Entity();
//        e.setId(id);
//        Player result= pozitieRepository.find(e);
//        System.out.println("Result ..."+result);
//
//        return result.getName();
//    }

/*
    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }*/
}
