package com.Project3.BackEnd.REST;

import java.util.ArrayList;

import com.Project3.BackEnd.TicketsManagement.User;

public class RegisteredUsers {
	private static RegisteredUsers registeredUsers = null;
	private ArrayList<User> users;
	
	private RegisteredUsers() {
		users = new ArrayList<User>();
	}
	public static synchronized RegisteredUsers getInstance() {
		if (registeredUsers == null) {
			registeredUsers = new RegisteredUsers();
		}
		return registeredUsers;
	}
	public ArrayList<User> getUsers() {
		return users;
	}
	public void addUser(User user) {
		this.users.add(user);
	}
	
	
}
