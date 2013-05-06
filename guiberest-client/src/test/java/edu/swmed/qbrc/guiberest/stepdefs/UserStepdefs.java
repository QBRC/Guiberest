package edu.swmed.qbrc.guiberest.stepdefs;

import org.junit.Assert;
import com.google.inject.Inject;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import cucumber.table.DataTable;
import edu.swmed.qbrc.guiberest.shared.domain.guiberest.User;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;
import edu.swmed.qbrc.jacksonate.rest.jackson.TableJSONContainer;
import edu.swmed.qbrc.jacksonate.rest.util.IntegerArray;
import edu.swmed.qbrc.jacksonate.rest.util.StringArray;
import gherkin.formatter.model.DataTableRow;

public class UserStepdefs {
    
	final GuiberestRestService guiberestRestService;
	
	final IntegerArray insertIdCache = new IntegerArray();
	TableJSONContainer<User> resultsCache;
	
    @Inject
    public UserStepdefs(final GuiberestRestService guiberestRestService) {
    	this.guiberestRestService = guiberestRestService;
    }
	
    @Given("^I'm ready to go$")
    public void I_m_ready_to_go() throws Throwable {
        Assert.assertTrue(true);
    }

    @When("^I request users with the following user ids:$")
    public void I_request_users_with_the_following_user_ids(DataTable ids) throws Throwable {
    	final StringArray idArray = new StringArray();
    	for (DataTableRow row : ids.getGherkinRows()) {
    		idArray.getList().add(row.getCells().get(0));
    	}
    	resultsCache = guiberestRestService.getUsers(idArray);
    }

    @Then("^I see the following full user results:$")
    public void I_see_the_following_full_user_results(DataTable results) throws Throwable {
    	Integer index = 0;
    	for (DataTableRow row : results.getGherkinRows()) {
    		User user = resultsCache.getData().get(index++);
    		Assert.assertTrue(
    				user.getId().trim().equals(row.getCells().get(0)) &&
    				user.getPassword().trim().equals(row.getCells().get(1)) &&
    				user.getSecret().trim().equals(row.getCells().get(2))
    		);
    	}
    }

    @When("^I request all users$")
    public void I_request_all_users() throws Throwable {
    	resultsCache = guiberestRestService.getUsers();
    }

    @Then("^I see at least (\\d+) user results$")
    public void I_see_at_least_x_user_results(int results) throws Throwable {
    	System.out.println("Row Count: " + resultsCache.getData().size());
        Assert.assertTrue(resultsCache.getData().size() >= results);
    }

}
