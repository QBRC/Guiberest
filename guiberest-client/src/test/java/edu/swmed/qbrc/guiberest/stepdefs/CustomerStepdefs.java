package edu.swmed.qbrc.guiberest.stepdefs;

import com.google.inject.Inject;
import edu.swmed.qbrc.guiberest.tester.TestableEntityTester;

public class CustomerStepdefs {
    
	@SuppressWarnings("unused")
	private final TestableEntityTester tester;
	
    @Inject
    public CustomerStepdefs(final TestableEntityTester tester) {
    	this.tester = tester;
    }

}
