package com.innovation.iot.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.innovation.iot.common.NotificationException;
import com.innovation.iot.common.ObjectConverter;
import com.innovation.iot.common.PathBuilder;
import com.innovation.iot.common.PathBuilder.Base;
import com.innovation.iot.common.StringUtil;
import com.innovation.iot.domain.MessageContent;
import com.innovation.iot.external.gamification.Gamification;
import com.innovation.iot.external.gamification.representation.consumes.Badge;
import com.innovation.iot.external.gamification.representation.consumes.GamificationResponse;

public class GamificationManager {

	private static final GamificationManager instance = new GamificationManager();
	private static final ResourceBundle bundle = ResourceBundle.getBundle("External");
	private static final Logger LOG = Logger.getLogger(Gamification.class);
	
	private GamificationManager() {
	}

	public static GamificationManager getInstance() {
		return instance;
	}

	public String registerUser(String userCode, String userName) {
		return getGamification().register(userCode, userName);
	}

	public void awardUser(final String userCode, List<String> challenges) {
		LOG.debug("<awardUser> challenges : "+challenges);
		for (String challenge : challenges) {
			getGamification().awardUser(userCode, challenge);
		}
	}

	public List<MessageContent> points(final String userCode) {
		List<MessageContent> contents = new ArrayList<MessageContent>();
		try {
			String responseFromServer = getGamification().badges(userCode);
			if (!StringUtil.isEmpty(responseFromServer)) {
				GamificationResponse response = ObjectConverter.convert(responseFromServer,
						GamificationResponse.class);
				
				if (StringUtil.isNotNull(response)) {
					List<Badge> badges = response.getResponse();
					LOG.debug("<points> list of badges"+badges);
					if( StringUtil.isNotNull(badges)){
						for (Badge badge : badges) {
							contents.add(new MessageContent(badge.getStory(), formattedMessage(badge.getImage())));
						}
					}
				}
			}
		} catch (NotificationException e) {
			e.printStackTrace();
		}
		Collections.reverse(contents);
		return contents;
	}

	private Gamification getGamification() {
		return Gamification.getInstance();
	}

	private String formattedMessage(String image) {
		return PathBuilder.build(image, Base.REWARD);
	}
}