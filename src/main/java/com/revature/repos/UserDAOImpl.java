package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class UserDAOImpl implements UserDAO {

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getOne(String username) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT u.user_id, u.username, u.first_name, u.last_name, u.user_email, r.role_type AS user_role "
					+ "FROM users u "
					+ "INNER JOIN roles r "
					+ "ON u.user_role = r.role_id "
					+ "WHERE u.username = ? ;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, username);
			
			ResultSet result = statement.executeQuery();
			User user = new User();
			if(result.next()) {
				user.setUser_id(result.getInt("user_id"));
				user.setUsername(result.getString("username"));
				user.setFirst_name(result.getString("first_name"));
				user.setLast_name(result.getString("last_name"));
				user.setUser_email(result.getString("user_email"));
				user.setUser_role(result.getString("user_role"));
			}
			
			return user;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean login(String username, String password) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT user_password FROM users WHERE username = ?";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, username);
			
			ResultSet result = statement.executeQuery();
			
			String pass = "";
			if(result.next()) {
				pass = result.getString("user_password");
			}
			
			if(!pass.isEmpty()) {
				if(BCrypt.checkpw(password, pass)) {
					System.out.println("Password Matches");
				}else {
					System.out.println("Password DO NOT Matches");
				}
				
				return BCrypt.checkpw(password, pass);
			}
			
			System.out.println("No such username.");
			
			return false;
				
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int getUserId(String username) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT user_id FROM users WHERE username = ?";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, username);
			
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return result.getInt("user_id");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
