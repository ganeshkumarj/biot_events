package com.innovation.iot.core;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovation.iot.common.NotificationException;
import com.innovation.iot.common.StatusType;
import com.innovation.iot.representation.Status;
import com.innovation.iot.representation.produces.Admin;
import com.innovation.iot.representation.produces.Communication;
import com.innovation.iot.representation.produces.Notification;
import com.innovation.iot.representation.produces.Presence;

public class ExecutorService {

	public String execute(PresenceExecutionContext context) {
		Status status = null;
		Presence presence = new Presence();
		try {
			context.execute(presence);
			status = StatusType.Success.get();
		} catch (NotificationException notificationException) {
			status = StatusType.Error.get(notificationException.getMessage());
		}
		if (presence.getStatus() == null) {
			presence.setStatus(status);
		}
		return getJSON(presence);
	}

	public String execute(NotificationExecutionContext context) {
		Status status = null;
		Notification notification = new Notification();
		try {
			context.execute(notification);
			status = StatusType.Success.get();
		} catch (NotificationException notificationException) {
			status = StatusType.Error.get(notificationException.getMessage());
		}
		if (notification.getStatus() == null) {
			notification.setStatus(status);
		}
		return getJSON(notification);
	}

	public String execute(CommunicationExecutionContext context) {
		Status status = null;
		Communication communication = new Communication();
		try {
			context.execute(communication);
			status = StatusType.Success.get();
		} catch (NotificationException notificationException) {
			status = StatusType.Error.get(notificationException.getMessage());
		}
		if (communication.getStatus() == null) {
			communication.setStatus(status);
		}
		return getJSON(communication);
	}

	public String execute(AdminExecutionContext context) {
		Status status = null;
		Admin admin = new Admin();
		try {
			context.execute(admin);
			status = StatusType.Success.get();
		} catch (NotificationException notificationException) {
			status = StatusType.Error.get(notificationException.getMessage());
		}
		admin.setStatus(status);
		return getJSON(admin);
	}

	private String getJSON(Object object) {
		String json = "";
		try {
			json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	interface NotificationExecutionContext {
		public abstract void execute(Notification notification) throws NotificationException;
	}

	interface AdminExecutionContext {
		public abstract void execute(Admin admin) throws NotificationException;
	}

	interface CommunicationExecutionContext {
		public abstract void execute(Communication communication) throws NotificationException;
	}

	interface PresenceExecutionContext {
		public abstract void execute(Presence presence) throws NotificationException;
	}

}
