package com.Project3.BackEnd.RoutesManagement;

import java.util.ArrayList;

public class Station {
	/**
	 * @author Luis Pedro Morales Rodriguez
	 * @version 5/8/2020
	 */
	
	private String name;
	private String location;
	private ArrayList<Connection> connections;
	private Float accumWeight;
	
	public Station (String name) {
		this.name = name;
		this.accumWeight = new Float(0.0);
		connections = new ArrayList<Connection>();
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

	public ArrayList<Connection> getConnections() {
		return connections;
	}

	public void setConnections(ArrayList<Connection> connections) {
		this.connections = connections;
	}
	
	public void addConnection(Connection newConnection) {
		if (!this.connections.contains(newConnection)) connections.add(newConnection);
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
	
}
