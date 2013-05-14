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
import com.google.inject.Provider;
import com.google.inject.name.Named;
import edu.swmed.qbrc.auth.cashmac.client.ClientAuthInterceptor;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.jacksonate.rest.jackson.JacksonConfigProvider;

public class GuiberestRestServiceProviderThomas implements Provider<GuiberestRestService> {

	private final String clientId;
	private final String secret;
	private final String hostName;
	private final String restURL;
	private final JacksonConfigProvider jacksonConfigProvider;
	
	@Inject
	public GuiberestRestServiceProviderThomas(
							@Named("ClientId-thomas") final String clientId,
							@Named("Secret-thomas") final String secret,
							@Named("HostName") final String hostName,
							@Named("RestURL") final String restURL,
							final JacksonConfigProvider jacksonConfigProvider) {
		this.clientId = clientId;
		this.secret = secret;
		this.hostName = hostName;
		this.restURL = restURL;
		this.jacksonConfigProvider = jacksonConfigProvider;
	}
	
	public GuiberestRestService get() {
		
		/* Here, we create and configure the Client Interceptor, which will intercept REST requests
		 * to our RESTEasy service and add the headers for HMAC authentication.
		 */
		ClientAuthInterceptor interceptor = new ClientAuthInterceptor();
		interceptor.setClientId(clientId);
		interceptor.setSecret(secret);
		interceptor.setHostName(hostName);			

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