package edu.swmed.qbrc.guiberest.guice;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAllowedException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Customer;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.jacksonate.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.jacksonate.rest.util.IntegerArray;

@Singleton
public class MyServlet extends HttpServlet {
	
	@Inject
	GuiberestRestService guibRestService;

	private static final long serialVersionUID = -5697849610754976017L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		
		StringBuilder out = new StringBuilder();
		out.append("<p>");
		
		IntegerArray customerids = new IntegerArray();
		customerids.getList().add(21);
		customerids.getList().add(23);
		try {
			TableJSONContainer<Customer> tblexp = guibRestService.getCustomers(customerids);
			List<Customer> customers = tblexp.getData();
			if (customers != null) {
				for (Customer customer : customers) {
					out.append("Customer: " + customer.getId() + " - " + customer.getName() + "<br/>");
				}
			}
		} catch (NotAllowedException e) {
    		e.getResponse().close();
		} catch (BadRequestException e) {
			e.getResponse().close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
		
		IntegerArray saleids = new IntegerArray();
		saleids.getList().add(32);
		saleids.getList().add(33);
		try {
			TableJSONContainer<Sale> sltbl = guibRestService.getSales(saleids);
			if (sltbl.getData() != null) {
				for (Sale sale : sltbl.getData()) {
					out.append("Sale: " + sale.getId() + " - " + sale.getCustomerId() + "/" + sale.getStoreId() + " - " + sale.getTotal() + "<br/>");
				}
			}				
		} catch (NotAllowedException e) {
    		e.getResponse().close();
		} catch (BadRequestException e) {
			e.getResponse().close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

		try {
			TableJSONContainer<Sale> tbl = guibRestService.getSalesByCustomer(25);
			if (tbl.getData() != null) {
				for (Sale sale : tbl.getData()) {
					out.append("Sale: " + sale.getId() + " - " + sale.getCustomerId() + "/" + sale.getStoreId() + " - " + sale.getTotal() + "<br/>");
				}
			}
		} catch (NotAllowedException e) {
    		e.getResponse().close();
		} catch (BadRequestException e) {
			e.getResponse().close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
		
		// Send to browser
		out.append("</p>");
		resp.getOutputStream().print(out.toString());
	}
	
}