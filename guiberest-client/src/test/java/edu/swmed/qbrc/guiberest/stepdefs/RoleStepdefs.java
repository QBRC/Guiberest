package edu.swmed.qbrc.guiberest.stepdefs;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.ClientResponse;
import org.junit.Assert;
import com.google.inject.Inject;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import cucumber.table.DataTable;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.Role;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.jacksonate.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.jacksonate.rest.util.IntegerArray;
import edu.swmed.qbrc.jacksonate.rest.util.StringArray;
import gherkin.formatter.model.DataTableRow;

public class RoleStepdefs {
    
	final GuiberestRestService guiberestRestService;
	
	final IntegerArray insertIdCache = new IntegerArray();
	TableJSONContainer<Role> resultsCache;
	
    @Inject
    public RoleStepdefs(final GuiberestRestService guiberestRestService) {
    	this.guiberestRestService = guiberestRestService;
    }
	
    @When("^I request roles for the following users:$")
    public void I_request_roles_with_the_following_role_ids(DataTable ids) throws Throwable {
    	final StringArray idArray = new StringArray();
    	for (DataTableRow row : ids.getGherkinRows()) {
    		idArray.getList().add(row.getCells().get(0));
    	}
    	resultsCache = guiberestRestService.getRoles(idArray);
    }

    @Then("^I see the following full role results:$")
    public void I_see_the_following_full_role_results(DataTable results) throws Throwable {
    	Integer index = 0;
    	for (DataTableRow row : results.getGherkinRows()) {
    		Role role = resultsCache.getData().get(index++);
    		Assert.assertTrue(
    				role.getId().toString().trim().equals(row.getCells().get(0)) &&
    				role.getUsername().trim().equals(row.getCells().get(1)) &&
    				role.getRole().trim().equals(row.getCells().get(2))
    		);
    	}
    }
    
    @SuppressWarnings("unchecked")
    @When("^I insert the following roles:$")
    public void I_insert_the_following_roles(DataTable inserts) throws Throwable {
    	insertIdCache.getList().clear();
    	for (DataTableRow row : inserts.getGherkinRows()) {
    		ClientResponse<String> response = (ClientResponse<String>)guiberestRestService.putRole(
    				Integer.parseInt(row.getCells().get(0).trim()),
    				row.getCells().get(1).trim(),
    				row.getCells().get(2).trim()
    		);
    		Integer id = response.getEntity(Integer.class);
    		System.out.println("Inserted Role with ID: " + id);
    		insertIdCache.getList().add(id);
    	}
    }

    @SuppressWarnings("unchecked")
    @When("^I update the following roles:$")
    public void I_update_the_following_roles(DataTable inserts) throws Throwable {
    	insertIdCache.getList().clear();
    	for (DataTableRow row : inserts.getGherkinRows()) {
    		ClientResponse<String> response = (ClientResponse<String>)guiberestRestService.putRole(
    				Integer.parseInt(row.getCells().get(0).trim()),
    				row.getCells().get(1).trim(),
    				row.getCells().get(2).trim()
    		);
    		Integer id = response.getEntity(Integer.class);
    		System.out.println("Updated Role with ID: " + id);
    		insertIdCache.getList().add(id);
    	}
    }

    @SuppressWarnings("unused")
	@When("^I delete the following roles:$")
    public void I_delete_the_following_roles(DataTable deletes) throws Throwable {
    	insertIdCache.getList().clear();
    	for (DataTableRow row : deletes.getGherkinRows()) {
    		Response response = guiberestRestService.deleteRole(
    				Integer.parseInt(row.getCells().get(0).trim())
    		);
    	}
    }

    @Then("^I see no more than (\\d+) role results$")
    public void I_see_no_more_than_role_results(int results) throws Throwable {
    	System.out.println("Row Count: " + resultsCache.getData().size());
        Assert.assertTrue(resultsCache.getData().size() <= results);
    }
    
}
