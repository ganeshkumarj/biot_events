package com.innovation.iot.core.challenge;

import com.innovation.iot.domain.Challenge;

public interface ChallengeProcessor {

	void setProcessed();

	boolean isProcessed();

	public boolean doProcess(Challenge challenge);

}
