package com.revature.controllers;

import com.revature.services.UserService;

public class UserController {

	private static UserService userService = new UserService();
	
	public boolean login() {
		return userService.login();
	}
}
