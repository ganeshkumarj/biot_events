package com.innovation.iot.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.innovation.iot.common.NotificationException;
import com.innovation.iot.domain.Challenge;
import com.innovation.iot.domain.Device;

public class DeviceDao {

	public Device getDevice(String code) throws NotificationException {
		Device device = null;
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection
						.prepareStatement("select id, locationmap, location, owner from bi_ma_device where id = ?");) {
			ps.setString(1, code);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					device = new Device(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
		return device;
	}

	public void checkOut(String userCode, String currentDeviceId) throws NotificationException {
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(
						"update bi_tr_user_current_location set checkout = now() where user_code = ? and device_id = ? ");) {
			ps.setString(1, userCode);
			ps.setString(2, currentDeviceId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
	}

	public void checkIn(String userCode, String currentDeviceId) throws NotificationException {
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement presenceSt = connection.prepareStatement(
						"select user_code from bi_tr_user_current_location where user_code = ? and device_id = ?");
				PreparedStatement updateSt = connection.prepareStatement(
						"update bi_tr_user_current_location set checkout = null, checkin = now() where user_code = ? and device_id = ? ");
				PreparedStatement insertSt = connection.prepareStatement(
						"insert into bi_tr_user_current_location( user_code, device_id, checkin ) values( ?, ?, now())");
				PreparedStatement updateIncasePreviousCheckoutWasNullSt = connection.prepareStatement(
						"update bi_tr_user_current_location set checkout = now() where user_code = ? and checkout is null ");) {

			presenceSt.setString(1, userCode);
			presenceSt.setString(2, currentDeviceId);
			try (ResultSet presenceResult = presenceSt.executeQuery()) {
				boolean isExist = false;
				while (presenceResult.next()) {
					isExist = presenceResult.getString(1) != null;
				}
				
				updateIncasePreviousCheckoutWasNullSt.setString(1, userCode);
				updateIncasePreviousCheckoutWasNullSt.executeUpdate();
				
				if (isExist) {
					System.out.println( userCode + " is checking in" + currentDeviceId );
					updateSt.setString(1, userCode);
					updateSt.setString(2, currentDeviceId);
					updateSt.executeUpdate();
				} else {
					System.out.println( "New " + userCode + " is checking in" + currentDeviceId );
					insertSt.setString(1, userCode);
					insertSt.setString(2, currentDeviceId);
					insertSt.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
	}
        
	public List<Challenge> getChallenges(String currentDeviceId) throws NotificationException {
		List<Challenge> challenges = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(
						"select challenge_code, start_time, end_time from bi_ma_user_location_challenge where device_id = ?");) {
			ps.setString(1, currentDeviceId);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					challenges.add(
							new Challenge(currentDeviceId, rs.getString(1), rs.getTimestamp(2), rs.getTimestamp(3)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
		return challenges;
	}

}
