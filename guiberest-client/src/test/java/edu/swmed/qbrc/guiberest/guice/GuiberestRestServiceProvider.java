package edu.swmed.qbrc.guiberest.guice;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import edu.swmed.qbrc.auth.cashmac.client.CasHmacRestProvider;
import edu.swmed.qbrc.auth.cashmac.client.ClientAuthInterceptor;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.jacksonate.rest.jackson.JacksonConfigProvider;

public class GuiberestRestServiceProvider implements CasHmacRestProvider<GuiberestRestService> {

	private final String hostName;
	private final String restURL;
	private final JacksonConfigProvider jacksonConfigProvider;
	final ClientIdentification clientIdentification;
	
	@Inject
	public GuiberestRestServiceProvider(
							@Named("HostName") final String hostName,
							@Named("RestURL") final String restURL,
							final JacksonConfigProvider jacksonConfigProvider,
							final ClientIdentification clientIdentification) {
		this.hostName = hostName;
		this.restURL = restURL;
		this.jacksonConfigProvider = jacksonConfigProvider;
		this.clientIdentification = clientIdentification;
	}
	
	@Override
	public String getClientId() {
		return clientIdentification.getClientId();
	}
	
	@Override
	public String getSecret() {
		return clientIdentification.getSecret();
	}

	public GuiberestRestService get() {
		
		/* Here, we create and configure the Client Interceptor, which will intercept REST requests
		 * to our RESTEasy service and add the headers for HMAC authentication.
		 */
		ClientAuthInterceptor interceptor = new ClientAuthInterceptor();
		interceptor.setHostName(hostName);
		interceptor.setProvider(this);

		/* Here, we register the interceptor */
		ResteasyProviderFactory.getInstance().getClientExecutionInterceptorRegistry().register(interceptor);
		
		// And the Jackson (de)serialization provider
		ResteasyProviderFactory.getInstance().registerProviderInstance(jacksonConfigProvider);
		
		/* Here, we return the MessageRestService.  If we didn't need the custom executor, we could
		 * simply skip the "executor" parameter. 
		 */
		ClientConnectionManager cm = new ThreadSafeClientConnManager();
		HttpClient httpClient = new DefaultHttpClient(cm);
		ClientExecutor executor = new ApacheHttpClient4Executor(httpClient);
		return ProxyFactory.create(GuiberestRestService.class, restURL, executor);
	}
	
}