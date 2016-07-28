package com.innovation.iot.common;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectConverter {
	public static <T> T convert(String content, Class<T> type) throws NotificationException {
		ObjectMapper mapper = new ObjectMapper();
		T modified = null;
		try {
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			if (content != null) {
				modified = mapper.readValue(content, type);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001");
		}
		return modified;
	}
}
