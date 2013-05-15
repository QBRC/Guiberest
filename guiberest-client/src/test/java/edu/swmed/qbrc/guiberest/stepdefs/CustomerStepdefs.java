package edu.swmed.qbrc.guiberest.stepdefs;

import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Assert;
import com.google.inject.Inject;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import cucumber.table.DataTable;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Customer;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.jacksonate.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.jacksonate.rest.util.IntegerArray;
import gherkin.formatter.model.DataTableRow;

public class CustomerStepdefs {
    
	final GuiberestRestService guiberestRestService;
	
	final IntegerArray insertIdCache = new IntegerArray();
	TableJSONContainer<Customer> resultsCache;
	
    @Inject
    public CustomerStepdefs(final GuiberestRestService guiberestRestService) {
    	this.guiberestRestService = guiberestRestService;
    }
	
    @When("^I request customers with the following customer ids:$")
    public void I_request_customers_with_the_following_customer_ids(DataTable ids) throws Throwable {
    	final IntegerArray idArray = new IntegerArray();
    	for (DataTableRow row : ids.getGherkinRows()) {
    		idArray.getList().add(Integer.parseInt(row.getCells().get(0)));
    	}
    	resultsCache = guiberestRestService.getCustomers(idArray);
    }

    @Then("^I see the following full customer results:$")
    public void I_see_the_following_full_customer_results(DataTable results) throws Throwable {
    	Integer index = 0;
    	for (DataTableRow row : results.getGherkinRows()) {
    		Customer customer = resultsCache.getData().get(index++);
    		Assert.assertTrue(
    				customer.getId().toString().trim().equals(row.getCells().get(0)) &&
    				customer.getPreferredStoreId().toString().trim().equals(row.getCells().get(1)) &&
    				customer.getName().trim().equals(row.getCells().get(2))
    		);
    	}
    }

    @When("^I request all customers$")
    public void I_request_all_customers() throws Throwable {
    	resultsCache = guiberestRestService.getCustomers();
    }

    @Then("^I see at least (\\d+) customer results$")
    public void I_see_at_least_x_customer_results(int results) throws Throwable {
    	System.out.println("Row Count: " + resultsCache.getData().size());
        Assert.assertTrue(resultsCache.getData().size() >= results);
    }
    
    @SuppressWarnings("unchecked")
	@When("^I insert the following customers:$")
    public void I_insert_the_following_customers(DataTable inserts) throws Throwable {
    	insertIdCache.getList().clear();
    	for (DataTableRow row : inserts.getGherkinRows()) {
    		ClientResponse<String> response = (ClientResponse<String>)guiberestRestService.putCustomer(
    				Integer.parseInt(row.getCells().get(0).trim()),
    				Integer.parseInt(row.getCells().get(1).trim()),
    				row.getCells().get(2).trim()
    		);
    		Integer id = response.getEntity(Integer.class);
    		System.out.println("Inserted Customer with ID: " + id);
    		insertIdCache.getList().add(id);
    	}
    }

    @SuppressWarnings("unchecked")
    @When("^I update the following customers:$")
    public void I_update_the_following_customers(DataTable inserts) throws Throwable {
    	insertIdCache.getList().clear();
    	for (DataTableRow row : inserts.getGherkinRows()) {
    		ClientResponse<String> response = (ClientResponse<String>)guiberestRestService.putCustomer(
    				Integer.parseInt(row.getCells().get(0).trim()),
    				Integer.parseInt(row.getCells().get(1).trim()),
    				row.getCells().get(2).trim()
    		);
    		Integer id = response.getEntity(Integer.class);
    		System.out.println("Updated Customer with ID: " + id);
    		insertIdCache.getList().add(id);
    	}
    }

    @SuppressWarnings("unused")
	@When("^I delete the following customers:$")
    public void I_delete_the_following_customers(DataTable deletes) throws Throwable {
    	insertIdCache.getList().clear();
    	for (DataTableRow row : deletes.getGherkinRows()) {
    		Response response = guiberestRestService.deleteCustomer(
    				Integer.parseInt(row.getCells().get(0).trim())
    		);
    	}
    }

    @Then("^I see no more than (\\d+) customer results$")
    public void I_see_no_more_than_customer_results(int results) throws Throwable {
    	System.out.println("Row Count: " + resultsCache.getData().size());
        Assert.assertTrue(resultsCache.getData().size() <= results);
    }

    

}
