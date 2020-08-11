package com.Project3.BackEnd.TicketsManagement;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class User {
	
	private String id;
	private String name;
	private String email;
	private String password;
	private boolean admin;
	private MD5 md5;
	
	public User(String id, String name, String email,String password, boolean admin) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.admin = admin;
		md5 = new MD5(password);
		try {
			setPassword();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword() throws NoSuchAlgorithmException, NoSuchProviderException {
		
		this.password = md5.getMD5();
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
}
