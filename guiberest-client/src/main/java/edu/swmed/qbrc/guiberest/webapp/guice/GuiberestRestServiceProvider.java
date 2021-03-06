package edu.swmed.qbrc.guiberest.webapp.guice;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import edu.swmed.qbrc.auth.cashmac.client.CasHmacRestProvider;
import edu.swmed.qbrc.auth.cashmac.client.ClientAuthInterceptorCas;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.jacksonate.rest.jackson.JacksonConfigProvider;

public class GuiberestRestServiceProvider implements CasHmacRestProvider<GuiberestRestService> {

	private final String clientId;
	private final String secret;
	private final String hostName;
	private final String restURL;
	private final JacksonConfigProvider jacksonConfigProvider; 
	
	@Inject
	public GuiberestRestServiceProvider(
							@Named("HostName") final String hostName,
							@Named("RestURL") final String restURL,
							final JacksonConfigProvider jacksonConfigProvider) {
		this.clientId = "cas";
		this.secret = "secret";
		this.hostName = hostName;
		this.restURL = restURL;
		this.jacksonConfigProvider = jacksonConfigProvider;
	}
	
	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public String getSecret() {
		return secret;
	}

	public GuiberestRestService get() {
		
		/* Here, we create and configure the Client Interceptor, which will intercept REST requests
		 * to our RESTEasy service and add the headers for HMAC authentication.
		 */
		ClientAuthInterceptorCas interceptor = new ClientAuthInterceptorCas();
		interceptor.setProvider(this);
		interceptor.setHostName(hostName);

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
		 *   ClientExecutor to the CasHmac client library. It's up to the developer using the library
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
		//ClientExecutor executor = new GWTClientExecutor(httpClient);
		ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClient);
		
		/* Here, we register the interceptor and the Jackson (de)serialization provider */
		ResteasyClient client = new ResteasyClientBuilder().httpEngine(engine).register(interceptor).register(jacksonConfigProvider).build();
		ResteasyWebTarget target = (ResteasyWebTarget)client.target(restURL);
		return target.proxy(GuiberestRestService.class);
	}
	
}