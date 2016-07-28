package com.innovation.iot.core.challenge;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.innovation.iot.common.StringUtil;

public abstract class AbstractRequestProcessor implements ChallengeProcessor {

	protected static final ResourceBundle bundle = ResourceBundle.getBundle("Challenge");
	protected static final Logger LOG = Logger.getLogger(AbstractRequestProcessor.class);

	boolean isProcessed = Boolean.FALSE;

	public boolean isProcessed() {
		return isProcessed;
	}

	public void setProcessed() {
		isProcessed = Boolean.TRUE;
	}
	
	protected boolean isValidCode(String challengeCode){
		return StringUtil.isNotNull(challengeCode);
	}

}
