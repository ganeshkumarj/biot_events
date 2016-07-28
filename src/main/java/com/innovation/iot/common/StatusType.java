package com.innovation.iot.common;

import java.util.ResourceBundle;

import com.innovation.iot.representation.Status;

public enum StatusType {
	Error() {
		@Override
		public Status get() {
			return get("BIKO-0001");
		}
	},
	Success() {
		@Override
		public Status get() {
			return get("BIOK-0001");
		}
	};
	ResourceBundle resourceBundle = ResourceBundle.getBundle("ErrorMessage");

	public abstract Status get();

	public Status get(String code) {
		return new Status(code.substring(0, code.indexOf("-")), resourceBundle.getString(code));
	}

}
