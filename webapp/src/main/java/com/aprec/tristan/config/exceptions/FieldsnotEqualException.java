package com.aprec.tristan.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class FieldsnotEqualException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2727513286809008744L;

	public FieldsnotEqualException() {
		super();
	}

	public FieldsnotEqualException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FieldsnotEqualException(String message, Throwable cause) {
		super(message, cause);
	}

	public FieldsnotEqualException(String message) {
		super(message);
	}

	public FieldsnotEqualException(Throwable cause) {
		super(cause);
	}


}
