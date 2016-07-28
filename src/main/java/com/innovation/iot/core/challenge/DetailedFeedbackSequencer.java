package com.innovation.iot.core.challenge;

import java.util.LinkedList;
import java.util.Queue;

public class DetailedFeedbackSequencer implements ChallengeSequncer {

	Queue<ChallengeProcessor> sequence = null;

	public DetailedFeedbackSequencer() {
		sequence = new LinkedList<>();
		sequence.add(new DetailedFeedbackChallenge());
	}

	@Override
	public Queue<ChallengeProcessor> get() {
		return sequence;
	}

}
