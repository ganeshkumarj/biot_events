package com.innovation.iot.representation.produces;

import java.util.List;

import com.innovation.iot.domain.Message;
import com.innovation.iot.domain.Profile;
import com.innovation.iot.domain.User;
import com.innovation.iot.representation.Status;

public class Admin {
	private Status status;
	private List<User> users;
	private List<Profile> profiles;
	private User currentUser;
	private Profile currentProfile;
	private List<Message> messages;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public Profile getCurrentProfile() {
		return currentProfile;
	}

	public void setCurrentProfile(Profile currentProfile) {
		this.currentProfile = currentProfile;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Admin [status=");
		builder.append(status);
		builder.append(", users=");
		builder.append(users);
		builder.append(", profiles=");
		builder.append(profiles);
		builder.append(", currentUser=");
		builder.append(currentUser);
		builder.append(", currentProfile=");
		builder.append(currentProfile);
		builder.append(", messages=");
		builder.append(messages);
		builder.append("]");
		return builder.toString();
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
       
}
