package com.revature.repos;

import java.util.List;

import com.revature.models.Reimbursement;

public interface ReimbursementDAO {

	public List<Reimbursement> getAll();
	public List<Reimbursement> getAllByUser(String username);
	public Reimbursement getOne(int reimb_id);
	public int getStatusId(String status);
	public int getTypeId(String type);
	public boolean addReimbursement(Reimbursement reimbursement);
	public boolean updateStatus(int reimb_id, int status, int resolver);
	public List<Reimbursement> getByStatus(int status);
}
