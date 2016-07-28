package com.innovation.iot.core.challenge;

import java.util.Queue;

import com.innovation.iot.domain.Challenge;

public class SequenceHandler {

	private Queue<ChallengeProcessor> processSequence;

	private Queue<ChallengeProcessor> getProcessSequence() {
		return processSequence;
	}

	private ChallengeProcessor next() {
		return getProcessSequence().poll();
	}

	public SequenceHandler(final ChallengeSequncer sequencer) {
		this.processSequence = sequencer.get();
	}

	public boolean doProcess(final Challenge challenge) {
		boolean processedOutPut = false;
		final ChallengeProcessor processer = next();
		if (processer != null) {
			processedOutPut = processer.doProcess(challenge);
			if (!processer.isProcessed()) {
				processedOutPut = doProcess(challenge);
			}
		}
		return processedOutPut;
	}

}