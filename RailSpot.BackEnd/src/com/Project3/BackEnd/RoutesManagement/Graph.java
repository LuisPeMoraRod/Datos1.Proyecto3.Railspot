package com.Project3.BackEnd.RoutesManagement;

import java.util.ArrayList;

public class Graph {
	private static Graph graph = null;
	private  ArrayList<Station> stations;
	private Graph() {
		stations = new ArrayList<Station>();
	}
	
	public static synchronized Graph getInstance() {
		if(graph == null) {
			graph = new Graph();
		}
		return graph;
	}

	public ArrayList<Station> getStations() {
		return stations;
	}

	public void setStations(ArrayList<Station> stations) {
		this.stations = stations;
	}
	
	public void addStation(Station newStation) {
		if (!this.stations.contains(newStation)) stations.add(newStation);
	}
	
	public void removeStation(Station station) {
		if (this.stations.contains(station)) stations.remove(station);
	}
	

}
