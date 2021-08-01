package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;

public class ReimbursementController {

	private static ReimbursementService reimbService = new ReimbursementService();
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public void getAllReimbursements(HttpServletResponse response) throws IOException{
		List<Reimbursement> list = reimbService.getAll();
		
		String json = objectMapper.writeValueAsString(list);
		
		System.out.println(json);
		
		PrintWriter printWriter = response.getWriter();
		
		printWriter.print(json);
		
		response.setStatus(200);
	}
	
	public void getUserReimbursements(HttpServletResponse response, String username) throws IOException{
		List<Reimbursement> list = reimbService.getUserReimbursements(username);
		
		String json = objectMapper.writeValueAsString(list);
		
		System.out.println(json);
		
		PrintWriter printWriter = response.getWriter();
		
		printWriter.print(json);
		
		response.setStatus(200);
	}
	
	public void getOneReimbursement(HttpServletResponse response, int reimb_id) throws IOException{
		Reimbursement reimb = reimbService.getOne(reimb_id);
		
		String json = objectMapper.writeValueAsString(reimb);
		
		System.out.println(json);
		
		PrintWriter printWriter = response.getWriter();
		
		printWriter.print(json);
		
		response.setStatus(200);
	}
	
	public void addReimbursment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		BufferedReader reader = request.getReader();
		
		StringBuilder stringBuilder = new StringBuilder();
		
		String line = reader.readLine();
		
		while(line != null){
			stringBuilder.append(line);
			line = reader.readLine();
		}
		
		String body = new String(stringBuilder);
		
		Reimbursement reimbursement = objectMapper.readValue(body, Reimbursement.class);
		
		if(reimbService.addReimbursement(reimbursement)) {
			response.setStatus(201);
		}else {
			response.setStatus(406);
		}
		
	}
}
