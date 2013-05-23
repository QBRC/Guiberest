package edu.swmed.qbrc.guiberest.stepdefs;

import com.google.inject.Inject;
import edu.swmed.qbrc.guiberest.tester.TestableEntityTester;

public class StoreStepdefs {
    
	final TestableEntityTester tester;
	
    @Inject
    public StoreStepdefs(final TestableEntityTester tester) {
    	this.tester = tester;
    }

}
