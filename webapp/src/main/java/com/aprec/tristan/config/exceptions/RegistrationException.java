package com.aprec.tristan.config.exceptions;

public class RegistrationException extends IllegalStateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2406379223425969664L;

	public RegistrationException() {
		super();
	}

	public RegistrationException(String message, Throwable cause) {
		super(message, cause);
	}

	public RegistrationException(String s) {
		super(s);
	}

	public RegistrationException(Throwable cause) {
		super(cause);
	}

}
