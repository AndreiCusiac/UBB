package proiect.persistence;

import java.util.List;

public interface Repository<ID, Entity> {
    /**
     * gets one entity based on its id
     * @param id - the id of the entity to be returned, id cannot be null
     * @return the entity with the specified id
     */
    Entity getOne(ID id);

    /**
     * gets all entities
     * @return all entities
     */
    List<Entity> getAll();

    /**
     * adds an entity
     * @param entity - cannot be null
     */
    void add(Entity entity);

    /**
     * deletes the entity with the given id
     * @param id - cannot be null
     */
    void delete(ID id);

    /**
     * updates the data in an entity
     * @param entity - cannot be null
     */
    void update(Entity entity);
}
