package com.innovation.iot.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.innovation.iot.common.NotificationException;
import com.innovation.iot.domain.Feedback;

public class FeedbackDao {
	public void feedback(List<Feedback> feedbacks) throws NotificationException {
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(
						"insert into bi_tr_user_feedback (user_code, device_id, question, answer,insertion_date ) values(?, ?, ?, ?, ?)");) {
			for (Feedback feedback : feedbacks) {
				ps.setString(1, feedback.getUser());
				ps.setString(2, feedback.getDeviceId());
				ps.setString(3, feedback.getQuestion());
				ps.setString(4, feedback.getAnswer());
				ps.setTimestamp(5, new Timestamp(new Date().getTime()));
				ps.execute();
				ps.clearParameters();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
	}
}
