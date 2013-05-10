package edu.swmed.qbrc.guiberest.app;

import java.util.List;
import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.reflections.Reflections;
import edu.swmed.qbrc.auth.cashmac.client.ClientAuthInterceptor;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Customer;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.guiberest.shared.rest.jackson.GuiberestSerializationModule;
import edu.swmed.qbrc.jacksonate.rest.jackson.JacksonConfigProvider;
import edu.swmed.qbrc.jacksonate.rest.jackson.ReflectionFactory;
import edu.swmed.qbrc.jacksonate.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.jacksonate.rest.util.IntegerArray;

public class App {
	
	private static GuiberestRestService guibRestService;

	private static ClientRequestFactory initializeRequests() {
		ResteasyProviderFactory instance = ResteasyProviderFactory.getInstance();
		RegisterBuiltin.register(instance);
		instance.registerProvider(GuiberestRestService.class);
		ReflectionFactory reflectionFactory = new ReflectionFactory();
		Reflections reflections = new Reflections("edu.swmed.qbrc.guiberest.shared.domain.guiberest");
		instance.registerProviderInstance(new JacksonConfigProvider(new GuiberestSerializationModule(reflectionFactory, reflections, null))); // For custom serialization/deserialization
	 
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
		
		IntegerArray customerids = new IntegerArray();
		customerids.getList().add(1);
		customerids.getList().add(25);
		TableJSONContainer<Customer> tblexp = guibRestService.getCustomers(customerids);
		List<Customer> customers = tblexp.getData();
		if (customers != null) {
			for (Customer customer : customers) {
				System.out.println("Customer: " + customer.getId() + " - " + customer.getName() + "<br/>");
			}
		}

		TableJSONContainer<Sale> tbl = guibRestService.getSalesByCustomer(6);
		if (tbl.getData() != null) {
			for (Sale sale : tbl.getData()) {
				System.out.println("Sale: " + sale.getId() + " - " + sale.getCustomerId() + "/" + sale.getStoreId() + " - " + sale.getTotal() + "<br/>");
			}
		}		
		
	}

}
