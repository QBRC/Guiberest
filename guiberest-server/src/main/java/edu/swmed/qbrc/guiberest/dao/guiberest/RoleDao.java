package edu.swmed.qbrc.guiberest.dao.guiberest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import edu.swmed.qbrc.guiberest.dao.BaseDao;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Role;
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
public class RoleDao extends BaseDao<Role> {
    @Inject
    public RoleDao(@GuiberestDataSource Provider<EntityManager> entityManagerProvider) {
        super(Role.class, entityManagerProvider);
    }
    
    public List<Role> getRolesForUser(List<String> userids) {
    	EntityManager entityManager = entityManager();
	    
    	//get all relevant patients
    	Query query = entityManager.createQuery(
    			"SELECT r FROM Role r, RoleUser ru " +
    			"WHERE r.id = ru.roleId " +
    			"AND ru.username in ( :userids )"
    	);
        query.setParameter("userids", userids);
		                
        @SuppressWarnings("unchecked")
		List<Role> toReturn = (List<Role>)query.getResultList();
        entityManager.clear();
        
        return toReturn;
    	
    }
}
