package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.services.UserService;

public class UserController {

	private static UserService userService = new UserService();
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public void login(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		//TODO: Get data from request to send to the login service username and password
		String data = "";
		StringBuilder stringBuilder = new StringBuilder();
		
		BufferedReader reader = request.getReader();
		
		String line = reader.readLine();
		
		while(line != null) {
			stringBuilder.append(line);
			line = reader.readLine();
		}
		
		String body = new String(stringBuilder);
		
		User user = objectMapper.readValue(body, User.class);
		
		System.out.println("Username: " + user.getUsername() + " Password: " + user.getUser_password());
		
		if( userService.login(user.getUsername(),user.getUser_password()) ) {
			HttpSession session = request.getSession();
			session.setAttribute("username", user.getUsername());
			User loggedUser = userService.getUser(user.getUsername());
			String json = objectMapper.writeValueAsString(loggedUser);
			System.out.println(json);
			response.getWriter().print(json);
			response.setStatus(200);
		}else {
			response.setStatus(401);
		}
	}
	
}
