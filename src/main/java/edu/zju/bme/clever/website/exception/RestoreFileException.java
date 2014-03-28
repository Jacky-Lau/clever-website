package edu.zju.bme.clever.website.exception;

public class RestoreFileException extends RuntimeException {
	private static final long serialVersionUID = -3980110388158966169L;

	public RestoreFileException() {

	}

	public RestoreFileException(String msg) {
		super(msg);
	}

	public RestoreFileException(Throwable e) {
		super(e);
	}
}
