package com.innovation.iot.common;

public class StringUtil {

	public static boolean isNull(Object check) {
		return check == null;
	}

	public static boolean isNotNull(Object check) {
		return check != null;
	}

	public static boolean isEmpty(String check) {
		return isNull(check) ? true : "".equals(check);
	}
	
	public static boolean isNotEmpty( String check){
		return !isEmpty(check);
	}

}
