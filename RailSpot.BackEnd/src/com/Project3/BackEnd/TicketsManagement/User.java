package com.Project3.BackEnd.TicketsManagement;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String id;
	private String name;
	private String email;
	private String password;
	private boolean admin;
	private List<Ticket> tickets;
	private MD5 md5;

	public User(String id, String name, String email, String password, boolean admin) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.admin = admin;
		md5 = new MD5(password);
		setPassword();
		this.tickets = new ArrayList<>();
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

	public void setPassword(){

		this.password = md5.getMD5();
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void addTicket(Ticket ticket) {
		this.tickets.add(0, ticket);
	}

	public void removeTicket(Ticket ticket) {
		this.tickets.remove(ticket);
	}

}
