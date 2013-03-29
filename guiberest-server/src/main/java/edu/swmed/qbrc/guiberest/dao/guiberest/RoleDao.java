package edu.swmed.qbrc.guiberest.dao.guiberest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import edu.swmed.qbrc.guiberest.dao.BaseDao;
import edu.swmed.qbrc.guiberest.guice.GuiberestDataSource;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Role;

public class RoleDao extends BaseDao<Role> {
    @Inject
    public RoleDao(@GuiberestDataSource Provider<EntityManager> entityManagerProvider) {
        super(Role.class, entityManagerProvider);
    }
    
    public List<Role> getRolesForUser(List<String> userids) {
    	EntityManager entityManager = entityManager();
	    
    	//get all relevant patients
    	Query query = entityManager.createQuery("SELECT r FROM Role r " +
    			"WHERE r.username in ( :userids )");
        query.setParameter("userids", userids);
		                
        @SuppressWarnings("unchecked")
		List<Role> toReturn = (List<Role>)query.getResultList();
        entityManager.clear();
        
        return toReturn;
    	
    }
}
