package edu.swmed.qbrc.guiberest.guice;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import edu.swmed.qbrc.auth.cashmac.client.ClientAuthInterceptor;
import edu.swmed.qbrc.auth.cashmac.client.GWTClientExecutor;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.JacksonConfigProvider;

public class GuiberestRestServiceProvider implements Provider<GuiberestRestService> {

	private final String clientId;
	private final String secret;
	private final String hostName;
	private final String restURL;
	
	@Inject
	public GuiberestRestServiceProvider(
							@Named("ClientId") final String clientId,
							@Named("Secret") final String secret,
							@Named("HostName") final String hostName,
							@Named("RestURL") final String restURL) {
		this.clientId = clientId;
		this.secret = secret;
		this.hostName = hostName;
		this.restURL = restURL;
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

		/* For custom serialization/deserialization */
		ResteasyProviderFactory.getInstance().registerProvider(JacksonConfigProvider.class);
		
		/* Optional:
		 * 
		 *   Here, we create a thread-safe ClientConnectionManager, provide it to a default
		 *   HttpClient, and pass the HttpClient to a custom GWTClientExecutor.
		 *   
		 *   If you need the thread-safe ClientConnectionManager, but not the custom
		 *   GWTClientExecutor, then replace the ClientExecutor line with the following:
		 *   
		 *      ClientExecutor executor = new ApacheHttpClient4Executor(httpClient);
		 *      
		 * NOTES:
		 *   
		 *   The RESTEasy library's default ClientExecutor, which is used by the Client Interceptor,
		 *   was not working with GWT apps.  I think that GWT must append form parameters to all
		 *   requests automatically.  When the ClientExecutor code sees the form parameters, it
		 *   assumes that it's dealing with a POST request, and attempts to cast the request to
		 *   an HttpPost request, which fails since it's actually a GET request.  I created a
		 *   custom ClientExecutor that extends the implementation used by RESTEasy, and configured
		 *   it to simply assume that we're dealing with a GET request.  I added the extended
		 *   ClientExecutor to the CasHmac client library—it's up to the developer using the library
		 *   whether to go with the custom ClientExecutor or the default one provided by RESTEasy.
		 *   
		 *   Because the default client connection manager used by RESTEasy is not thread safe, and
		 *   since some applications may fire off several REST requests simultaneously, I started
		 *   having issues with secondary REST requests failing due to a previous connection that
		 *   was still open. After reading some of the JBoss documentation, I figured out how to set
		 *   up a thread-safe client connection manager, which takes care of the problem.
		 */
		ClientConnectionManager cm = new ThreadSafeClientConnManager();
		HttpClient httpClient = new DefaultHttpClient(cm);
		ClientExecutor executor = new GWTClientExecutor(httpClient);
		
		/* Here, we return the MessageRestService.  If we didn't need the custom executor, we could
		 * simply skip the "executor" parameter. 
		 */
		return ProxyFactory.create(GuiberestRestService.class, restURL, executor);
	}
	
}