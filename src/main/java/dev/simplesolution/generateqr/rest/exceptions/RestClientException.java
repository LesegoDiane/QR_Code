package dev.simplesolution.generateqr.rest.exceptions;

public class RestClientException extends Exception {

	private static final long serialVersionUID = 1L;

	public RestClientException(String message) {
		super(message);
	}
	
	public RestClientException(Throwable e) {
		super(e);
	}

}

