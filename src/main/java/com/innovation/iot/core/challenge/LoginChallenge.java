package com.innovation.iot.core.challenge;

import com.innovation.iot.domain.Challenge;

public class LoginChallenge extends AbstractRequestProcessor {

	@Override
	public boolean doProcess(Challenge challenge) {
		if (isValidCode(challenge.getChallengeCode())
				&& challenge.getChallengeCode().contains(bundle.getString("challenge.login"))) {
			setProcessed();
		}
		return isProcessed();
	}

}