package com.innovation.iot.core.challenge;

import java.util.LinkedList;
import java.util.Queue;

public class DefaultChallengeSequencer implements ChallengeSequncer {
	Queue<ChallengeProcessor> sequence = null;

	public DefaultChallengeSequencer() {
		sequence = new LinkedList<>();
		sequence.add(new EarlyBirdChallenge());
		sequence.add(new OffPeakChallenge());
		sequence.add(new PlaceVisitChallenge());
		sequence.add(new BranchVisitChallenge());
	}

	@Override
	public Queue<ChallengeProcessor> get() {
		return sequence;
	}

}
