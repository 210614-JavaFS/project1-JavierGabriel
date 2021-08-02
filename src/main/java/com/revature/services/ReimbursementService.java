package com.revature.services;

import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.repos.ReimbursementDAO;
import com.revature.repos.ReimbursementDAOImpl;

public class ReimbursementService {

	private static ReimbursementDAO reimbDAO = new ReimbursementDAOImpl();
	
	public List<Reimbursement> getAll(){
		return reimbDAO.getAll();
	}
	
	public List<Reimbursement> getUserReimbursements(String username){
		return reimbDAO.getAllByUser(username);
	}

	public Reimbursement getOne(int reimb_id) {
		return reimbDAO.getOne(reimb_id);
	}

	public boolean addReimbursement(Reimbursement reimbursement) {
		return reimbDAO.addReimbursement(reimbursement);
	}
	
	public boolean updateStatus(int reimb_id, String status, String resolver) {
		return reimbDAO.updateStatus(reimb_id, status, resolver);
	}
}
