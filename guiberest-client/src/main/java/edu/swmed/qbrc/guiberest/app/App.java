package edu.swmed.qbrc.guiberest.app;

import java.util.List;
import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.reflections.Reflections;
import edu.swmed.qbrc.auth.cashmac.client.ClientAuthInterceptor;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Role;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.JacksonConfigProvider;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.ReflectionFactory;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.guiberest.shared.rest.util.StringArray;

public class App {
	
	private static GuiberestRestService guibRestService;

	private static ClientRequestFactory initializeRequests() {
		ResteasyProviderFactory instance = ResteasyProviderFactory.getInstance();
		RegisterBuiltin.register(instance);
		instance.registerProvider(GuiberestRestService.class);
		ReflectionFactory reflectionFactory = new ReflectionFactory();
		Reflections reflections = new Reflections("edu.swmed.qbrc.guiberest.shared.domain.guiberest");
		instance.registerProviderInstance(new JacksonConfigProvider(reflectionFactory, reflections)); // For custom serialization/deserialization
	 
		ClientRequestFactory clientRequestFactory = new ClientRequestFactory();
		ClientAuthInterceptor interceptor = new ClientAuthInterceptor();
		interceptor.setClientId("thomas");
		interceptor.setSecret("123456789");
		interceptor.setHostName("127.0.0.1:9090");
		clientRequestFactory.getPrefixInterceptors().registerInterceptor(interceptor);
		return clientRequestFactory;
	}	

	public static void main(String[] args) {
		
		ClientRequestFactory client = initializeRequests();
		guibRestService = client.createProxy(GuiberestRestService.class, "http://127.0.0.1:9090/");
		
		StringArray userids = new StringArray();
		userids.getList().add("thomas");
		userids.getList().add("roger");
		TableJSONContainer<User> tblexp = guibRestService.getUsers(userids);
		List<User> users = tblexp.getData();
		if (users != null) {
			for (User user : users) {
				System.out.println("User Secret: " + user.getSecret());
			}
		}

		StringArray useridsforrole = new StringArray();
		useridsforrole.getList().add("thomas");
		TableJSONContainer<Role> roles = guibRestService.getRoles(useridsforrole);
		for (Role role : roles.getData()) {
			System.out.println("Role: " + role.getRole());
		}
		
	}

}
