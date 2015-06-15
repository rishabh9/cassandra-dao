package in.notwork.cassandra.dao;


import in.notwork.cassandra.model.BaseModel;

import java.io.Serializable;

/**
 * @author rishabh.
 */
public interface BaseDao<T extends BaseModel, PK extends Serializable> {

    /**
     * Generic method to get an object based on class and identifier.
     *
     * @param id the identifier (partition key) of the object to get
     * @return a populated object
     */
    T get(PK id);

    /**
     * Checks for existence of an object of type T using the id arg.
     *
     * @param id the id of the entity
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);

    /**
     * Generic method to save an object
     *
     * @param object the object to save
     */
    void save(T object);

    /**
     * Generic method to update an object.
     *
     * @param object the object to update
     */
    void update(T object);

    /**
     * Generic method to delete an object
     *
     * @param object the object to remove
     */
    void remove(T object);

    /**
     * Generic method to delete an object
     *
     * @param id the identifier (partition key) of the object to remove
     */
    void remove(PK id);
}
