package edu.swmed.qbrc.guiberest.dao.guiberest;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import edu.swmed.qbrc.guiberest.dao.BaseDao;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Customer;
import edu.swmed.qbrc.guiberest.shared.guice.datasources.GuiberestDataSource;

public class CustomerDao extends BaseDao<Customer> {
    @Inject
    public CustomerDao(@GuiberestDataSource Provider<EntityManager> entityManagerProvider) {
        super(Customer.class, entityManagerProvider);
    }
}
