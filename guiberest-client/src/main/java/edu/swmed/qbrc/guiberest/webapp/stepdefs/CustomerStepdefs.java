package edu.swmed.qbrc.guiberest.webapp.stepdefs;

import com.google.inject.Inject;

import edu.swmed.qbrc.guiberest.webapp.guice.TestableEntityTester;

public class CustomerStepdefs {
    
	@SuppressWarnings("unused")
	private final TestableEntityTester tester;
	
    @Inject
    public CustomerStepdefs(final TestableEntityTester tester) {
    	this.tester = tester;
    }

}
