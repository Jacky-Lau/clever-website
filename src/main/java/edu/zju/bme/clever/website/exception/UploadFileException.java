package edu.zju.bme.clever.website.exception;

public class UploadFileException extends RuntimeException {
	private static final long serialVersionUID = -3911316137024305696L;

	public UploadFileException() {

	}

	public UploadFileException(String msg) {
		super(msg);
	}
}
