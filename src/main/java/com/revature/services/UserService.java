package com.revature.services;

import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;

public class UserService {

	
	private static UserDAO userDao = new UserDAOImpl();
	
	public boolean login() {
		String user = "javier";
		String pass = "pass";
		
		return userDao.login(user, pass);
	}
}
