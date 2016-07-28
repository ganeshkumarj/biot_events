package com.innovation.iot.domain;

import java.sql.Timestamp;

public class Event {

	private int id;
	private User createdBy;
	private Timestamp startTimeStamp;
	private Timestamp endTimeStamp;
	private String location;
	private String content;
	private String title;

	public Event(int id, String createdBy, Timestamp startTimeStamp, Timestamp endTimeStamp, String location, String content, String title) {
		this.id = id;
		this.createdBy = new User(createdBy);
		this.startTimeStamp = startTimeStamp;
		this.endTimeStamp = endTimeStamp;
		this.location = location;
		this.content = content;
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
