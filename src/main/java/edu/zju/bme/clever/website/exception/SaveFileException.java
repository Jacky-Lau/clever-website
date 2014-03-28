package edu.zju.bme.clever.website.exception;

public class SaveFileException extends RuntimeException {
	private static final long serialVersionUID = 8900605557977537238L;

	public SaveFileException() {

	}

	public SaveFileException(String msg) {
		super(msg);
	}

	public SaveFileException(Throwable e) {
		super(e);
	}
}
