package com.innovation.iot.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.innovation.iot.common.NotificationException;
import com.innovation.iot.domain.Event;

public class EventDao {
	
	public List<Event> getEvents(String deviceId, String userCode) throws NotificationException {
		List<Event> events = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(getQuery());) {
			ps.setString(1, userCode);
			ps.setString(2, userCode);
			ps.setString(3, deviceId);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					Event event = new Event(rs.getInt(1), rs.getString(2), rs.getTimestamp(3),  rs.getTimestamp(4), rs.getString(5), rs.getString(6), rs.getString(7));
					events.add(event);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
		return events;
	}

	public List<Event> getAll(String userCode) throws NotificationException {
		List<Event> events = new ArrayList<>();
		boolean isUserCodeExist = userCode != null && !"".equals(userCode);
		String all = "select eve.id, eve.created , eve.start_date, eve.end_date, eve.location, eve.description, eve.title from bi_tr_event eve where (DATE_FORMAT(now(), '%m-%d-%Y') between DATE_FORMAT(eve.start_date , '%m-%d-%Y') and DATE_FORMAT(eve.end_date, '%m-%d-%Y')) and (DATE_FORMAT(now(), '%m-%d-%Y:%H:%i') < DATE_FORMAT(eve.start_date, '%m-%d-%Y:%H:%i') or DATE_FORMAT(now(), '%m-%d-%Y:%H:%i') < DATE_FORMAT(eve.end_date, '%m-%d-%Y:%H:%i'))";
		String userBased = "select eve.id id,eve.created ,eve.start_date ,eve.end_date, eve.location, eve.description, eve.title from bi_tr_event eve, bi_lk_event_user eve_usr where eve_usr.event_id = eve.id and (DATE_FORMAT(now(), '%m-%d-%Y') between DATE_FORMAT(eve.start_date , '%m-%d-%Y') and DATE_FORMAT(eve.end_date, '%m-%d-%Y'))  and (DATE_FORMAT(now(), '%m-%d-%Y:%H:%i') < DATE_FORMAT(eve.start_date, '%m-%d-%Y:%H:%i') or DATE_FORMAT(now(), '%m-%d-%Y:%H:%i') < DATE_FORMAT(eve.end_date, '%m-%d-%Y:%H:%i')) and UPPER(eve_usr.user_code) = ?";
		String query = isUserCodeExist ? userBased : all;
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement( query );){
				if(isUserCodeExist){
					ps.setString(1, userCode.toUpperCase());
				}
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					Event event = new Event(rs.getInt(1), rs.getString(2), rs.getTimestamp(3),  rs.getTimestamp(4), rs.getString(5), rs.getString(6), rs.getString(7));
					events.add(event);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
		return events;
	}
	
	private String getQuery() {
		StringBuilder query = new StringBuilder();
		query.append(" select q.id, q.created, q.startdate, q.enddate,q.location, q.description,  q.title");  
		query.append(" from (select eve.id id,eve.created created,eve.start_date startdate,eve.end_date enddate, eve.location, eve.title, eve.description ");
		query.append(" from bi_tr_event eve, bi_lk_event_profile eve_prf, bi_lk_user_profile usr_prf ");
		query.append(" where eve_prf.event_id = eve.id ");
		query.append(" and usr_prf.profile_id = eve_prf.profile_id ");
		query.append(" and (now() between eve.start_date and eve.end_date) ");
		query.append(" and usr_prf.user_code = ? ");
		query.append(" union   ");
		query.append(" select eve.id id,eve.created created,eve.start_date startdate,eve.end_date enddate, eve.location, eve.title, eve.description  ");
		query.append(" from bi_tr_event eve, bi_lk_event_user eve_usr");
		query.append(" where eve_usr.event_id = eve.id  ");
		query.append(" and (now() between eve.start_date and eve.end_date) ");
		query.append(" and eve_usr.user_code = ?) q ");
		query.append(" where q.created in ( select owner from bi_ma_device where id = ? )  ");
		query.append(" order by q.id asc; ");
		return query.toString();
	}
}
