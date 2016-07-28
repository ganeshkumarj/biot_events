package com.innovation.iot.representation.produces;

import java.util.List;

import com.innovation.iot.domain.Event;
import com.innovation.iot.representation.Status;

public class Communication {
	
	public List<Event> events;
	private Status status;

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Communication [events=");
		builder.append(events);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
