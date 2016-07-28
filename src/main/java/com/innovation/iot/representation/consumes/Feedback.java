package com.innovation.iot.representation.consumes;

import java.util.List;

public class Feedback {

	private List<com.innovation.iot.domain.Feedback> feedbacks;

	public List<com.innovation.iot.domain.Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<com.innovation.iot.domain.Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Feedback [feedbacks=");
		builder.append(feedbacks);
		builder.append("]");
		return builder.toString();
	}

}
