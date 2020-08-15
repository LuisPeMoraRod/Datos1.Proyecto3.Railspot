package com.Project3.BackEnd.RoutesManagement;

import java.util.ArrayList;
import java.util.List;

import com.Project3.BackEnd.TicketsManagement.Ticket;

public class Graph {
	private static Graph graph = null;
	private List<Station> stations;
	private List<Ticket> tickets;

	private Graph() {
		stations = new ArrayList<>();
		tickets = new ArrayList<>();
	}

	public static synchronized Graph getInstance() {
		if (graph == null) {
			graph = new Graph();
		}
		return graph;
	}

	public List<Station> getStations() {
		return stations;
	}

	public void setStations(List<Station> stations) {
		this.stations = stations;
	}

	public void addStation(Station newStation) {
		if (!this.stations.contains(newStation))
			stations.add(newStation);
	}

	public void removeStation(Station station) {
		if (this.stations.contains(station))
			stations.remove(station);
	}

	public Station getStation(String stationName) {
		Station station = null;
		for (Station element : this.stations) {
			if (element.getName().equals(stationName)) {
				station = element;
			}
		}
		return station;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	public void addTicket(Ticket ticket) {
		this.tickets.add(ticket);
	}
	
	public void removeTicket(Ticket ticket) {
		this.tickets.remove(ticket);
	}
	

}
