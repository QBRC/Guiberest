package edu.swmed.qbrc.guiberest.webapp.guice;

import com.google.inject.Inject;
import com.google.inject.Provider;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;

public class TestableEntityTesterProvider implements Provider<TestableEntityTester> {

	private static TestableEntityTester testableEntityTester;
	
	private final GuiberestRestService guiberestRestService;
	private final ClientIdentification clientIdentification;
	
	@Inject
	public TestableEntityTesterProvider(final GuiberestRestService guiberestRestService, final ClientIdentification clientIdentification) {
		this.guiberestRestService = guiberestRestService;
		this.clientIdentification = clientIdentification;
	}
	
	@Override
	public TestableEntityTester get() {
		if (testableEntityTester == null) {
			testableEntityTester = new TestableEntityTester(guiberestRestService, clientIdentification);
		}
		return testableEntityTester;
	}

}
