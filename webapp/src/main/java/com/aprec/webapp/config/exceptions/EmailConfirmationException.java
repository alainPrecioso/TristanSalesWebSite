package com.aprec.webapp.config.exceptions;

public class EmailConfirmationException extends RegistrationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8260780639903267255L;

	public EmailConfirmationException() {
		super();
	}

	public EmailConfirmationException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailConfirmationException(String s) {
		super(s);
	}

	public EmailConfirmationException(Throwable cause) {
		super(cause);
	}
	
}
