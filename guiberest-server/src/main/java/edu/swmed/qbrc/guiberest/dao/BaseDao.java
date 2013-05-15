package edu.swmed.qbrc.guiberest.dao;

import java.util.List;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.JDBCException;

import edu.swmed.qbrc.auth.cashmac.shared.exceptions.AclDeleteException;
import edu.swmed.qbrc.auth.cashmac.shared.exceptions.BadAclRoleException;
import edu.swmed.qbrc.auth.cashmac.shared.exceptions.NoAclException;
import edu.swmed.qbrc.guiberest.shared.domain.BaseEntity;
import edu.swmed.qbrc.guiberest.shared.domain.Constraint;

public class BaseDao<T extends BaseEntity> {    

	private final Class<T> clazz;
    private final Provider<EntityManager> entityManagerProvider;
    
    public BaseDao(final Class<T> clazz, final Provider<EntityManager> entityManagerProvider) {
        this.clazz = clazz;
        this.entityManagerProvider = entityManagerProvider;
    }
    
    @SuppressWarnings("rawtypes")
	public List<T> findAll(List<Constraint> constraints) {
    	List<T> toReturn = null;
    	
    	EntityManager entityManager = entityManager();
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();

    	try {
	    	CriteriaQuery<T> cq = cb.createQuery(clazz);
	    	Root<T> root = cq.from(clazz);

	    	Predicate pred = cb.conjunction();
	    	for (Constraint constraint : constraints) {
	    		if (! constraint.isNull()) {
	    			pred = constraint.getPredicate(cb, root, pred);
	    		}
	    	}
	    	cq.select(root);
	    	cq.where(pred);
	    	
	    	toReturn = entityManager.createQuery(cq).getResultList();
        } catch (PersistenceException e) {
            throw e;
        } catch (JDBCException e) {
            throw e;
        } catch(RuntimeException e){
            throw e;
        } finally{
        	entityManager.clear();
        }
    	
    	return toReturn;
    }

    public T find(Object id) {
        EntityManager entityManager = entityManager();
        
    	T toReturn = entityManager.find(clazz, id);
    	entityManager.clear();
    	return(toReturn);
    }

    public T put(T entity) {
    	EntityManager entityManager = entityManager();
    	EntityTransaction transaction = entityManager.getTransaction();        
        transaction.begin();
        try {        	
        	entity = entityManager.merge(entity);
            entityManager.persist(entity);
            entityManager.flush();
            transaction.commit();
        } catch (PersistenceException e) {
            handleException(transaction, e);
        } catch (JDBCException e) {
            handleException(transaction, e);
        } catch(RuntimeException e){
        	handleException(transaction, e);
        } finally{
        	entityManager.clear();
        }
               
        return entity;
    }
    
    public void delete(T entity) {
    	EntityManager entityManager = entityManager();
    	EntityTransaction transaction = entityManager.getTransaction();
        
        transaction.begin();
        try {
            entity = entityManager.merge(entity);
            entityManager.remove(entity);
            entityManager.flush();
            transaction.commit();
        } catch (PersistenceException e) {
            handleException(transaction, e);
        } catch (JDBCException e) {
            handleException(transaction, e);
        } finally{
        	entityManager.clear();
        }
    }
    
    /**
     * Delete all the rows in a table
     * @param entity the class of objects corresponding to the table to delete
     */
    public void deleteAll() {        
        EntityManager entityManager = entityManager();
    	EntityTransaction transaction = entityManager.getTransaction();
        
        transaction.begin();
        try {
        	Query query = entityManager.createQuery("delete from " + clazz.getSimpleName() );
        	query.executeUpdate();
        	entityManager.flush();
            transaction.commit();
        } catch (PersistenceException e) {
            handleException(transaction, e);
        } catch (JDBCException e) {
            handleException(transaction, e);
        } finally{
        	entityManager.clear();
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
    	EntityManager entityManager = entityManager();
    	Query query = entityManager.createQuery("select c from " + clazz.getSimpleName() + " c");
    	    	
    	List<T> toReturn = (List<T>) query.getResultList();

    	entityManager.clear();
        return toReturn; 
    }

    protected EntityManager entityManager() {
        return entityManagerProvider.get();
    }
    
    public Long countAll() {
        Query query = entityManager().createQuery("select count(*) from " + clazz.getSimpleName() + " c");
        return (Long) query.getSingleResult();
    }

    private void handleException(EntityTransaction transaction, RuntimeException exception) {        
    	if (transaction.isActive()) {
            transaction.rollback();
        }
    	
    	if (exception.getCause() instanceof NoAclException) {
    		throw (NoAclException) exception.getCause();
    	}
    	else if (exception.getCause() instanceof BadAclRoleException) {
    		throw (BadAclRoleException) exception.getCause();
    	}
    	else if (exception.getCause() instanceof AclDeleteException) {
    		throw (AclDeleteException) exception.getCause();
    	}
    	
    	else if (exception.getCause() instanceof PersistenceException) {
            throw (PersistenceException) exception.getCause();
        }

        throw exception;
    }
    
}
