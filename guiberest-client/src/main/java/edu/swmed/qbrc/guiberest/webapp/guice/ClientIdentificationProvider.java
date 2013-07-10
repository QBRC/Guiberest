package edu.swmed.qbrc.guiberest.webapp.guice;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class ClientIdentificationProvider implements Provider<ClientIdentification> {

	private static ClientIdentification cid;

	@Inject
	public ClientIdentificationProvider(
			@Named("ClientId-thomas") final String thomas,
			@Named("Secret-thomas") final String sthomas,
			@Named("ClientId-roger") final String roger,
			@Named("Secret-roger") final String sroger,
			@Named("ClientId-sean") final String sean,
			@Named("Secret-sean") final String ssean,
			@Named("ClientId-irsauditer") final String irsauditer,
			@Named("Secret-irsauditer") final String sirsauditer,
			@Named("ClientId-guest") final String guest,
			@Named("Secret-guest") final String sguest,
			@Named("ClientId-cook") final String cook,
			@Named("Secret-cook") final String scook,
			@Named("usingCAS") final String usingCAS
	) {
		if (cid == null) {
			cid = new ClientIdentification(thomas, sthomas, roger, sroger, sean, ssean, irsauditer, sirsauditer, guest, sguest, cook, scook, usingCAS);
		}
	}
	
	@Override
	public ClientIdentification get() {
		return cid;
	}

}
