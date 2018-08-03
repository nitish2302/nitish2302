package dao;

import java.util.Collection;

/**
 * Base interface DAO for all types of concrete DAO implementation.
 * 
 * @author himanshu
 *
 */
public interface BaseDAO<K, E>
{
    /**
     * This method is used to persist an Entity from a repository.
     * 
     * @param entity
     */
    public void persist(E entity);

    /**
     * This method persists all entities passed as a parameter into the
     * repository.
     * 
     * @param entities
     */
    public void persistAll(Collection<E> entities);

    /**
     * This method deletes an entity from a repository.
     * 
     * @param entity
     */
    public void remove(E entity);

    /**
     * This method deletes all entries of the entities from the repository.
     */
    public void removeAll();

    /**
     * This method searches for an entity with the passed id.
     * 
     * @param id
     * @return Instance of the Entity found or null otherwise.
     */
    public E findById(K id);

    /**
     * This method returns all entries of a given entity.
     * 
     * @return
     */
    public Collection<E> findAll();

    /**
     * This method limit number of response entries of a given entity to be
     * searched
     * 
     */
    public Collection<E> findAllFromTo(int from, int to);
}
