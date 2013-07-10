package edu.swmed.qbrc.guiberest.console.app.guice;

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
import edu.swmed.qbrc.auth.cashmac.client.ClientAuthInterceptor;
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
		ClientAuthInterceptor interceptor = new ClientAuthInterceptor();
		interceptor.setHostName(hostName);
		interceptor.setProvider(this);

		/* Make service thread safe */
		ClientConnectionManager cm = new ThreadSafeClientConnManager();
		HttpClient httpClient = new DefaultHttpClient(cm);
		ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClient);
		
		/* Here, we register the interceptor and the Jackson (de)serialization provider */
		ResteasyClient client = new ResteasyClientBuilder().httpEngine(engine).register(interceptor).register(jacksonConfigProvider).build();
		ResteasyWebTarget target = (ResteasyWebTarget)client.target(restURL);
		return target.proxy(GuiberestRestService.class);
	}
	
}