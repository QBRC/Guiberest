package edu.swmed.qbrc.guiberest.webapp.stepdefs;

import com.google.inject.Inject;

import edu.swmed.qbrc.guiberest.webapp.guice.TestableEntityTester;

public class StoreStepdefs {
    
	final TestableEntityTester tester;
	
    @Inject
    public StoreStepdefs(final TestableEntityTester tester) {
    	this.tester = tester;
    }

}
