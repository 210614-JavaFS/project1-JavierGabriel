package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

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
		// TODO Auto-generated method stub
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
				
				if(BCrypt.checkpw(password, pass)) {
					System.out.println("Password Matches");
				}else {
					System.out.println("Password DO NOT Matches");
				}
			return BCrypt.checkpw(password, pass);
			
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
