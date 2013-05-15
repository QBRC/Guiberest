package edu.swmed.qbrc.guiberest.guice;

import com.google.inject.Provider;

public class ClientIdentificationProvider implements Provider<ClientIdentification> {

	private static ClientIdentification cid;

	public static void setInstance(final ClientIdentification mycid) {
		cid = mycid;
	}
	
	@Override
	public ClientIdentification get() {
		return cid;
	}

}
