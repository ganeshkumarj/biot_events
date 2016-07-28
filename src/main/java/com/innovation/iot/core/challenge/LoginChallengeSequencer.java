package com.innovation.iot.core.challenge;

import java.util.LinkedList;
import java.util.Queue;

public class LoginChallengeSequencer implements ChallengeSequncer {
	Queue<ChallengeProcessor> sequence = null;

	public LoginChallengeSequencer() {
		sequence = new LinkedList<>();
		sequence.add(new LoginChallenge());
	}

	@Override
	public Queue<ChallengeProcessor> get() {
		return sequence;
	}

}
