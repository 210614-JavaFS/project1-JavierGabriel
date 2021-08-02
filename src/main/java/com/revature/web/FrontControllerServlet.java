package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.controllers.ReimbursementController;
import com.revature.controllers.UserController;

public class FrontControllerServlet extends HttpServlet{

	private ReimbursementController reimbController = new ReimbursementController();
	private UserController userController = new UserController();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("application/json");
		
		response.setStatus(404);
		
		final String URL = request.getRequestURI().replace("/project1/", "");

		System.out.println(URL);
		
		String[] UrlSections = URL.split("/");
		
		
		switch(UrlSections[0]) {
			case "reimbursement":
				response.setStatus(401);
				HttpSession session = request.getSession(false);
				if(session == null) {
					return;
				}
				if(UrlSections.length == 1) {
					if(request.getMethod().equals("GET")) {
						reimbController.getAllReimbursements(response);
					}
					else if(request.getMethod().equals("POST")) {
					//call reimbursement controller to create new reimbursement
						reimbController.addReimbursment(request, response);
					}
				}
				//user sends a path parameter
				else if(UrlSections.length == 2) {
					if(request.getMethod().equals("GET")) {
						//Get reimbursement of id UrlSections[1]
						try {
							int parameter = Integer.parseInt(UrlSections[1]);
							reimbController.getOneReimbursement(response, parameter);
						}catch(NumberFormatException e) {
							//user sent a string in path parameter
							reimbController.getUserReimbursements(response, UrlSections[1]);
						}
						
					}
					else if(request.getMethod().equals("PUT")) {
						reimbController.updateStatus(request, response);
					}
				}
				break;
			case "login":
				if(UrlSections.length == 1) {
					//TODO: Change method to POST
					if(request.getMethod().equals("POST")) {
						userController.login(request,response);
					}
				}
				break;
				
		}	
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}

}
