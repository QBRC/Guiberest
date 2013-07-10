package edu.swmed.qbrc.guiberest.webapp.stepdefs;

import com.google.inject.Inject;
import cucumber.annotation.en.When;
import cucumber.table.DataTable;
import edu.swmed.qbrc.guiberest.webapp.guice.ClientIdentification;
import edu.swmed.qbrc.guiberest.webapp.guice.TestableEntityTester;

public class SaleStepdefs {
    
	final TestableEntityTester tester;
	private final ClientIdentification clientIdentification;
	
    @Inject
    public SaleStepdefs(final TestableEntityTester tester, final ClientIdentification clientIdentification) {
    	this.tester = tester;
		this.clientIdentification = clientIdentification;
    }
	
    private Boolean userOK(String userName) {
    	if (clientIdentification.getUsingCAS()) {
	    	tester.switchIdentity(userName); // Preserve switch, even though we cancel request
	    	Boolean ret = clientIdentification.getClientId().equals(clientIdentification.getLoggedInClientId());
	    	if (!ret) System.out.println("Not Using Default ID; Canceling Request......................required is " + clientIdentification.getClientId() + "; user is " + clientIdentification.getLoggedInClientId());
	    	else System.out.println("Allowing Test....");
	    	return ret;
    	} else {
    		return true;
    	}
    }

    @When("^I request sales with preauthorization for the following sale ids as user (\\S+):$")
	public void I_request_sales_with_preauthorization_for_the_following_sale_ids(String userName, DataTable ids) throws Throwable {
    	// Don't run any tests for non-default users
    	if (!userOK(userName)) return;
		tester.requestSalesWithPreAuth(ids, userName);
	}
	
}
