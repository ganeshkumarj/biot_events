package com.innovation.iot.common;

public class NoDataFoundException extends NotificationException{

	public NoDataFoundException() {
		super();
	}

	public NoDataFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoDataFoundException(String message) {
		super(message);
	}

	public NoDataFoundException(Throwable cause) {
		super(cause);
	}

}
