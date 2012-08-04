package com.playfish.palindrome.dao;

import com.playfish.palindrome.model.User;

public interface UserDao {

	public User add(String uuid);
	
	public User get(String uuid);

	public User register(String uuid, String name);
	
	public void clear();
}
