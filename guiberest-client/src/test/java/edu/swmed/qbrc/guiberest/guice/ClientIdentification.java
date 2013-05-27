package edu.swmed.qbrc.guiberest.guice;

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
			final String scook
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
