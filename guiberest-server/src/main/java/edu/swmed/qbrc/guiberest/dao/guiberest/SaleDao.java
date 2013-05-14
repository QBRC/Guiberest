package edu.swmed.qbrc.guiberest.dao.guiberest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import edu.swmed.qbrc.guiberest.dao.BaseDao;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale;
import edu.swmed.qbrc.guiberest.shared.guice.datasources.GuiberestDataSource;

public class SaleDao extends BaseDao<Sale> {
    @Inject
    public SaleDao(@GuiberestDataSource Provider<EntityManager> entityManagerProvider) {
        super(Sale.class, entityManagerProvider);
    }
    
    public List<Sale> findByCustomer(Integer customerId) {
    	EntityManager entityManager = entityManager();
	    
    	//get all relevant patients
    	Query query = entityManager.createQuery("SELECT s FROM Sale s " +
    			"WHERE s.customerId = :customerId )");
        query.setParameter("customerId", customerId);
		                
        @SuppressWarnings("unchecked")
		List<Sale> toReturn = (List<Sale>)query.getResultList();
        entityManager.clear();
        
        return toReturn;
    	
    }
    
}
