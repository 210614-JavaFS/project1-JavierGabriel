package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
				if(UrlSections.length == 1) {
					if(request.getMethod().equals("GET")) {
						reimbController.getAllReimbursements(response);
					}
					else if(request.getMethod().equals("POST")) {
					//call reimbursement controller to create new reimbursement
					}
				}
				else if(UrlSections.length == 2) {
					if(request.getMethod().equals("GET")) {
						//Get reimbursement of id UrlSections[1]
						reimbController.getOneReimbursement(response, Integer.parseInt(UrlSections[1]));
					}
					else if(request.getMethod().equals("PATCH")) {
					//call reimbursement controller to update the reimbursement of id UrlSections[1]
					}
				}
				break;
			case "login":
				if(request.getMethod().equals("GET")) {
					userController.login();
				}
		}	
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}

}
