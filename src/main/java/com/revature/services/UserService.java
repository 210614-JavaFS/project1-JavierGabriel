package com.revature.services;

import com.revature.models.User;
import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;

public class UserService {

	
	private static UserDAO userDao = new UserDAOImpl();
	
	public boolean login(String username, String password) {
		return userDao.login(username, password);
	}
	
	public User getUser(String username) {
		return userDao.getOne(username);
	}
}
