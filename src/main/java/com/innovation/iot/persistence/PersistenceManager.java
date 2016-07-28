package com.innovation.iot.persistence;

import java.util.List;

import com.innovation.iot.common.NotificationException;
import com.innovation.iot.common.PathBuilder;
import com.innovation.iot.common.PathBuilder.Base;
import com.innovation.iot.domain.Challenge;
import com.innovation.iot.domain.CurrentLocation;
import com.innovation.iot.domain.DailyLog;
import com.innovation.iot.domain.Device;
import com.innovation.iot.domain.Event;
import com.innovation.iot.domain.Feedback;
import com.innovation.iot.domain.Message;
import com.innovation.iot.domain.MessageContent;
import com.innovation.iot.domain.Profile;
import com.innovation.iot.domain.User;
import java.sql.Timestamp;

public class PersistenceManager {

	private static final PersistenceManager manager = new PersistenceManager();

	private PersistenceManager() {

	}

	public static PersistenceManager getInstance() {
		return manager;
	}

	public List<Message> getMessages(String deviceId, String userCode) throws NotificationException {
		List<Message> messages = getMessageDao().getMessages(deviceId, userCode);
		for (Message message : messages) {
			message.setCreatedBy(getUser(message.getCreatedBy().getCode()));
			formatMessageImage(message);
		}
		return messages;
	}
	
	public List<Message> getAllMessages(String managerCode) throws NotificationException {
		List<Message> messages = getMessageDao().getAll(managerCode);
		return messages;
	}

	public void markMessageAsRead(List<Message> messages, String userCode) {
		getMessageDao().markMessageAsRead(messages, userCode);
	}

	public void addMessage(com.innovation.iot.representation.consumes.Message message) throws NotificationException {
		getMessageDao().addMessages(message);
	}
	
	public void cleanMessageDetails(String managerCode, String messageId) throws NotificationException {
		getMessageDao().cleanMessageDetails(managerCode, messageId);
	}

	private MessageDao getMessageDao() {
		return new MessageDao();
	}

	public List<Event> getEvents(String deviceId, String userCode) throws NotificationException {
		List<Event> events = getEventDao().getEvents(deviceId, userCode);
		for (Event event : events) {
			event.setCreatedBy(getUser(event.getCreatedBy().getCode()));
		}
		return events;
	}
	
	public List<Event> getAllEvents( String userCode) throws NotificationException {
		List<Event> events = getEventDao().getAll(userCode);
		for (Event event : events) {
			event.setCreatedBy(getUser(event.getCreatedBy().getCode()));
		}
		return events;
	}
	
	private EventDao getEventDao(){
		return new EventDao();
	}
	
	public void addLog( String userCode) throws NotificationException{
		getDailyLogDao().addLog(userCode);
	}
	
	public DailyLog getTodayPresence( String userCode) throws NotificationException{
		DailyLog log = getDailyLogDao().getTodayPresence(userCode);
		if( log != null ){
			log.setUser( getUser(log.getUser().getCode()));
		}
		return log;
	}
	
	private DailyLogDao getDailyLogDao(){
		return new DailyLogDao();
	}

	public List<User> getUsers() throws NotificationException {
		List<User> users = getUserDao().getUsers();
		for (User user : users) {
			formatUserImage(user);
		}
		return users;
	}

	public List<User> getUsersOnMyLocation(String managerCode) throws NotificationException {
		List<User> users = getUserDao().getUsersOnMyLocation(managerCode);
		for (User user : users) {
			formatUserImage(user);
		}
		return users;
	}

	public User getUser(String userCode) throws NotificationException {
		User user = getUserDao().getUser(userCode);
		formatUserImage(user);
		return user;
	}

	public void registerUser(String userCode, String userDeviceId) throws NotificationException {
		getUserDao().register(userCode, userDeviceId);
	}
        
        public void registerUserDetail(String userName,String userCode,String password,String imgURL) throws NotificationException {
		getUserDao().registerUserDetails(userName, userCode, password, imgURL);
	}

	public User login(String code, String password) throws NotificationException {
		User user = getUserDao().getUser(code, password);
		formatUserImage(user);
		return user;
	}
	
	public void addUser(User user, String profile) throws NotificationException{
		getUserDao().addUser(user, profile);
	}
	
	public void cleanUserDetails(String userCode) throws NotificationException {
		getUserDao().cleanUserDetails(userCode);
	}

	private UserDao getUserDao() {
		return new UserDao();
	}

	public Profile getProfile(String userCode) throws NotificationException {
		return getProfileDao().getProfile(userCode);
	}

	public List<Profile> getProfiles() throws NotificationException {
		return getProfileDao().getProfiles();
	}

	private ProfileDao getProfileDao() {
		return new ProfileDao();
	}

	public Device getDevice(String deviceId) throws NotificationException {
		Device device = getDeviceDao().getDevice(deviceId);
		formatDeviceImage(device);
		return device;
	}

	public void checkin(String userCode, String currentDeviceId) throws NotificationException {
		getDeviceDao().checkIn(userCode, currentDeviceId);
	}

	public void checkout(String userCode, String currentDeviceId) throws NotificationException {
		getDeviceDao().checkOut(userCode, currentDeviceId);
	}

	public List<Challenge> getChallenges(String currentDeviceId) throws NotificationException {
		return getDeviceDao().getChallenges(currentDeviceId);
	}
        
        public DailyLog getuserLog(String userId,String date) throws NotificationException {
		return getDailyLogDao().getUserBiometric(userId,date);
	}
        
        public List<CurrentLocation> getCurrentLocation(String userId,String date) throws NotificationException {
		return getDailyLogDao().getCurrentLocation(userId,date);
	}

	private DeviceDao getDeviceDao() {
		return new DeviceDao();
	}
	
	public void feedback( List<Feedback> feedbacks) throws NotificationException{
		getFeedbackDao().feedback(feedbacks);
	}
	
	private FeedbackDao getFeedbackDao(){
		return new FeedbackDao();
	}

	private void formatUserImage(User user) {
		user.setImage(PathBuilder.build(user.getImage() + user.getCode() + ".jpeg", Base.USERPROFILE));
	}
	
	private void formatDeviceImage(Device device){
		device.setLocationMap( PathBuilder.build(device.getLocationMap(), Base.DEVICELOCATION));
	}

	private void formatMessageImage(Message message) {
		List<MessageContent> messages = message.getMessages();
		for (MessageContent content : messages) {
			if (content.getImage() != null) {
				content.setImage(PathBuilder.build(content.getImage(), Base.MESSAGES));
			}
		}
	}
}