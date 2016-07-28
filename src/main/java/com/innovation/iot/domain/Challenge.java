package com.innovation.iot.domain;

import java.sql.Timestamp;

public class Challenge {

	private String deviceId;
	private String challengeCode;
	private Timestamp start;
	private Timestamp end;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getChallengeCode() {
		return challengeCode;
	}

	public void setChallengeCode(String challengeCode) {
		this.challengeCode = challengeCode;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	public Challenge(String deviceId, String challengeCode, Timestamp start, Timestamp end) {
		super();
		this.deviceId = deviceId;
		this.challengeCode = challengeCode;
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Challenge [deviceId=");
		builder.append(deviceId);
		builder.append(", challengeCode=");
		builder.append(challengeCode);
		builder.append(", start=");
		builder.append(start);
		builder.append(", end=");
		builder.append(end);
		builder.append("]");
		return builder.toString();
	}

}
