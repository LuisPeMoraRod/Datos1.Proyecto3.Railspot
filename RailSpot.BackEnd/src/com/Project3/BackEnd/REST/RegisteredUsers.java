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

	public User getUser(String id) {
		for (User temp : users) {
			if(temp.getId().equals(id))
				return temp;
		}
		return null;
	}

	public boolean addUser(User user) {
		for (User temp : users) {
			if (temp.getId().equals(user.getId()))
				return false;
		}
		this.users.add(user);
		return true;

	}

}
