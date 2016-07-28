package com.innovation.iot.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.innovation.iot.core.CommunicationManager;

@Path("/communications")
public class CommunicationService {
	
	private static final Logger LOG = Logger.getLogger(NotificationService.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/events/{userCode}")
	public String getEvents(@PathParam("userCode") String userCode) {
		LOG.debug("<getEvents> userCode : " + userCode);
		String output = getManager().getEvents(userCode);
		LOG.debug("<getEvents> output : " + output);
		return output;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/events")
	public String getEvents() {
		String output = getManager().getEvents(null);
		LOG.debug("<getEvents> output : " + output);
		return output;
	}
	
	private static CommunicationManager getManager() {
		return CommunicationManager.getInstance();
	}
}