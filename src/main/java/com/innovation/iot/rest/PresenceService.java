package com.innovation.iot.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
import org.apache.log4j.Logger;

import com.innovation.iot.core.PresenceManager;

@Path("/presence")
public class PresenceService {

	private static final Logger LOG = Logger.getLogger(PresenceService.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/today/{userCode}")
	public String getTodayPresence(@PathParam("userCode") String userCode) {
		LOG.debug("<markPresence> userCode : " + userCode);
		String output = getManager().getTodayPresence(userCode);
		LOG.debug("<markPresence> output : " + output);
		return output;
	}
        
	private static PresenceManager getManager() {
		return PresenceManager.getInstance();
	}
}