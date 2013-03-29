package edu.swmed.qbrc.guiberest.shared.exception;

public class IllegalArgException extends Exception {

	private static final long serialVersionUID = -8497207692157879965L;

	public IllegalArgException(){
		super();
	}
	
	public IllegalArgException(String msg){
		super(msg);
	}
}
