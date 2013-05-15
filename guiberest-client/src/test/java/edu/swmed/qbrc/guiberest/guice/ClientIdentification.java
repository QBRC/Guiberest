package edu.swmed.qbrc.guiberest.guice;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ClientIdentification {

	private String clientId;
	private String secret;
	
	private String thomas;
	private String sthomas;
	private String roger;
	private String sroger;
	private String sean;
	private String ssean;
	private String irsauditer;
	private String sirsauditer;
	
	@Inject
	public ClientIdentification(
			@Named("ClientId-thomas") final String thomas,
			@Named("Secret-thomas") final String sthomas,
			@Named("ClientId-roger") final String roger,
			@Named("Secret-roger") final String sroger,
			@Named("ClientId-sean") final String sean,
			@Named("Secret-sean") final String ssean,
			@Named("ClientId-irsauditer") final String irsauditer,
			@Named("Secret-irsauditer") final String sirsauditer
	) {
		this.thomas = thomas;
		this.sthomas = sthomas;
		this.roger = roger;
		this.sroger = sroger;
		this.sean = sean;
		this.ssean = ssean;
		this.irsauditer = irsauditer;
		this.sirsauditer = sirsauditer;
	}
	
	public void setThomas() {
		setClientId(thomas);
		setSecret(sthomas);
	}
	public void setRoger() {
		setClientId(roger);
		setSecret(sroger);
	}
	public void setSean() {
		setClientId(sean);
		setSecret(ssean);
	}
	public void setIrsAuditer() {
		setClientId(irsauditer);
		setSecret(sirsauditer);
	}
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
}
