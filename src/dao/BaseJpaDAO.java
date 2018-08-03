package dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.StoredProcedureQuery;

import dao.BaseDAO;

/**
 * Base DAO interface containing all the service methods for JPA entities
 * 
 * @author himanshu
 *
 */
public interface BaseJpaDAO<K, E> extends BaseDAO<K, E>
{

    /**
     * This method first merges the entity with the context and then flushes the
     * entity.
     * 
     * @param entity
     * @return Managed Instance of the entity.
     */
    public E saveOrUpdate(E entity);

    /**
     * Searches for entities with a named query.
     * 
     * @param query
     * @param parameters
     * @return
     */
    public List<E> findAllByNamedQuery(String query, Map<String, ?> parameters);

    /**
     * 
     * 
     * @param query
     * @param parameters
     * @return
     */
    public E findByNamedQuery(String query, Map<String, ?> parameters);

    /**
     * 
     * @param query
     * @param parameters
     * @param flushModeType
     * @return
     */
    public E findByNamedQuery(String query, Map<String, ?> parameters, FlushModeType flushModeType,
            LockModeType lockMode);

    /**
     * 
     * @param query
     * @param parameters
     * @param flushModeType
     * @param lockMode
     * @return
     */
    public StoredProcedureQuery executeStoredProcedure(String query, Map<String, ?> parameters,
            final FlushModeType flushModeType, final LockModeType lockMode);

    /**
     * This method synchronizes the entity with the underlying database.
     */
    public void flush();

    /**
     * This method merges the passed entity into the repository and returns the
     * managed instance of the entity.
     * 
     * @param entity
     * @return Managed instance of the entity.
     */
    public E merge(E entity);

    /**
     * This method persists all entities passed as a parameter into the
     * repository.
     * 
     * @param entities
     */
    public void persistAll(Collection<E> entities);

    /**
     * This method refreshes the entities in the repository
     * 
     * @param entity
     */
    public void refresh(E entity);

    /**
     * This method deletes all entries of the entities from the repository.
     */
    public void removeAll();

    /**
     * This method sets the entity manager for further transactions.
     * 
     * @param entityManager
     */
    public void setEntityManager(EntityManager entityManager);

    /**
     * 
     * @param query
     * @param parameters
     * @param offset
     * @param limit
     * @return
     */
    public List<E> findAllByNamedQuery(String query, Map<String, ?> parameters, int offset, int limit);

    public void saveOrUpdateAll(Collection<E> entities);

    public E saveOrUpdate(E entity, boolean flushFlag);
}
