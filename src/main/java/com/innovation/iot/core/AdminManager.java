package com.innovation.iot.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import com.innovation.iot.common.NotificationException;
import com.innovation.iot.common.ObjectConverter;
import com.innovation.iot.core.ExecutorService.AdminExecutionContext;
import com.innovation.iot.domain.MessageContent;
import com.innovation.iot.domain.User;
import com.innovation.iot.persistence.PersistenceManager;
import com.innovation.iot.representation.consumes.Login;
import com.innovation.iot.representation.consumes.Message;
import com.innovation.iot.representation.produces.Admin;

public class AdminManager {
	private static final AdminManager instance = new AdminManager();
	private static final ResourceBundle bundle = ResourceBundle.getBundle("Admin");

	private AdminManager() {
	}

	public static AdminManager getInstance() {
		return instance;
	}

	public String addMessage(final String messageContent) {
		return execute(new AdminExecutionContext() {
			public void execute(Admin admin) throws NotificationException {
				Message message = convert(messageContent, Message.class);
				List<MessageContent> contents = message.getMessages();
				for (MessageContent content : contents) {
					content.setImage(handleImage(content.getImage()));
				}
				getPersistence().addMessage(message);
			}
		});
	}

	public String allMessage(final String managerCode) {
		return execute(new AdminExecutionContext() {
			public void execute(Admin admin) throws NotificationException {
				admin.setMessages(getPersistence().getAllMessages(managerCode));
			}
		});
	}

	public String cleanMessage(final String managerCode, final String messageId) {
		return execute(new AdminExecutionContext() {
			public void execute(Admin admin) throws NotificationException {
				getPersistence().cleanMessageDetails(managerCode, messageId);
			}
		});
	}

	public String cleanUser(final String userCode) {
		return execute(new AdminExecutionContext() {
			public void execute(Admin admin) throws NotificationException {
				getPersistence().cleanUserDetails(userCode);
			}
		});
	}

	private String getImageStorageLocation() {
		return bundle.getString("message.upload.directory");
	}

	private String handleImage(String base64Coded) {
		String fileName = null;
		if (base64Coded != null && !"".equals(base64Coded)) {
			String imageType = base64Coded.substring(0, base64Coded.indexOf(";base64"));
			imageType = imageType.substring(imageType.indexOf("/") + 1);
			String imageContent = base64Coded.substring(base64Coded.indexOf(",") + 1);
			String id = UUID.randomUUID().toString();
			fileName = "messages/" + id + "." + imageType;
			try (FileOutputStream fos = new FileOutputStream(getImageStorageLocation() + fileName);) {
				fos.write(new sun.misc.BASE64Decoder().decodeBuffer(imageContent));
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		return fileName;
	}

	public String login(final String loginContent) {
		return execute(new AdminExecutionContext() {
			public void execute(Admin admin) throws NotificationException {
				Login input = convert(loginContent, Login.class);
				admin.setCurrentUser(getPersistence().login(input.getUser(), input.getPassword()));
				admin.setCurrentProfile(getPersistence().getProfile(input.getUser()));
			}
		});
	}

	public String addUser(final String userCode, final String userName, final String profileId) {
		return execute(new AdminExecutionContext() {
			public void execute(Admin admin) throws NotificationException {
				getPersistence().addUser(
						new User(userCode, userName, "users/", getFormattedDate("1970-01-01 01:01:01")), profileId);
			}
		});
	}

	public String getUsersOnMyLocation(final String managerCode) {
		return execute(new AdminExecutionContext() {
			public void execute(Admin admin) throws NotificationException {
				admin.setUsers(getPersistence().getUsersOnMyLocation(managerCode));
			}
		});
	}

	public String getSupportingDetails() {
		return execute(new AdminExecutionContext() {
			public void execute(Admin admin) throws NotificationException {
				admin.setProfiles(getPersistence().getProfiles());
				admin.setUsers(getPersistence().getUsers());
			}
		});
	}

	private String execute(AdminExecutionContext context) {
		return new ExecutorService().execute(context);
	}

	private PersistenceManager getPersistence() {
		return PersistenceManager.getInstance();
	}

	private <T> T convert(String content, Class<T> type) throws NotificationException {
		return ObjectConverter.convert(content, type);
	}

	private Date getFormattedDate(String dateAsString) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = formatter.parse(dateAsString);
		} catch (ParseException e) {
			e.printStackTrace();
			date = new Date();
		}
		return date;
	}

}
