package com.innovation.iot.core;

import com.innovation.iot.common.DateUtil;
import com.innovation.iot.common.NotificationException;
import com.innovation.iot.core.ExecutorService.PresenceExecutionContext;
import com.innovation.iot.domain.CurrentLocation;
import com.innovation.iot.persistence.PersistenceManager;
import com.innovation.iot.representation.produces.Presence;

public class PresenceManager {

	private static final PresenceManager instance = new PresenceManager();

	private PresenceManager() {
	}

	public static PresenceManager getInstance() {
		return instance;
	}

	public String getTodayPresence(final String userCode) {
		return execute(new PresenceExecutionContext() {
			public void execute(Presence presence) throws NotificationException {
				presence.setLog(getPersistence().getTodayPresence(userCode));
                                presence.setCurrentLocation(getPersistence().getCurrentLocation(userCode, DateUtil.getDateString()));
                                setNetMinutes(presence);
                        }
		});
	} 
        
        private void setNetMinutes(Presence presence){
            Long totalNetMinutes = 0l;
            for (CurrentLocation currentLocation : presence.getCurrentLocation()) {
                totalNetMinutes = totalNetMinutes + currentLocation.getTotalMinutes();
            }
            presence.setNetMinutes(totalNetMinutes);
        }
        
	private String execute(PresenceExecutionContext context) {
		return new ExecutorService().execute(context);
	}

	private PersistenceManager getPersistence() {
		return PersistenceManager.getInstance();
	}

}
