package com.revature.repos;

import java.util.List;

import com.revature.models.User;

public interface UserDAO {

	public List<User> getAll();
	public User getOne(String username);
	public boolean login(String username, String password);
	public int getUserId(String username);
}
