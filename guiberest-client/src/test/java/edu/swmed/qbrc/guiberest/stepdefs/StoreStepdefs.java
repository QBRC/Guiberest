package edu.swmed.qbrc.guiberest.stepdefs;

import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Assert;
import com.google.inject.Inject;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import cucumber.table.DataTable;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.jacksonate.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.jacksonate.rest.util.IntegerArray;
import gherkin.formatter.model.DataTableRow;

public class StoreStepdefs {
    
	final GuiberestRestService guiberestRestService;
	
	final IntegerArray insertIdCache = new IntegerArray();
	TableJSONContainer<Store> resultsCache;
	
    @Inject
    public StoreStepdefs(final GuiberestRestService guiberestRestService) {
    	this.guiberestRestService = guiberestRestService;
    }
	
    @Given("^I'm ready to go$")
    public void I_m_ready_to_go() throws Throwable {
        Assert.assertTrue(true);
    }

    @When("^I request stores with the following store ids:$")
    public void I_request_stores_with_the_following_store_ids(DataTable ids) throws Throwable {
    	final IntegerArray idArray = new IntegerArray();
    	for (DataTableRow row : ids.getGherkinRows()) {
    		idArray.getList().add(Integer.parseInt(row.getCells().get(0)));
    	}
    	resultsCache = guiberestRestService.getStores(idArray);
    }

    @Then("^I see the following full store results:$")
    public void I_see_the_following_full_store_results(DataTable results) throws Throwable {
    	Integer index = 0;
    	for (DataTableRow row : results.getGherkinRows()) {
    		Store store = resultsCache.getData().get(index++);
    		Assert.assertTrue(
    				store.getId().toString().trim().equals(row.getCells().get(0)) &&
    				store.getName().trim().equals(row.getCells().get(1))
    		);
    	}
    }

    @When("^I request all stores$")
    public void I_request_all_stores() throws Throwable {
    	resultsCache = guiberestRestService.getStores();
    }

    @Then("^I see at least (\\d+) store results$")
    public void I_see_at_least_x_store_results(int results) throws Throwable {
    	System.out.println("Row Count: " + resultsCache.getData().size());
        Assert.assertTrue(resultsCache.getData().size() >= results);
    }
    
    @SuppressWarnings("unchecked")
	@When("^I insert the following stores:$")
    public void I_insert_the_following_stores(DataTable inserts) throws Throwable {
    	insertIdCache.getList().clear();
    	for (DataTableRow row : inserts.getGherkinRows()) {
    		ClientResponse<Integer> response = (ClientResponse<Integer>)guiberestRestService.putStore(
    				Integer.parseInt(row.getCells().get(0).trim()),
    				row.getCells().get(1).trim()
    		);
    		Integer id = response.getEntity(Integer.class);
    		System.out.println("Inserted Store with ID: " + id);
    		insertIdCache.getList().add(id);
    	}
    }

    @SuppressWarnings("unchecked")
    @When("^I update the following stores:$")
    public void I_update_the_following_stores(DataTable inserts) throws Throwable {
    	insertIdCache.getList().clear();
    	for (DataTableRow row : inserts.getGherkinRows()) {
    		ClientResponse<String> response = (ClientResponse<String>)guiberestRestService.putStore(
    				Integer.parseInt(row.getCells().get(0).trim()),
    				row.getCells().get(1).trim()
    		);
    		Integer id = response.getEntity(Integer.class);
    		System.out.println("Updated Store with ID: " + id);
    		insertIdCache.getList().add(id);
    	}
    }

    @SuppressWarnings("unused")
	@When("^I delete the following stores:$")
    public void I_delete_the_following_stores(DataTable deletes) throws Throwable {
    	insertIdCache.getList().clear();
    	for (DataTableRow row : deletes.getGherkinRows()) {
    		Response response = guiberestRestService.deleteStore(
    				Integer.parseInt(row.getCells().get(0).trim())
    		);
    	}
    }

    @Then("^I see no more than (\\d+) store results$")
    public void I_see_no_more_than_store_results(int results) throws Throwable {
    	System.out.println("Row Count: " + resultsCache.getData().size());
        Assert.assertTrue(resultsCache.getData().size() <= results);
    }

    

}
