package com.innovation.iot.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.innovation.iot.common.NotificationException;

public class ConnectionManager {
	
	private static final ResourceBundle bundle = ResourceBundle.getBundle("Admin");

	public static Connection getConnection() throws NotificationException, SQLException {
		try {
			Class.forName(bundle.getString("db.connection.driver"));
		} catch (ClassNotFoundException e) {
			throw new NotificationException("BIKO-0001", e);
		}
		return DriverManager.getConnection(bundle.getString("db.connection.url"));
	}
	
}
