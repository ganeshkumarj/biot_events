package com.innovation.iot.common;

public class PathBuilder {

	public static String build(String path, Base base) {
		return base.path() + path;
	}

	public enum Base {
		USERPROFILE {
			public String path() {
				return "/biot/static/images/";
			}
		},
		MESSAGES {
			public String path() {
				return "/biot/static/images/";
			}
		},
		REWARD {
			public String path() {
				return "/biot/static/images/gamification/";
			}
		},
		DEVICELOCATION {
			public String path() {
				return "/biot/static/images/";
			}
		};
		public abstract String path();
	}

}
