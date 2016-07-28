package com.innovation.iot.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.innovation.iot.common.NotificationException;
import com.innovation.iot.domain.Message;
import com.innovation.iot.domain.MessageContent;

public class MessageDao {

	public List<Message> getMessages(String deviceId, String userCode) throws NotificationException {
		List<Message> messages = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(getQuery());) {
			ps.setString(1, userCode);
			ps.setString(2, userCode);
			ps.setString(3, deviceId);
			// ps.setString(4, userCode);
			try (ResultSet rs = ps.executeQuery();) {
				int messageId = 0;
				List<MessageContent> contents = null;
				while (rs.next()) {
					if (messageId != rs.getInt(1)) {
						contents = new ArrayList<>();
						messages.add(new Message(rs.getInt(1), contents, rs.getString(2), rs.getTimestamp(3)));
						contents.add(new MessageContent(rs.getString(4), rs.getString(5)));
						messageId = rs.getInt(1);
					} else {
						contents.add(new MessageContent(rs.getString(4), rs.getString(5)));
						messageId = rs.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
		return messages;
	}

	public void markMessageAsRead(List<Message> messages, String userCode) {
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(
						"insert into bi_hs_message_user(message_id, user_code, date) values( ?, ?, now() )");) {
			for (Message message : messages) {
				ps.setInt(1, message.getId());
				ps.setString(2, userCode);
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException | NotificationException e) {
			e.printStackTrace();
		}
	}

	public void addMessages(com.innovation.iot.representation.consumes.Message message) throws NotificationException {
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement messagePs = connection.prepareStatement(
						"insert into bi_tr_message(interval_desc, created, start_date, end_date) values( ?, ?, ?, ? )");
				PreparedStatement primaryPs = connection.prepareStatement("select last_insert_id()");
				PreparedStatement contentPs = connection.prepareStatement(
						"insert into bi_tr_message_content( message_id, content, image_url) values ( ?, ?, ?)");
				PreparedStatement profilePs = connection
						.prepareStatement("insert into bi_lk_message_profile(message_id, profile_id) values ( ?, ?)");
				PreparedStatement userPs = connection
						.prepareStatement("insert into bi_lk_message_user(message_id, user_code) values (?, ?)");) {
			connection.setAutoCommit(false);
			messagePs.setString(1, message.getInterval());
			messagePs.setString(2, message.getCreated());
			messagePs.setTimestamp(3, getFormattedDate(message.getStart()));
			messagePs.setTimestamp(4, getFormattedDate(message.getEnd()));
			messagePs.executeUpdate();
			try (ResultSet result = primaryPs.executeQuery();) {
				if (result.next()) {
					String messageId = result.getString(1);

					for (MessageContent content : message.getMessages()) {
						contentPs.setString(1, messageId);
						contentPs.setString(2, content.getContent());
						contentPs.setString(3, content.getImage());
						contentPs.executeUpdate();
						contentPs.clearParameters();
					}

					if (!message.getProfiles().isEmpty()) {
						for (int profile : message.getProfiles()) {
							profilePs.setString(1, messageId);
							profilePs.setInt(2, profile);
							profilePs.executeUpdate();
							profilePs.clearParameters();
						}
					}

					if (!message.getUsers().isEmpty()) {
						for (String user : message.getUsers()) {
							userPs.setString(1, messageId);
							userPs.setString(2, user);
							userPs.executeUpdate();
							userPs.clearParameters();
						}
					}
				}
			}
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
	}

	private java.sql.Timestamp getFormattedDate(String dateAsString) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = formatter.parse(dateAsString);
		} catch (ParseException e) {
			e.printStackTrace();
			date = new Date();
		}
		return new java.sql.Timestamp(date.getTime());
	}

	private String getQuery() {
		StringBuilder query = new StringBuilder();
		query.append(" select q.id, q.created, q.startdate, q.content, q.msgImage ");
		query.append(" from ");
		query.append(
				"(select msg.id id,msg.created created,msg.start_date startdate,msgcont.content content,msgcont.image_url msgImage ");
		query.append(" from ");
		query.append(
				" bi_tr_message msg, bi_lk_message_profile msg_prf, bi_lk_user_profile usr_prf, bi_tr_message_content msgcont ");
		query.append(" where msg_prf.message_id = msg.id ");
		query.append(" and usr_prf.profile_id = msg_prf.profile_id ");
		query.append(" and msg.id = msgcont.message_id ");
		query.append(" and (now() between msg.start_date and msg.end_date) ");
		query.append(" and usr_prf.user_code = ? union select  ");
		query.append(" msg.id id, ");
		query.append(" msg.created created, ");
		query.append(" msg.start_date startdate, ");
		query.append(" msgcont.content content, ");
		query.append(" msgcont.image_url msgImage ");
		query.append(" from ");
		query.append(" bi_tr_message msg, bi_lk_message_user msg_usr, bi_tr_message_content msgcont ");
		query.append(" where ");
		query.append(" msg_usr.message_id = msg.id ");
		query.append(" and msg.id = msgcont.message_id ");
		query.append(" and (now() between msg.start_date and msg.end_date) ");
		query.append(" and msg_usr.user_code = ?) q ");
		query.append(" where q.created in ( select owner from bi_ma_device where id = ? )  ");
		// query.append(" and (q.id, date(now()) ) not in (select message_id,
		// date(max(date)) from bi_hs_message_user where user_code = ? group by
		// message_id)");
		query.append(" order by q.id asc; ");
		return query.toString();
	}

	public List<Message> getAll(String managerCode) throws NotificationException {
		List<Message> messages = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(
						"select msg.id, msg.start_date, msg.end_date, msg_con.content, msg_con.image_url, msg.created from bi_tr_message msg, bi_tr_message_content msg_con where msg.id = msg_con.message_id and msg.created = ?");) {
			ps.setString(1, managerCode);
			try (ResultSet rs = ps.executeQuery();) {
				int messageId = 0;
				List<MessageContent> contents = null;
				while (rs.next()) {
					if (messageId != rs.getInt(1)) {
						contents = new ArrayList<>();
						messages.add(new Message(rs.getInt(1), contents, rs.getString(6), rs.getTimestamp(2), rs.getTimestamp(3)));
						contents.add(new MessageContent(rs.getString(4), rs.getString(5)));
						messageId = rs.getInt(1);
					} else {
						contents.add(new MessageContent(rs.getString(4), rs.getString(5)));
						messageId = rs.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
		return messages;
	}

	public void cleanMessageDetails(String managerCode, String messageId) throws NotificationException {
		try (Connection connection = ConnectionManager.getConnection();
				CallableStatement ps = connection.prepareCall("call deleteMessages(?, ?)");) {
			ps.setString(1, managerCode);
			ps.setString(2, messageId);
			ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
	}
}
