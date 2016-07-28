package com.innovation.iot.representation.produces;

import java.util.List;

import com.innovation.iot.domain.Device;
import com.innovation.iot.domain.Message;
import com.innovation.iot.domain.MessageContent;
import com.innovation.iot.domain.User;
import com.innovation.iot.representation.Status;
  
public class Notification {

	private User user;
	private Device device;
	private Status status;
	private List<Message> announcements;
	private List<MessageContent> rewards;
	
	public List<MessageContent> getRewards() {
		return rewards;
	}

	public void setRewards(List<MessageContent> rewards) {
		this.rewards = rewards;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Message> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(List<Message> announcements) {
		this.announcements = announcements;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Notification [user=").append(user).append(", device=").append(device).append(", status=").append(status).append(", announcements=").append(announcements).append(", rewards=").append(rewards).append("]");
		return builder.toString();
	}

}
