package com.innovation.iot.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.innovation.iot.core.NotificationManager;

@Path("/notifications")
public class NotificationService {

	private static final Logger LOG = Logger.getLogger(NotificationService.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{userCode}/{deviceId}/notification")
	public String getNotifications(@PathParam("deviceId") String deviceId, @PathParam("userCode") String userCode) {
		LOG.debug("<getNotifications> deviceId : " + deviceId + ",userCode : " + userCode);
		String output = getManager().getNotifications(deviceId, userCode);
		LOG.debug("<getNotifications> output : " + output);
		return output;
	}
	
	private static NotificationManager getManager() {
		return NotificationManager.getInstance();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("user")
	public String registerUser(String registerContent) {
		LOG.debug("<registerUser> input : " + registerContent);
		String output = getManager().registerUser(registerContent);
		LOG.debug("<registerUser> output : " + output);
		return output;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{userCode}/{deviceId}/feedback")
	public String feedback(@PathParam("userCode") String userCode, @PathParam("deviceId") String deviceId,
			String feedbackContent) {
		LOG.debug("<feedback> input : " + feedbackContent + ",user" + userCode + ", deviceId :" + deviceId);
		String output = getManager().feedback(userCode, deviceId, feedbackContent);
		LOG.debug("<feedback> output : " + output);
		return output;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{userCode}/{deviceId}/location")
	public String check(@PathParam("deviceId") String deviceId, @PathParam("userCode") String userCode,
			@QueryParam("check") String check) {
		LOG.debug("<check> deviceId : " + deviceId + ",userCode : " + userCode + ",check : " + check);
		String output = "";
		if ("in".equals(check)) {
			output = getManager().checkin(userCode, deviceId);
		} else {
			output = getManager().checkout(userCode, deviceId);
		}
		LOG.debug("<check> output : " + output);
		return output;
	}
}