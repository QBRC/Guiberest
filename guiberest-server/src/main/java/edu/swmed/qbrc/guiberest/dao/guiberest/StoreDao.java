package edu.swmed.qbrc.guiberest.dao.guiberest;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import edu.swmed.qbrc.guiberest.dao.BaseDao;
import edu.swmed.qbrc.guiberest.guice.datasources.GuiberestDataSource;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store;

public class StoreDao extends BaseDao<Store> {
    @Inject
    public StoreDao(@GuiberestDataSource Provider<EntityManager> entityManagerProvider) {
        super(Store.class, entityManagerProvider);
    }
}
