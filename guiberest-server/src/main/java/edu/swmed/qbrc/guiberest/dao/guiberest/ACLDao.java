package edu.swmed.qbrc.guiberest.dao.guiberest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import edu.swmed.qbrc.guiberest.dao.BaseDao;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.ACL;
import edu.swmed.qbrc.guiberest.shared.guice.datasources.GuiberestDataSource;

/**
 * This DAO class was created, not to provide a sample DAO object for the
 * Guiberest application, but to enable more comprehensive testing of the
 * ACL-based permissions in this application.  In short, the Customer, 
 * Store, and Sale entities exist to for use in the sample RESTful service.
 * The User, Role, and ACL entities exist (but would probably be deleted
 * in a production application) simply for use by some of our unit tests.
 * 
 * @author JYODE1
 *
 */
public class ACLDao extends BaseDao<ACL> {
    @Inject
    public ACLDao(@GuiberestDataSource Provider<EntityManager> entityManagerProvider) {
        super(ACL.class, entityManagerProvider);
    }
    
    public List<ACL> getAclsForObject(String className, String pkValue) {
    	EntityManager entityManager = entityManager();
	    
    	//get all relevant patients
    	Query query = entityManager.createQuery("SELECT a FROM ACL a " +
    			"WHERE a.objectClass = :class AND a.objectPK = :pk");
        query.setParameter("class", className);
        query.setParameter("pk", pkValue);
		                
        @SuppressWarnings("unchecked")
		List<ACL> toReturn = (List<ACL>)query.getResultList();
        entityManager.clear();
        
        return toReturn;
    	
    }    
}
