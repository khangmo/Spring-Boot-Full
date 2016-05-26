package com.example.modal;

public class ProjectException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private int errorCode;

    public ProjectException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ProjectException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
