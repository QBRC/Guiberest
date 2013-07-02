package edu.swmed.qbrc.guiberest.app;

import java.util.List;
import org.reflections.Reflections;

import edu.swmed.qbrc.guiberest.app.guice.GuiberestRestServiceProvider;
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

	private static GuiberestRestService getWebService() {
		
		// Get a Jackson provider
		ReflectionFactory reflectionFactory = new ReflectionFactory();
		Reflections reflections = new Reflections("edu.swmed.qbrc.guiberest.shared.domain.guiberest");
		JacksonConfigProvider jackson = new JacksonConfigProvider(new GuiberestSerializationModule(reflectionFactory, reflections, null));
		
		String hostname = "localhost";
		try {
			hostname = java.net.InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {}
		
		// Manually instantiate our GuiberestRestServiceProvider (since we're not really using Guice in this test app).
		GuiberestRestServiceProvider provider = new GuiberestRestServiceProvider(
				"thomas",
				"123456789",
				hostname + ":9090",
				"https://" + hostname + ":9090",
				jackson);
		
		// Get an instance of the web service from the provider.
		return provider.get();
	}	

	public static void main(String[] args) {

		guibRestService = getWebService();
		
		IntegerArray customerids = new IntegerArray();
		customerids.getList().add(21);
		customerids.getList().add(23);
		TableJSONContainer<Customer> tblexp = guibRestService.getCustomers(customerids);
		List<Customer> customers = tblexp.getData();
		if (customers != null) {
			for (Customer customer : customers) {
				System.out.println("Customer: " + customer.getId() + " - " + customer.getName() + "<br/>");
			}
		}

		IntegerArray saleids = new IntegerArray();
		saleids.getList().add(32);
		saleids.getList().add(33);
		TableJSONContainer<Sale> sltbl = guibRestService.getSales(saleids);
		if (sltbl.getData() != null) {
			for (Sale sale : sltbl.getData()) {
				System.out.println("Sale: " + sale.getId() + " - " + sale.getCustomerId() + "/" + sale.getStoreId() + " - " + sale.getTotal() + "<br/>");
			}
		}		
		
		TableJSONContainer<Sale> tbl = guibRestService.getSalesByCustomer(25);
		if (tbl.getData() != null) {
			for (Sale sale : tbl.getData()) {
				System.out.println("Sale: " + sale.getId() + " - " + sale.getCustomerId() + "/" + sale.getStoreId() + " - " + sale.getTotal() + "<br/>");
			}
		}		
		
	}
}
