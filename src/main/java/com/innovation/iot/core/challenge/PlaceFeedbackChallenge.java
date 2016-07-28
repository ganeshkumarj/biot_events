package com.innovation.iot.core.challenge;

import com.innovation.iot.domain.Challenge;

public class PlaceFeedbackChallenge  extends AbstractRequestProcessor {

	@Override
	public boolean doProcess(Challenge challenge) {
		if (isValidCode(challenge.getChallengeCode())
				&& challenge.getChallengeCode().contains(bundle.getString("challenge.feedback.identifier"))) {
			setProcessed();
		}
		return isProcessed();
	}

}
