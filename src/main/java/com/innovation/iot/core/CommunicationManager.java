package com.innovation.iot.core;

import com.innovation.iot.common.NotificationException;
import com.innovation.iot.common.StatusType;
import com.innovation.iot.core.ExecutorService.CommunicationExecutionContext;
import com.innovation.iot.persistence.PersistenceManager;
import com.innovation.iot.representation.produces.Communication;

public class CommunicationManager {

	private static final CommunicationManager instance = new CommunicationManager();
	
	private CommunicationManager() {
	}

	public static CommunicationManager getInstance() {
		return instance;
	}

	public String getEvents(final String userCode) {
		String communications = null;
		communications = execute(new CommunicationExecutionContext() {
			public void execute(Communication communication) throws NotificationException {
				communication.setEvents( getPersistence().getAllEvents(userCode));
			}
		});
		return communications;
	}
	
	public String markPresence( final String userCode){
		return execute( new CommunicationExecutionContext() {
			public void execute(Communication communication) throws NotificationException {
				getPersistence().addLog(userCode);
				communication.setStatus(StatusType.Success.get("BIOK-0002"));;
			}
		});
	}

	private String execute(CommunicationExecutionContext context) {
		return new ExecutorService().execute(context);
	}

	private PersistenceManager getPersistence() {
		return PersistenceManager.getInstance();
	}
}