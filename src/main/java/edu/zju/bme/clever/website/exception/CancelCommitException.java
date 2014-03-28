package edu.zju.bme.clever.website.exception;

public class CancelCommitException extends RuntimeException {
	private static final long serialVersionUID = -6169835076237433357L;

	public CancelCommitException() {

	}

	public CancelCommitException(String msg) {
		super(msg);
	}
}
