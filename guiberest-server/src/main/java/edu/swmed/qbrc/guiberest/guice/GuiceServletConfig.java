package edu.swmed.qbrc.guiberest.guice;

import java.lang.reflect.Type;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.plugins.guice.GuiceResourceFactory;
import org.jboss.resteasy.plugins.server.servlet.ListenerBootstrap;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.util.GetRestful;
import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.servlet.GuiceServletContextListener;
import edu.swmed.qbrc.auth.cashmac.server.ValidationInterceptorCasHmac;
import edu.swmed.qbrc.auth.cashmac.server.guice.MainGuiceModule;
import edu.swmed.qbrc.guiberest.guice.datasources.GuiberestDataSourcePersistModule;
import edu.swmed.qbrc.guiberest.shared.guice.datasources.GuiberestDataSource;

public class GuiceServletConfig extends GuiceServletContextListener {

	private static final Logger log = Logger.getLogger(GuiceServletConfig.class);

	private ResteasyDeployment deployment;
	private Injector injector = null;
	
	/**
	 * Sets up the injector for our REST service.  Only creates the injector once, 
	 * since the "contextInitialized" method also needs access to the injector.
	 */
	@Override
    protected Injector getInjector() {

		if (injector == null) {
	        this.injector = Guice.createInjector(
	        		new DispatchServletModule(),
	        		new RestModule(),
	        		new GuiberestDataSourcePersistModule()
       		);
		}

		// Set up injection for CasHmac Library
		com.google.inject.Provider<EntityManager> p = injector.getInstance(Key.get(new TypeLiteral<com.google.inject.Provider<EntityManager>>(){}, GuiberestDataSource.class));
		MainGuiceModule.addEntityManagerProvider(p, GuiberestDataSource.class);
		
		return injector;
    }
	
	/**
	 * Some of this code was taken from the RESTEasy source.  We're trying to use Guice
	 * as best we can for DI, and it was difficult, if not impossible, to get our own
	 * "Guicified" web application to play nicely with the Guice injector used by
	 * RESTEasy's Guice module.  So we pulled out the pieces necessary and handle setting
	 * up RESTEasy ourselves. 
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		final ServletContext context = event.getServletContext();
	    final ListenerBootstrap config = new ListenerBootstrap(event.getServletContext());
	    deployment = config.createDeployment();
	    deployment.start();

		final Registry registry = deployment.getRegistry();
		final ResteasyProviderFactory providerFactory = deployment.getProviderFactory();

	    context.setAttribute(ResteasyProviderFactory.class.getName(), providerFactory);
	    context.setAttribute(Dispatcher.class.getName(), deployment.getDispatcher());
		context.setAttribute(Registry.class.getName(), registry);
	    
	    // Process any REST service @Provider annotated classes (set up services)
		processInjector(getInjector(), registry, providerFactory);

		// Add server interceptor for authentication (CasHmac)
		ValidationInterceptorCasHmac interceptor = new ValidationInterceptorCasHmac();
		providerFactory.getServerPreProcessInterceptorRegistry().register(interceptor);
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		super.contextDestroyed(servletContextEvent);
		deployment.stop();
	}
	
	/**
	 * Unfortunately, this was a private method in org.jboss.resteasy.plugins.guice.ModuleProcessor.
	 * Had it been public, we could have simply called "processInjector" with our own injector. Since
	 * it was private, we copy it here so we can pass our own injector to it.
	 * 
	 * @param injector
	 * @param registry
	 * @param providerFactory
	 */
	private void processInjector(final Injector injector, final Registry registry, final ResteasyProviderFactory providerFactory) {
		for (final Binding<?> binding : injector.getBindings().values()) {
			final Type type = binding.getKey().getTypeLiteral().getType();
			if (type instanceof Class) {
				final Class<?> beanClass = (Class<?>) type;
				if (GetRestful.isRootResource(beanClass)) {
					final ResourceFactory resourceFactory = new GuiceResourceFactory(binding.getProvider(), beanClass);
					log.info("registering factory for " + beanClass.getName());
					registry.addResourceFactory(resourceFactory);
				}
				if (beanClass.isAnnotationPresent(Provider.class)) {
					log.info("registering provider instance for " + beanClass.getName());
					providerFactory.registerProviderInstance(binding.getProvider().get());
				}
			}
		}
	}	

}
