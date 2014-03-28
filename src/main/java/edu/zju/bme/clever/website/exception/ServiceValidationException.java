package edu.zju.bme.clever.website.exception;

public class ServiceValidationException extends RuntimeException {
	private static final long serialVersionUID = -2145239778037939925L;

	public ServiceValidationException() {

	}

	public ServiceValidationException(String msg) {
		super(msg);
	}

	public ServiceValidationException(Throwable e) {
		super(e);
	}
}
