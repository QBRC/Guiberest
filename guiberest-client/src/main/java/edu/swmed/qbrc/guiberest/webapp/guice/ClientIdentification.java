package edu.swmed.qbrc.guiberest.webapp.guice;

public class ClientIdentification {

	private String clientId;
	private String secret;
	private Boolean usingCAS;
	private String loggedInUser;
	
	private String thomas;
	private String sthomas;
	private String roger;
	private String sroger;
	private String sean;
	private String ssean;
	private String irsauditer;
	private String sirsauditer;
	private String guest;
	private String sguest;
	private String cook;
	private String scook;
	
	public ClientIdentification(
			final String thomas,
			final String sthomas,
			final String roger,
			final String sroger,
			final String sean,
			final String ssean,
			final String irsauditer,
			final String sirsauditer,
			final String guest,
			final String sguest,
			final String cook,
			final String scook,
			final String usingCAS
	) {
		this.thomas = thomas;
		this.sthomas = sthomas;
		this.roger = roger;
		this.sroger = sroger;
		this.sean = sean;
		this.ssean = ssean;
		this.irsauditer = irsauditer;
		this.sirsauditer = sirsauditer;
		this.guest = guest;
		this.sguest = sguest;
		this.cook = cook;
		this.scook = scook;
		this.setThomas(); // Default to Thomas user
		this.setUsingCAS(usingCAS.equalsIgnoreCase("true"));
		this.loggedInUser = "";
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
	public void setGuest() {
		setClientId(guest);
		setSecret(sguest);
	}
	public void setCook() {
		setClientId(cook);
		setSecret(scook);
	}

	public void setLoggedInClientId(String clientId) {
		this.loggedInUser = clientId;
	}
	public String getLoggedInClientId() {
		return this.loggedInUser;
	}

	public Boolean getUsingCAS() {
		return usingCAS;
	}
	public void setUsingCAS(Boolean usingCAS) {
		this.usingCAS = usingCAS;
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
