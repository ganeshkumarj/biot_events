package com.innovation.iot.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.innovation.iot.common.NotificationException;
import com.innovation.iot.domain.CurrentLocation;
import com.innovation.iot.domain.DailyLog;
import java.util.ArrayList;
import java.util.List;

public class DailyLogDao {

	 public void addLog(String userCode) throws NotificationException {
		userCode = userCode.toUpperCase();
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement existCheckStatement = connection.prepareStatement(
						"select user_code from bi_tr_daily_log where DATE_FORMAT(now(), '%m-%d-%Y') = DATE_FORMAT(first_in, '%m-%d-%Y') and UPPER(user_code) = ?");
				PreparedStatement firstInsertStatement = connection
						.prepareStatement("insert into bi_tr_daily_log( user_code, first_in) values( ?, now() )");
				PreparedStatement lastUpdateStatement = connection
						.prepareStatement("update bi_tr_daily_log set last_out = now() where UPPER(user_code) = ?");){
			existCheckStatement.setString(1, userCode);
			try (ResultSet rs = existCheckStatement.executeQuery();) {
				if (rs.next()) {
					lastUpdateStatement.setString(1, userCode);
					lastUpdateStatement.execute();
				} else {
					firstInsertStatement.setString(1, userCode);
					firstInsertStatement.execute();
				}
			}
		} catch (SQLException | NotificationException e) {
			e.printStackTrace();
		}
	}

	public DailyLog getTodayPresence(String userCode) throws NotificationException {
		DailyLog log = null;
		userCode = userCode.toUpperCase();
		try (Connection connection = ConnectionManager.getConnection();
			PreparedStatement currentStatement = connection.prepareStatement(
					"select id, user_code, first_in, last_out from bi_tr_daily_log where DATE_FORMAT(now(), '%m-%d-%Y') = DATE_FORMAT(first_in, '%m-%d-%Y') and UPPER(user_code) = ?");){
			currentStatement.setString(1, userCode);  
			try (ResultSet currentRs = currentStatement.executeQuery();) {
				if( currentRs.next() ){
					log = new DailyLog(currentRs.getInt(1), currentRs.getString(2), currentRs.getTimestamp(3), currentRs.getTimestamp(4));
				}
			} 
		} catch (SQLException | NotificationException e) {
			e.printStackTrace();
		}
		return log;
	}
        
         public DailyLog getUserBiometric(String userCode,String date)throws NotificationException{
            DailyLog dailyLog =null;
            try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(
						"select id,user_code,first_in,last_out from bi_tr_daily_log log where user_code=? and  DATE_FORMAT(log.first_in, '%m-%d-%Y') = ? and  DATE_FORMAT(log.last_out, '%m-%d-%Y') = ?");) {
			ps.setString(1, userCode);
                        ps.setString(2, date);
                        ps.setString(3, date);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
                                   dailyLog = new DailyLog(rs.getInt(1),rs.getString(2), rs.getTimestamp(3), rs.getTimestamp(4));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
                        throw new NotificationException("BIKO-0001", e);
		}
		return dailyLog;
	}
         
          public List<CurrentLocation> getCurrentLocation(String userCode,String date)throws NotificationException{
            List<CurrentLocation> currentLocations =new ArrayList<>();
            try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(
						"select device_id,user_code,checkin,checkout from bi_tr_user_current_location where user_code=? and  DATE_FORMAT(checkin, '%m-%d-%Y') = ?");) {
			ps.setString(1, userCode);
                        ps.setString(2, date);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
                                   currentLocations.add(new CurrentLocation(rs.getInt(1),rs.getString(2), rs.getTimestamp(3), rs.getTimestamp(4)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
                        throw new NotificationException("BIKO-0001", e);
		}
		return currentLocations;
	}


}
 

