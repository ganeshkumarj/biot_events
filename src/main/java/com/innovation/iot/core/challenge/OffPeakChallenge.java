package com.innovation.iot.core.challenge;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.innovation.iot.common.StringUtil;
import com.innovation.iot.domain.Challenge;

public class OffPeakChallenge extends AbstractRequestProcessor {

	@Override
	public boolean doProcess(Challenge challenge) {
		SimpleDateFormat formater = new SimpleDateFormat("kk");
		String currentHour = formater.format(new Date());
		if (StringUtil.isNotNull(challenge.getStart()) && StringUtil.isNotNull(challenge.getEnd())) {
			String thresoldStartHour = formater.format(challenge.getStart());
			String thresoldEndHour = formater.format(challenge.getEnd());
			LOG.debug("<OffPeak> Current Hour" + currentHour);
			LOG.debug("<OffPeak> Thresold Start Hour" + thresoldStartHour);
			LOG.debug("<OffPeak> Thresold End Hour" + thresoldEndHour);
			if ((new Integer(currentHour) >= new Integer(thresoldStartHour))
					&& (new Integer(currentHour) <= new Integer(thresoldEndHour))) {
				setProcessed();
			}
		}
		return isProcessed();
	}
}