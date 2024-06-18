package com.aprec.tristan.config.exceptions;

public class PasswordRequestException extends IllegalStateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3200681430901038524L;

	public PasswordRequestException() {
		super();
	}

	public PasswordRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordRequestException(String s) {
		super(s);
	}

	public PasswordRequestException(Throwable cause) {
		super(cause);
	}

}
