package edu.swmed.qbrc.guiberest.stepdefs;

import org.junit.Assert;
import com.google.inject.Inject;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import cucumber.table.DataTable;
import edu.swmed.qbrc.guiberest.tester.TestableEntityTester;

public class GenericStepdefs {
	
	final TestableEntityTester tester;
	
    @Inject
    public GenericStepdefs(final TestableEntityTester tester) {
    	this.tester = tester;
    }
	
    @Given("^I'm ready to go$")
    public void I_m_ready_to_go() throws Throwable {
        Assert.assertTrue(true);
    }

    @When("^I request (\\S+) data for the following ids as user (\\S+):$")
    public void I_request_x_data_with_the_following_ids_as_user(String type, String userName, DataTable ids) throws Throwable {
    	tester.requestXforTheFollowingIds(type, ids, userName);
    }

    @When("^I request (\\S+) data for the (\\d+) id and the (\\S+) class as user (\\S+)$")
    public void I_request_x_data_for_the_x_id_as_user(String type, Integer customerId, String className, String userName) throws Throwable {
    	tester.requestXforThisIdAndClass(type, customerId, className, userName);
    }
    
    @When("^I request (\\S+) data for the (\\d+) id as user (\\S+)$")
    public void I_request_x_data_for_the_x_id_as_user(String type, Integer customerId, String userName) throws Throwable {
    	tester.requestXforThisId(type, customerId, userName);
    }

    @Then("^I see the following (\\S+) results:$")
    public void I_see_the_following_x_results(String type, DataTable results) throws Throwable {
    	Assert.assertTrue(tester.checkTheFollowingXResults(type, results));
    }
    
    @When("^I insert the following (\\S+) data as user (\\S+):$")
    public void I_insert_the_following_x_data_as_user(String type, String userName, DataTable inserts) throws Throwable {
		System.out.println("Will now insert data of type " + type + "...");
		tester.putXfortheFollowingX(type, inserts, userName);
    }

    @When("^I update the following (\\S+) data as user (\\S+):$")
    public void I_update_the_following_x_data_as_user(String type, String userName, DataTable inserts) throws Throwable {
		tester.putXfortheFollowingX(type, inserts, userName);
    }

	@When("^I delete the following (\\S+) data as user (\\S+):$")
    public void I_delete_the_following_x_data_as_user(String type, String userName, DataTable deletes) throws Throwable {
    	tester.deleteXfortheFollowingX(type, deletes, userName);
    }

    @Then("^I see no more than (\\d+) (\\S+) results$")
    public void I_see_no_more_than_x_type_results(int results, String type) throws Throwable {
        Assert.assertTrue(tester.checkForLessThanOrEqualXresults(type, results));
    }

    @Then("^I see less than (\\d+) (\\S+) results$")
    public void I_see_less_than_x_type_results(int results, String type) throws Throwable {
        Assert.assertTrue(tester.checkForLessThanXresults(type, results));
    }
    
    @Then("^I see more than (\\d+) (\\S+) results$")
    public void I_see_more_than_x_type_results(int results, String type) throws Throwable {
        Assert.assertTrue(tester.checkForGreaterThanXresults(type, results));
    }

    @Then("^I see at least (\\d+) (\\S+) results$")
    public void I_see_at_least_x_type_results(int results, String type) throws Throwable {
        Assert.assertTrue(tester.checkForGreaterThanOrEqualXresults(type, results));
    }

    @Then("^I see exactly (\\d+) (\\S+) results$")
    public void I_see_exactly_x_type_results(int results, String type) throws Throwable {
        Assert.assertTrue(tester.checkForEqualsXresults(type, results));
    }
    
    @When("^I receive a NoAclException$")
    public void I_receive_a_NoAclException() throws Throwable {
    	Assert.assertTrue(tester.iSeeNoAclException());
    }

}
