package edu.swmed.qbrc.guiberest.stepdefs;

import com.google.inject.Inject;
import cucumber.annotation.en.When;
import cucumber.table.DataTable;
import edu.swmed.qbrc.guiberest.tester.TestableEntityTester;

public class SaleStepdefs {
    
	final TestableEntityTester tester;
	
    @Inject
    public SaleStepdefs(final TestableEntityTester tester) {
    	this.tester = tester;
    }
	
	@When("^I request sales with preauthorization for the following sale ids as user (\\S+):$")
	public void I_request_sales_with_preauthorization_for_the_following_sale_ids(String userName, DataTable ids) throws Throwable {
		tester.requestSalesWithPreAuth(ids, userName);
	}
	
}
