package com.innovation.iot.domain;

import java.sql.Timestamp;
import java.util.List;

public class Message {

	private int id;
	private List<MessageContent> messages;
	private String intervel;
	private User createdBy;
	private Timestamp startTimeStamp;
	private Timestamp endTimeStamp;

	public Message(int id, List<MessageContent> messages, String intervel, String createdBy, Timestamp start, Timestamp end) {
		this.id = id;
		this.messages = messages;
		this.intervel = intervel;
		this.createdBy = new User(createdBy);
		this.startTimeStamp = start;
		this.endTimeStamp = end;
	}

	public Message(int id, List<MessageContent> messages, String createdBy, Timestamp start) {
		this(id, messages, null, createdBy, start, null);
	}
	
	public Message(int id, List<MessageContent> messages, String createdBy, Timestamp start, Timestamp end) {
		this(id, messages, null, createdBy, start, end);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIntervel() {
		return intervel;
	}

	public void setIntervel(String intervel) {
		this.intervel = intervel;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getStartTimeStamp() {
		return startTimeStamp;
	}

	public void setStartTimeStamp(Timestamp startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}

	public Timestamp getEndTimeStamp() {
		return endTimeStamp;
	}

	public void setEndTimeStamp(Timestamp endTimeStamp) {
		this.endTimeStamp = endTimeStamp;
	}

	public List<MessageContent> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageContent> messages) {
		this.messages = messages;
	}

}
