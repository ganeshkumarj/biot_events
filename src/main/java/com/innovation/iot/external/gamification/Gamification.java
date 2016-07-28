package com.innovation.iot.external.gamification;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.innovation.iot.external.http.HttpHandler;
import com.innovation.iot.external.http.HttpHandler.ExecutionContext;
import com.innovation.iot.external.http.HttpHandler.HttpAction;

public class Gamification {

	private static final Gamification instance = new Gamification();
	private static final Logger LOG = Logger.getLogger(Gamification.class);

	private Gamification() {
	}

	public static Gamification getInstance() {
		return instance;
	}

	private static final ResourceBundle bundle = ResourceBundle.getBundle("External");

	public String awardUser(final String userCode, final String challenge) {
		LOG.debug("<awardUser> userCode : "+userCode+",challenge : "+challenge);
		return invoke(new ExecutionContext() {
			public String url() {
				return fqdn(getUrl("gamification.action.completion"));
			}

			public List<NameValuePair> parameters() {
				List<NameValuePair> pair = new ArrayList<>();
				pair.add(new BasicNameValuePair("userCode", userCode));
				pair.add(new BasicNameValuePair("actionCode", challenge));
				return pair;
			}

			public HttpAction action() {
				return HttpAction.GET;
			}
		});
	}

	public String badges(final String userCode) {
		return invoke(new ExecutionContext() {
			public String url() {
				return fqdn(getUrl("gamification.action.receiveBadge"));
			}

			public List<NameValuePair> parameters() {
				List<NameValuePair> pair = new ArrayList<>();
				pair.add(new BasicNameValuePair("userCode", userCode));
				pair.add(new BasicNameValuePair("goalCode", "IOT_GOAL"));
				return pair;
			}

			public HttpAction action() {
				return HttpAction.GET;
			}
		});
	}

	public String register(final String userCode, final String userName) {
		return invoke(new ExecutionContext() {
			public String url() {
				return fqdn(getUrl("gamification.registration"));
			}

			public List<NameValuePair> parameters() {
				List<NameValuePair> pair = new ArrayList<>();
				pair.add(new BasicNameValuePair("userCode", userCode));
				pair.add(new BasicNameValuePair("userType", "EMPLOYEE"));
				pair.add(new BasicNameValuePair("name", userName));
				return pair;
			}

			public HttpAction action() {
				return HttpAction.GET;
			}
		});
	}

	private String getUrl(String key) {
		return bundle.getString(key);
	}

	private String invoke(ExecutionContext context) {
		if (true) {
			return new HttpHandler().invoke(context);
		}
		return null;
	}

	private String fqdn(String base) {
		return gamificationServerPath() + base;
	}

	private boolean isReachable() {
		boolean reachable = false;
		try {
			InetAddress inetAddress = Inet4Address.getByName(gamificationServerPath());
			reachable = inetAddress.isReachable(timeout());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reachable;
	}

	private String gamificationServerPath() {
		return "http://" + ip() + ":" + port();
	}

	private int timeout() {
		return new Integer(bundle.getString("gamification.server.reachable.timeout")).intValue();
	}

	private String ip() {
		return bundle.getString("gamification.server.ip");
	}

	private String port() {
		return bundle.getString("gamification.server.port");
	}

}
