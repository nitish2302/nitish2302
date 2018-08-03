package daoimpl;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.BaseJpaDAO;

/**
 * Abstract JPA Implementation of {@link BaseJpaDAO}.
 * 
 * @author himanshu
 *
 */
public abstract class BaseJpaDAOImpl<K, E> implements BaseJpaDAO<K, E>
{
    private static final Logger logger = LoggerFactory.getLogger(BaseJpaDAOImpl.class);
    private static final int BATCH_SIZE = 100;

    /**
     * The persistent Context
     */

    @PersistenceContext(name="")
    private EntityManager entityManager;

    /**
     * The entity type.
     */
    protected Class<E> entityClass;

    public EntityManager getEntityManager()
    {
        return this.entityManager;
    }

    public void setEntityManager(final EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    public BaseJpaDAOImpl()
    {
        ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperClass.getActualTypeArguments()[1];

    }

    public Collection<E> findAll()
    {
        try
        {
            logger.debug("Find all entities recorded of class " + entityClass.getSimpleName());
            return getEntityManager().createQuery("SELECT h FROM " + entityClass.getName() + " h ORDER BY h.id")
                    .getResultList();
        }
        catch (NoResultException nre)
        {
            logger.debug("No result returned for find all by ids.");
        }
        return new ArrayList<E>();

    }

    public List<E> findAllByNamedQuery(final String query, final Map<String, ?> parameters)
    {
        try
        {
            logger.debug("Find limited entities recorded of class " + entityClass.getSimpleName());
            return getFormedNamedQuery(query, parameters, null, null).getResultList();
        }
        catch (NoResultException nre)
        {
            logger.debug("No result returned for find all by ids.");
        }
        return new ArrayList<E>();
    }

    public Collection<E> findAllFromTo(final int from, final int to)
    {

        try
        {
            logger.debug("Find limited entities recorded of class " + entityClass.getSimpleName());
            return getEntityManager().createQuery("SELECT h FROM " + entityClass.getName()).setFirstResult(from)
                    .setMaxResults(to).getResultList();
        }
        catch (NoResultException nre)
        {
            logger.debug("No result returned for find all by ids.");
        }
        return new ArrayList<E>();

    }

    public E findById(final K id)
    {
        try
        {
            logger.debug("Find entity " + entityClass.getSimpleName() + " by id " + id);
            return getEntityManager().find(entityClass, id);
        }
        catch (NoResultException nre)
        {
            logger.debug("No result returned for find all by ids.");
        }
        return null;

    }

    public List<E> findByIds(final Collection<String> ids)
    {
        try
        {
            logger.debug("Find entity " + entityClass.getSimpleName() + " by id " + ids);
            return getEntityManager().createQuery("FROM " + entityClass.getName() + " WHERE id in :idList", entityClass)
                    .setParameter("idList", ids).getResultList();
        }
        catch (NoResultException nre)
        {
            logger.debug("No result returned for find all by ids.");
        }
        return new ArrayList<E>();
    }

    public E findByNamedQuery(final String query, final Map<String, ?> parameters)
    {
        try
        {
            return getFormedNamedQuery(query, parameters, null, null).getSingleResult();
        }
        catch (NoResultException nre)
        {
            logger.debug(
                    "No result returned for query '" + query + "'. Parameters[" + parameters + "]. Returning null.");
        }
        return null;
    }

    public E findByNamedQuery(final String query, final Map<String, ?> parameters, final FlushModeType flushModeType,
            final LockModeType lockMode)
    {
        try
        {
            return getFormedNamedQuery(query, parameters, flushModeType, lockMode).getSingleResult();
        }
        catch (NoResultException nre)
        {
            logger.debug(
                    "No result returned for query '" + query + "'. Parameters[" + parameters + "]. Returning null.");
        }

        return null;
    }

    public void flush()
    {
        logger.debug("Flushing");
        getEntityManager().flush();
    }

    public E merge(final E entity)
    {
        logger.debug("Merging entity " + entity);
        return getEntityManager().merge(entity);
    }

    public void persist(final E entity)
    {
        logger.debug("Persisting entity " + entity);
        getEntityManager().persist(entity);
        flush();
    }

    public void persistAll(final Collection<E> entities)
    {
        Iterator<E> iterator = entities.iterator();
        int cursor = 0;

        while (iterator.hasNext())
        {
            E entity = iterator.next();

            if (getEntityManager().contains(entity))
            {
                getEntityManager().merge(entity);
            }
            else
            {
                getEntityManager().persist(entity);
            }

            if (cursor / BATCH_SIZE == 0 || !iterator.hasNext())
            {
                logger.debug("Flushing after " + cursor + ".");
                getEntityManager().flush();
            }
            cursor++;
        }
    }

    public void refresh(final E entity)
    {
        logger.debug("Refreshing entity " + entity);
        getEntityManager().refresh(entity);
    }

    public void remove(final E entity)
    {
        logger.debug("Deleting entity " + entity + ".");
        getEntityManager().remove(entity);

    }

    public void removeAll()
    {
        logger.debug("Deleting bulk entities.");
        for (E entity : findAll())
        {
            remove(entity);
        }
    }

    public void removeById(final K id)
    {
        logger.debug("Remove entity " + entityClass.getSimpleName() + " by id " + id);
        getEntityManager().remove(findById(id));
    }

    public E saveOrUpdate(E entity)
    {
        logger.debug("Saving or Updating entity " + entity + ".");
        entity = getEntityManager().merge(entity);
        flush();
        return entity;
    }

    public List<E> findAllByNamedQuery(String query, Map<String, ?> parameters, int offset, int limit)
    {
        try
        {
            return getFormedNamedQuery(query, parameters, offset, limit).getResultList();
        }
        catch (NoResultException nre)
        {
            logger.debug("No result returned for query '" + query + "'. Parameters[" + parameters + "]. Offset["
                    + offset + "] Limit[" + limit + "]  Returning null.");
        }
        return null;

    }

    public StoredProcedureQuery executeStoredProcedure(String query, Map<String, ?> parameters,
            final FlushModeType flushModeType, final LockModeType lockMode)
    {
        try
        {
            StoredProcedureQuery spQuery = getFormedStoredProcedureQuery(query, parameters, flushModeType, lockMode);
            spQuery.execute();

            return spQuery;
        }
        catch (NoResultException nre)
        {
            logger.debug("No result returned for query '" + query + "'. Parameters[" + parameters + "]");
        }
        return null;

    }

    private TypedQuery<E> getFormedNamedQuery(final String query, final Map<String, ?> parameters,
            final FlushModeType flushModeType, final LockModeType lockMode)
    {

        TypedQuery<E> namedQuery = entityManager.createNamedQuery(query, entityClass);
        if (flushModeType != null)
        {
            namedQuery.setFlushMode(flushModeType);
        }

        if (lockMode != null)
        {
            namedQuery.setLockMode(lockMode);
        }

        for (Map.Entry<String, ?> param : parameters.entrySet())
        {
            logger.debug("Setting parameter: [" + param.getKey() + "=" + param.getValue() + "]");
            namedQuery.setParameter(param.getKey(), param.getValue());
        }

        return namedQuery;
    }

    private StoredProcedureQuery getFormedStoredProcedureQuery(final String query, final Map<String, ?> parameters,
            final FlushModeType flushModeType, final LockModeType lockMode)
    {

        StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery(query);

        if (flushModeType != null)
        {
            storedProcedureQuery.setFlushMode(flushModeType);
        }

        if (lockMode != null)
        {
            storedProcedureQuery.setLockMode(lockMode);
        }

        for (Map.Entry<String, ?> param : parameters.entrySet())
        {
            logger.debug("Setting parameter: [" + param.getKey() + "=" + param.getValue() + "]");
            storedProcedureQuery.setParameter(param.getKey(), param.getValue());
        }

        return storedProcedureQuery;
    }

    private TypedQuery<E> getFormedNamedQuery(final String query, final Map<String, ?> parameters, final int offset,
            final int limit)
    {
        TypedQuery<E> namedQuery = entityManager.createNamedQuery(query, entityClass);

        for (Map.Entry<String, ?> param : parameters.entrySet())
        {
            logger.debug("Setting parameter: [" + param.getKey() + "=" + param.getValue() + "]");
            namedQuery.setParameter(param.getKey(), param.getValue());
        }
        namedQuery.setFirstResult(offset);
        namedQuery.setMaxResults(limit);
        return namedQuery;
    }

    public Long getCountByNamedQuery(final String query, final Map<String, ?> parameters)
    {
        try
        {
            TypedQuery<Long> namedQuery = entityManager.createNamedQuery(query, Long.class);
            if (parameters != null)
            {
                for (Map.Entry<String, ?> param : parameters.entrySet())
                {
                    logger.debug("Setting parameter: [" + param.getKey() + "=" + param.getValue() + "]");
                    namedQuery.setParameter(param.getKey(), param.getValue());
                }
            }
            return namedQuery.getSingleResult();
        }
        catch (NoResultException nre)
        {
            logger.debug(
                    "No result returned for query '" + query + "'. Parameters[" + parameters + "]. Returning null.");
        }
        return null;
    }

    public String getStringValueByNamedQuery(final String query, final Map<String, ?> parameters)
    {
        try
        {
            TypedQuery<String> namedQuery = entityManager.createNamedQuery(query, String.class);
            if (parameters != null)
            {
                for (Map.Entry<String, ?> param : parameters.entrySet())
                {
                    logger.debug("Setting parameter: [" + param.getKey() + "=" + param.getValue() + "]");
                    namedQuery.setParameter(param.getKey(), param.getValue());
                }
            }
            return namedQuery.getSingleResult();
        }
        catch (NoResultException nre)
        {
            logger.debug(
                    "No result returned for query '" + query + "'. Parameters[" + parameters + "]. Returning null.");
        }
        return null;
    }

    public void saveOrUpdateAll(final Collection<E> entities)
    {
        Iterator<E> iterator = entities.iterator();
        int cursor = 0;

        while (iterator.hasNext())
        {
            E entity = iterator.next();
            saveOrUpdate(entity);

            if (cursor / BATCH_SIZE == 0 || !iterator.hasNext())
            {
                logger.debug("Flushing after " + cursor + ".");
                getEntityManager().flush();
            }
            cursor++;
        }
    }

    public E saveOrUpdate(E entity, boolean flushFlag)
    {
        logger.debug("Saving or Updating entity " + entity + ".");
        entity = getEntityManager().merge(entity);
        if (flushFlag)
        {
            flush();
        }
        return entity;
    }

    public List<E> executeStoredProcedure(String procName, Map<String, Object> inTypes)
    {
        StoredProcedureQuery storedProcedure = getEntityManager().createNamedStoredProcedureQuery(procName);
        // set parameters

        if (inTypes != null)
        {
            for (Entry<String, Object> entry : inTypes.entrySet())
            {
                // storedProcedure.registerStoredProcedureParameter(entry.getKey(),
                // entry.getValue().getClass(), ParameterMode.IN);
                storedProcedure.setParameter(entry.getKey(), entry.getValue());
            }
        }
        //
        // if (outTypes != null)
        // {
        // for (Entry<String, Class<?>> entry : outTypes.entrySet())
        // {
        // storedProcedure.registerStoredProcedureParameter(entry.getKey(),
        // entry.getValue(), ParameterMode.OUT);
        // }
        // }

        // execute SP
        // storedProcedure.execute();
        // get result

        return (List<E>) storedProcedure.getResultList();
    }
    
}
