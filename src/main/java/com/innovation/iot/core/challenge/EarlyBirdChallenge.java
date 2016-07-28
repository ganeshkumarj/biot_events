package com.innovation.iot.core.challenge;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.innovation.iot.domain.Challenge;

public class EarlyBirdChallenge extends AbstractRequestProcessor {

	@Override
	public boolean doProcess(Challenge challenge) {
		SimpleDateFormat formater = new SimpleDateFormat("kk");
		String currentHour = formater.format(new Date());
		if (challenge.getStart() != null && challenge.getEnd() == null) {
			String thresoldHour = formater.format(challenge.getStart());
			LOG.debug("<EarlyBird> Current Hour" + currentHour);
			LOG.debug("<EarlyBird> Thresold Hour" + thresoldHour);
			if (new Integer(currentHour) < new Integer(thresoldHour)) {
				setProcessed();
			}
		}
		return isProcessed();
	}
}