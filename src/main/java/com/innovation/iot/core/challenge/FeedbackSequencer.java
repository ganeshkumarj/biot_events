package com.innovation.iot.core.challenge;

import java.util.LinkedList;
import java.util.Queue;

public class FeedbackSequencer implements ChallengeSequncer {

	Queue<ChallengeProcessor> sequence = null;

	public FeedbackSequencer() {
		sequence = new LinkedList<>();
		sequence.add(new PlaceFeedbackChallenge());
	}

	@Override
	public Queue<ChallengeProcessor> get() {
		return sequence;
	}

}
