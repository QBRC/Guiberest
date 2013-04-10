package edu.swmed.qbrc.guiberest.dao.guiberest;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import edu.swmed.qbrc.guiberest.dao.BaseDao;
import edu.swmed.qbrc.guiberest.guice.datasources.GuiberestDataSource;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;

public class UserDao extends BaseDao<User> {
    @Inject
    public UserDao(@GuiberestDataSource Provider<EntityManager> entityManagerProvider) {
        super(User.class, entityManagerProvider);
    }
}
