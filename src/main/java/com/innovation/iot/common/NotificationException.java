package com.innovation.iot.common;

public class NotificationException extends Exception {
	
	public NotificationException( Throwable e ){
		super( e);
	}
	
	public NotificationException() {
		super();
	}

	public NotificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotificationException(String message) {
		super(message);
	}

}
