package com.innovation.iot.core;

import com.innovation.iot.common.ImageUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.innovation.iot.common.NotificationException;
import com.innovation.iot.common.ObjectConverter;
import com.innovation.iot.common.StatusType;
import com.innovation.iot.common.StringUtil;
import com.innovation.iot.core.ExecutorService.NotificationExecutionContext;
import com.innovation.iot.core.challenge.ChallengeSequncer;
import com.innovation.iot.core.challenge.DefaultChallengeSequencer;
import com.innovation.iot.core.challenge.DetailedFeedbackSequencer;
import com.innovation.iot.core.challenge.FeedbackSequencer;
import com.innovation.iot.core.challenge.LoginChallengeSequencer;
import com.innovation.iot.core.challenge.SequenceHandler;
import com.innovation.iot.domain.Challenge;
import com.innovation.iot.domain.User;
import com.innovation.iot.persistence.PersistenceManager;
import com.innovation.iot.representation.consumes.Feedback;
import com.innovation.iot.representation.consumes.Register;
import com.innovation.iot.representation.produces.Notification;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationManager {

	private static final NotificationManager instance = new NotificationManager();
	private static final ResourceBundle bundle = ResourceBundle.getBundle("Challenge");
	
	private NotificationManager() {
	}

	public static NotificationManager getInstance() {
		return instance;
	}
	
	public String getNotifications(final String deviceId, final String userCode) {
		String notifications = null;
		final Notification dummyWorkAroundNotification = new Notification();
		try {
			notifications = execute(new NotificationExecutionContext() {
				public void execute(Notification notification) throws NotificationException {
					notification.setAnnouncements(getPersistence().getMessages(deviceId, userCode));
					notification.setUser(getPersistence().getUser(userCode));
					notification.setDevice(getPersistence().getDevice(deviceId));
					if (notification.getAnnouncements().isEmpty()) {
						notification.setStatus(StatusType.Error.get("BINO-0003"));
					}
					dummyWorkAroundNotification.setAnnouncements(notification.getAnnouncements());
					// checkin(userCode, deviceId);
					notifyUserActionCompletion(userCode, deviceId, new SequenceContext() {
						public ChallengeSequncer get() {
							return new DefaultChallengeSequencer();
						}
					});
					notification.setRewards(getGamification().points(userCode));
				}
			});
		} finally {
			if (StringUtil.isNotNull(dummyWorkAroundNotification.getAnnouncements())
					&& !dummyWorkAroundNotification.getAnnouncements().isEmpty()) {
				getPersistence().markMessageAsRead(dummyWorkAroundNotification.getAnnouncements(), userCode);
			}
		}
		return notifications;
	}

	private interface SequenceContext {
		ChallengeSequncer get();
	}
	
	private void notifyUserActionCompletion(String userCode, String currentDeviceId, final SequenceContext context)
			throws NotificationException {
		List<Challenge> challenges = getPersistence().getChallenges(currentDeviceId);
		List<String> active = activeChallenges(challenges, context);
		getGamification().awardUser(userCode, active);
	}
        
	public String award(final String userCode, final String currentDeviceId, final SequenceContext context) {
		return execute(new NotificationExecutionContext() {
			public void execute(Notification notification) throws NotificationException {
				notifyUserActionCompletion(userCode, currentDeviceId, new SequenceContext() {
					public ChallengeSequncer get() {
						return new DefaultChallengeSequencer();
					}
				});
			}
		});
	}
        
 
	private List<String> activeChallenges(List<Challenge> challenges, SequenceContext context) {
		List<String> active = new ArrayList<String>();
		for (Challenge challenge : challenges) {
			SequenceHandler handler = new SequenceHandler(context.get());
			if (handler.doProcess(challenge)) {
				active.add(challenge.getChallengeCode());
			}
		}
		return active;
	}

	public String registerUser(final String registerContent) {
		return execute(new NotificationExecutionContext() {
			public void execute(Notification notification) throws NotificationException {
				Register register = convert(registerContent, Register.class);
                                String imgUrl = ImageUtil.getInstance().handleImage(register.getImgByte64Code(),register.getUserCode());
                                 getPersistence().addUser(
						new User(register.getUserCode(), register.getUserName(), "users/", getFormattedDate("1970-01-01 01:01:01")), "1");
				getPersistence().registerUser(register.getUserCode(), register.getUserDeviceId());
				User user = getPersistence().getUser(register.getUserCode());
				getGamification().registerUser(register.getUserCode(), user.getName());
				notifyUserActionCompletion(register.getUserCode(), register.getDeviceId(), new SequenceContext() {
					public ChallengeSequncer get() {
						return new LoginChallengeSequencer();
					}
				});
				notification.setUser(user);
			}
		});
	}

	public String checkout(final String userCode, final String currentDeviceId) {
		return execute(new NotificationExecutionContext() {
			public void execute(Notification notification) throws NotificationException {
				getPersistence().checkout(userCode, currentDeviceId);
				getPersistence().addLog(userCode);
			}
		});
	}

	public String checkin(final String userCode, final String currentDeviceId) {
		return execute(new NotificationExecutionContext() {
			public void execute(Notification notification) throws NotificationException {
				getPersistence().checkin(userCode, currentDeviceId);
				getPersistence().addLog(userCode);
			}
		});
	}

	public String feedback(final String userCode, final String deviceId, final String feedbackContent) {
		return execute(new NotificationExecutionContext() {
			public void execute(Notification notification) throws NotificationException {
				if (StringUtil.isNotEmpty(feedbackContent)) {
					Feedback feedback = convert(feedbackContent, Feedback.class);
					if (StringUtil.isNotNull(feedback.getFeedbacks())) {
						for (com.innovation.iot.domain.Feedback each : feedback.getFeedbacks()) {
							each.setUser(userCode);
							each.setDeviceId(deviceId);
						}
						getPersistence().feedback(feedback.getFeedbacks());
						notifyUserActionCompletion(userCode, deviceId, new SequenceContext() {
							public ChallengeSequncer get() {
								return new FeedbackSequencer();
							}
						});
						if (feedback.getFeedbacks().size() > 4) {
							com.innovation.iot.domain.Feedback each = feedback.getFeedbacks().get(4);
							if (StringUtil.isNotEmpty(each.getAnswer())
									&& each.getAnswer().length() >= getMinimumCharactersTypedToGetAward()) {
								notifyUserActionCompletion(userCode, deviceId, new SequenceContext() {
									public ChallengeSequncer get() {
										return new DetailedFeedbackSequencer();
									}
								});
							}
						}
					}
				}
			}
		});
	}

	private int getMinimumCharactersTypedToGetAward() {
		return new Integer(bundle.getString("detailed.feedback.characters")).intValue();
	}

	private String execute(NotificationExecutionContext context) {
		return new ExecutorService().execute(context);
	}

	private PersistenceManager getPersistence() {
		return PersistenceManager.getInstance();
	}

	private GamificationManager getGamification() {
		return GamificationManager.getInstance();
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