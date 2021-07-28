package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.utils.ConnectionUtil;

public class ReimbursementDAOImpl implements ReimbursementDAO {
	
	UserDAO userDao = new UserDAOImpl();

	@Override
	public List<Reimbursement> getAll() {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT r.reimb_id ,r.amount, r.submitted, r.resolved, r.description,"
					+ "a.username AS author, m.username AS resolver, s.reimb_status AS status , t.reimb_type AS reimb_type "
					+ "FROM reimbursements r "
					+ "INNER JOIN reimbursement_status s "
					+ "ON r.status_id = s.reimb_status_id "
					+ "INNER JOIN reimbursement_types t "
					+ "ON r.reimb_type = t.reimb_type_id "
					+ "INNER JOIN users a "
					+ "ON r.author= a.user_id "
					+ "LEFT JOIN users m "
					+ "ON r.resolver = m.user_id;";
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			List<Reimbursement> list = new ArrayList<>();
			
			while(result.next()) {
				Reimbursement reimb = new Reimbursement();
				reimb.setReimb_id(result.getInt("reimb_id"));
				reimb.setAmount(result.getDouble("amount"));
				reimb.setSubmitted(result.getDate("submitted").toString());
				reimb.setResolved((result.getDate("resolved") != null) ? result.getDate("resolved").toString():"");
				reimb.setDescription(result.getString("description"));
				reimb.setAuthor(result.getString("author"));
				reimb.setResolver(result.getString("resolver"));
				reimb.setStatus(result.getString("status"));
				reimb.setReimb_type(result.getString("reimb_type"));
				
				list.add(reimb);
			}
			
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Reimbursement getOne(int reimb_id) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT r.reimb_id ,r.amount, r.submitted, r.resolved, r.description,"
					+ "a.username AS author, m.username AS resolver, s.reimb_status AS status , t.reimb_type AS reimb_type "
					+ "FROM reimbursements r "
					+ "INNER JOIN reimbursement_status s "
					+ "ON r.status_id = s.reimb_status_id "
					+ "INNER JOIN reimbursement_types t "
					+ "ON r.reimb_type = t.reimb_type_id "
					+ "INNER JOIN users a "
					+ "ON r.author= a.user_id "
					+ "LEFT JOIN users m "
					+ "ON r.resolver = m.user_id "
					+ "WHERE r.reimb_id = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setInt(1, reimb_id);
			
			ResultSet result = statement.executeQuery();
			Reimbursement reimb = new Reimbursement();
			if(result.next()) {
				reimb.setReimb_id(result.getInt("reimb_id"));
				reimb.setAmount(result.getDouble("amount"));
				reimb.setSubmitted(result.getDate("submitted").toString());
				reimb.setResolved((result.getDate("resolved") != null) ? result.getDate("resolved").toString():"");
				reimb.setDescription(result.getString("description"));
				reimb.setAuthor(result.getString("author"));
				reimb.setResolver(result.getString("resolver"));
				reimb.setStatus(result.getString("status"));
				reimb.setReimb_type(result.getString("reimb_type"));
			}
			
			return reimb;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addReimbursement(Reimbursement reimbursement) {
		try (Connection conn = ConnectionUtil.getConnection()){
			int status_id = getStatusId("pending");
			int type_id = getTypeId(reimbursement.getReimb_type());
			int author = userDao.getUserId(reimbursement.getAuthor());
			
			String sql = "INSERT INTO reimbursements(amount, description, author, status_id, reimb_type) VALUES (?,?,?,?,?)";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int index = 0;
			statement.setDouble(++index, reimbursement.getAmount());
			statement.setString(++index, reimbursement.getDescription());
			statement.setInt(++index, author);
			statement.setInt(++index, status_id);
			statement.setInt(++index, type_id);
			
			statement.execute();
			
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean updateStatus(int reimb_id, int status, int resolver) {
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "UPDATE reimbursements SET status_id = ?, resolver = ? WHERE reimb_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int index = 0;
			statement.setInt(++index, status);
			statement.setInt(++index, resolver);
			statement.setInt(++index, reimb_id);
			statement.execute();
			
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Reimbursement> getByStatus(int status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStatusId(String status) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT reimb_status_id FROM reimbursement_status WHERE reimb_status = ?";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, status);
			
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return result.getInt("reimb_status_id");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int getTypeId(String type) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT reimb_type_id FROM reimbursement_types WHERE reimb_type = ?";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, type);
			
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return result.getInt("reimb_type_id");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
