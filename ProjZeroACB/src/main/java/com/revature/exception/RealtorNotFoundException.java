package com.revature.exception;

public class RealtorNotFoundException extends Exception {

	public RealtorNotFoundException() {
		super();
	}

	public RealtorNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RealtorNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public RealtorNotFoundException(String message) {
		super(message);
	}

	public RealtorNotFoundException(Throwable cause) {
		super(cause);
	}
	
	
	
}
