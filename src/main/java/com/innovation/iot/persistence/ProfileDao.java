package com.innovation.iot.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.innovation.iot.common.NotificationException;
import com.innovation.iot.domain.Profile;

public class ProfileDao {

	public List<Profile> getProfiles() throws NotificationException {
		List<Profile> profiles = new ArrayList<Profile>();
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement("select id, name, owner from bi_ma_profile");) {
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					profiles.add(new Profile(rs.getInt(1), rs.getString(2), rs.getString(3)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
		return profiles;
	}

	public Profile getProfile(String userCode) throws NotificationException {
		Profile profile = null;
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(
						"select prf.id, prf.name, prf.owner from bi_ma_profile prf, bi_lk_user_profile usr_prf where prf.id = usr_prf.profile_id and usr_prf.user_code = ?");) {
			ps.setString(1, userCode);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					profile = new Profile(rs.getInt(1), rs.getString(2), rs.getString(3));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
		return profile;
	}

}
