package com.revature.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;

public class APITests {
	
	ReimbursementService reimbService = new ReimbursementService();
	UserService userService = new UserService();
	
	@Test
	public void testGetOne() {
		Reimbursement reimb1 = reimbService.getOne(1);
		Reimbursement reimb2 = reimbService.getOne(1);
		
		assertEquals(reimb1,reimb2);
		
	}
	
	@Test
	public void testGetAll() {
		List<Reimbursement> all1 = reimbService.getAll();
		List<Reimbursement> all2 = reimbService.getAll();
		
		assertEquals(all1,all2);
	}
	
	@Test
	public void testUserReimb() {
		String username = "javier";
		
		List<Reimbursement> list = reimbService.getUserReimbursements(username);
		
		for(Reimbursement reimb : list) {
			assertEquals(reimb.getAuthor(), username);
		}
		
		
	}
	
	@Test
	public void testUpdateReimb() {
		assertTrue(reimbService.updateStatus(1, "denied", "tim"));
	}
	
	@Test
	public void testLogin() {
		assertTrue(userService.login("javier", "pass"));
	}
	
	@Test
	public void testLoginFalse() {
		assertFalse(userService.login("javier", "wrongPass"));
	}
	
	@Test
	public void testGetUser() {
		String username = "javier";
		User user = userService.getUser(username);
		
		assertEquals(user.getUsername(), username);
	}
	
}
