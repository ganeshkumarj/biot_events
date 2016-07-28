  package com.innovation.iot.external.gamification.representation.consumes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GamificationResponse {
	
	@Override
	public String toString() {
		return "GamificationPointsResponse [Response=" + response + "]";
	}

	@JsonProperty("Response")
	private List<Badge> response;

	public List<Badge> getResponse() {
		return response;
	}

	public void setResponse(List<Badge> response) {
		this.response = response;
	}

	
}
