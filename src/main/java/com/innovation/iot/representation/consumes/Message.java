package com.innovation.iot.representation.consumes;

import java.util.List;

import com.innovation.iot.domain.MessageContent;

public class Message {

	private List<MessageContent> messages;
	private List<Integer> profiles;
	private List<String> users;
	private String created;
	private String interval;
	private String start;
	private String end;

	public List<MessageContent> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageContent> messages) {
		this.messages = messages;
	}

	public List<Integer> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Integer> profiles) {
		this.profiles = profiles;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getInterval() {
		return "1D";
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [messages=").append(messages).append(", profiles=").append(profiles).append(", users=").append(users).append(", created=").append(created).append(", interval=").append(interval).append(", start=").append(start).append(", end=").append(end)
				.append("]");
		return builder.toString();
	}

}
