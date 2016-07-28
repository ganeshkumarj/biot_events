package com.innovation.iot.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.innovation.iot.common.NotificationException;
import com.innovation.iot.domain.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class UserDao {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("Admin");

	public User getUser(String code) throws NotificationException {
		User user = null;
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(
						"select code, name, image_url, date_of_birth from bi_ma_user where code = ?");) {
			ps.setString(1, code);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4));
				}
			}
		} catch (SQLException e) {
			throw new NotificationException("BIKO-0001", e);
		}
		return user;
	}

	public User getUser(String code, String password) throws NotificationException {
		User user = null;
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(
						"select code, name, image_url, date_of_birth from bi_ma_user where code = ? and password = ?");) {
			ps.setString(1, code);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4));
				}
			}
			if (user == null) {
				throw new NotificationException("BINO-0002");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
		return user;
	}

	public List<User> getUsers() throws NotificationException {
		List<User> users = new ArrayList<User>();
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection
						.prepareStatement("select code, name, image_url, date_of_birth from bi_ma_user where internal = 'N'");) {
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
		return users;
	}

	public List<User> getUsersOnMyLocation(String managerCode) throws NotificationException {
		List<User> users = new ArrayList<User>();
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(getQuery());) {
			ps.setString(1, managerCode);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
		return users;
	}

	private String getQuery() {
		StringBuilder query = new StringBuilder();
		query.append(" select usr.code, usr.name, usr.image_url, usr.date_of_birth ");
		query.append(
				" from bi_ma_user usr, bi_tr_user_current_location usr_loc, (select dev.id id from bi_ma_device dev where owner = ?) q");
		query.append(" where usr.code = usr_loc.user_code ");
		query.append(" and q.id = usr_loc.device_id ");
		query.append(
				" and (DATE_FORMAT(now(),'%i') between DATE_FORMAT(usr_loc.checkin,'%i') and DATE_FORMAT(usr_loc.checkin,'%i')+")
				.append(timeout()).append(")");
		query.append(" and usr_loc.checkout is null ");
		return query.toString();
	}

	private int timeout() {
		return new Integer(bundle.getString("user.inactive.time.for.current.location")).intValue();
	}

	public void addUser(User user, String profile) throws NotificationException {
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement userPs = connection.prepareStatement(
						"insert into bi_ma_user (code, name, image_url, date_of_birth, password) values ( ?, ?, ?, ?, ?)");
				PreparedStatement profileLinkPs = connection
						.prepareStatement("insert into bi_lk_user_profile( user_code, profile_id) values ( ?, ?)");) {
			connection.setAutoCommit(false);
			userPs.setString(1, user.getCode());
			userPs.setString(2, user.getName());
			userPs.setString(3, user.getImage());
			userPs.setTimestamp(4, new Timestamp(user.getDob().getTime()));
			userPs.setString(5, user.getCode().toLowerCase());
			userPs.executeUpdate();

			profileLinkPs.setString(1, user.getCode());
			profileLinkPs.setString(2, profile);
			profileLinkPs.executeUpdate();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
	}

	public void cleanUserDetails(String userCode) throws NotificationException {
		try (Connection connection = ConnectionManager.getConnection();
				CallableStatement ps = connection.prepareCall("call removeUserDetails(?)");) {
			ps.setString(1, userCode);
			ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
	}
	
	public void saveOrNothing( ) throws NotificationException{
		
	}

	public void register(String userCode, String userDeviceId) throws NotificationException {
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement existPs = connection.prepareStatement(
						"select user_code from bi_tr_user_device where user_code = ?  and user_device_id = ?");
				PreparedStatement ps = connection.prepareStatement(
						"insert into bi_tr_user_device( user_code, user_device_id, active) values( ?, ?, true)");) {
			existPs.setString(1, userCode);
			existPs.setString(2, userDeviceId);
			try (ResultSet result = existPs.executeQuery()) {
				boolean isExist = false;
				while (result.next()) {
					isExist = result.getString(1) != null;
				}
				if (!isExist) {
					ps.setString(1, userCode);
					ps.setString(2, userDeviceId);
					ps.execute();
				}
			}
		} catch (MySQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			throw new NotificationException("BINO-0001", e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
	}
        
        public void registerUserDetails(String userName,String userCode,String password,String imgURL)throws NotificationException{
            try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement registerUserPs = connection.prepareStatement(
						"insert into bi_ma_user(code,name,image_url,password) values(?,?,?,?) ");){
                        registerUserPs.setString(1, userCode);
			registerUserPs.setString(2, userName);
                        registerUserPs.setString(3, imgURL);
                        registerUserPs.setString(4, password);
                        registerUserPs.execute();
                        
            } catch (MySQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			throw new NotificationException("BINO-0001", e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotificationException("BIKO-0001", e);
		}
        }
}
