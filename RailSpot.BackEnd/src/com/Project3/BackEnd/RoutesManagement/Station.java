package com.Project3.BackEnd.RoutesManagement;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.Project3.BackEnd.TicketsManagement.Ticket;

public class Station {
	/**
	 * @author Luis Pedro Morales Rodriguez
	 * @version 5/8/2020
	 */
	
	private String name;
	private String location;
	private List<Connection> connections;
	private Float accumWeight;
	private List<Ticket> activeTickets;
	
	public Station (String name) {
		this.name = name;
		this.accumWeight = new Float(0.0);
		this.connections = new ArrayList<>();
		this.activeTickets = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Connection> getConnections() {
		return connections;
	}

	public void setConnections(List<Connection> connections) {
		this.connections = connections;
	}
	
	public void addConnection(Connection newConnection) {
		if (!this.hasConnection(newConnection)) connections.add(newConnection);
	}
	
	public void removeConnection(Connection connection) {
		if (this.hasConnection(connection)) connections.remove(connection);
	}
	public Connection getConnection(String destiny) {
		Connection connection = null;
		for(Connection element : this.connections) {
			if (element.getDestiny().equals(destiny)) connection = element;
		}
		return connection;
	}
	
	public boolean hasConnection(Connection connection) {
		if (connection == null)
			return false;
		for (Connection element : this.connections) {
			if (element.getDestiny().equals(connection.getDestiny())) return true;
		}
		return false;
	}

	public boolean editConnection(Connection newConnection) {
		if (this.hasConnection(newConnection)) {
			Connection connection = getConnection(newConnection.getDestiny());
			this.removeConnection(connection);
			this.addConnection(newConnection);
			return true;
		}
		return false;
	}
	public Float getAccumWeight() {
		return accumWeight;
	}

	public void setAccumWeight(Float accumWeight) {
		this.accumWeight = accumWeight;
	}
	
	public void incrementAccumWeight(Float distance) {
		this.accumWeight+=distance;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject parse() {
		JSONObject jsonStation = new JSONObject();
		jsonStation.put("name", this.name);
		jsonStation.put("location", this.location);
		return jsonStation;
		
	}

	public List<Ticket> getActiveTickets() {
		return activeTickets;
	}

	public void addActiveTicket(Ticket ticket) {
		this.activeTickets.add(ticket);
	}
	
	public void removeActiveTicket(Ticket ticket) {
		this.activeTickets.remove(ticket);
	}
	
}
