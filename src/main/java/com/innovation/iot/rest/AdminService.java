package com.innovation.iot.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.innovation.iot.core.AdminManager;

@Path("/admin")
public class AdminService {

	private static final Logger LOG = Logger.getLogger(AdminService.class);

	@POST
	@Path("user")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String login(String loginContent) {
		LOG.debug("<login> login input : " + loginContent);
		String output = getManager().login(loginContent);
		LOG.debug("<login> output : " + output);
		return output;
	}

	@GET
	@Path("clean/{userCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public String cleanUserDetails(@PathParam("userCode") String userCode) {
		LOG.debug("<cleanUserDetails> userCode : " + userCode);
		String output = getManager().cleanUser(userCode);
		LOG.debug("<cleanUserDetails> output : " + output);
		return output;
	}

	@GET
	@Path("add/{userCode}/{userName}/{profile}")
	@Produces(MediaType.APPLICATION_JSON)
	public String addUser(@PathParam("userCode") String userCode, @PathParam("userName") String userName,
			@PathParam("profile") String profile) {
		LOG.debug("<addUser> userCode : " + userCode + ", userName : " + userName + ", profile : " + profile);
		String output = getManager().addUser(userCode, userName, profile);
		LOG.debug("<addUser> output : " + output);
		return output;
	}

	@GET
	@Path("messages/{managerCode}/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String cleanMessageDetails(@PathParam("managerCode") String managerCode,
			@PathParam("messageId") String messageId) {
		LOG.debug("<cleanMessageDetails> managerCode : " + managerCode + ", messageId : " + messageId);
		String output = getManager().cleanMessage(managerCode, messageId);
		LOG.debug("<cleanMessageDetails> output : " + output);
		return output;
	}

	@GET
	@Path("messages/{managerCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllMessagesOfMine(@PathParam("managerCode") String managerCode) {
		LOG.debug("<getAllMessagesOfMine> managerCode : " + managerCode);
		String output = getManager().allMessage(managerCode);
		LOG.debug("<getAllMessagesOfMine> output : " + output);
		return output;
	}

	@GET
	@Path("user")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUsersOnMyLocation(@QueryParam("userCode") String managerCode) {
		LOG.debug("<getUsersOnMyLocation> managerCode : " + managerCode);
		String output = getManager().getUsersOnMyLocation(managerCode);
		LOG.debug("<getUsersOnMyLocation> output : " + output);
		return output;
	}

	@POST
	@Path("message")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addMessage(String message) {
		LOG.debug("<addMessage> message : " + message);
		String output = getManager().addMessage(message);
		LOG.debug("<addMessage> output : " + output);
		return output;
	}

	private AdminManager getManager() {
		return AdminManager.getInstance();
	}

	@GET
	@Path("message")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSupportingDetailsForMessage() {
		String output = getManager().getSupportingDetails();
		LOG.debug("<getSupportingDetailsForMessage> output : " + output);
		return output;
	}
        
}
