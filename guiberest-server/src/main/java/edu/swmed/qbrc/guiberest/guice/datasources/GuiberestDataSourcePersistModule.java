package edu.swmed.qbrc.guiberest.guice.datasources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.persistence.EntityManager;
import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.Provider;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;

import edu.swmed.qbrc.guiberest.shared.guice.datasources.GuiberestDataSource;

public class GuiberestDataSourcePersistModule extends PrivateModule {

	public static final Key<PersistFilter> GUIBEREST_DATA_SOURCE_FILTER_KEY = Key.get(PersistFilter.class, GuiberestDataSource.class);
	
	@Override
	protected void configure() {

	    install(new JpaPersistModule(getGuiberestPersistenceUnit()));
	    
	    final Provider<EntityManager> entityManagerProvider = binder().getProvider(EntityManager.class);
	    bind(EntityManager.class).annotatedWith(GuiberestDataSource.class).toProvider(entityManagerProvider);
	    expose(EntityManager.class).annotatedWith(GuiberestDataSource.class);

	    bind(GUIBEREST_DATA_SOURCE_FILTER_KEY).to(PersistFilter.class);
	    expose(GUIBEREST_DATA_SOURCE_FILTER_KEY);	    	    
	}
    
    private String getGuiberestPersistenceUnit() {
        InputStream inputStream = getClass().getResourceAsStream("/database.properties");

        if (inputStream == null) {
            throw new RuntimeException();
        }

        try {
            Properties properties = new Properties();
            properties.load(inputStream);

            return properties.getProperty("guiberest.persistenceUnit");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
}
