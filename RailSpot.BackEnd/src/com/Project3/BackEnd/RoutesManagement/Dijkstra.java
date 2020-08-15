package com.Project3.BackEnd.RoutesManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Dijkstra {
	/**
	 * Public class. Dijkstra algorithm for finding the shortest paths between nodes
	 * 
	 * @author Luis Pedro Morales Rodriguez
	 * @version 8/8/2020
	 */

	private List<Station> uncheckedStations; // Stations that haven't been checked by the algorithm
	private List<Station> route;// Shows the shortest route from A to B
	private Map<Station, Float> weights; // Every station with its weight
	private Map<Station, Station> procedence; // Every station with the previous station in the route
	private Graph graph;

	public Dijkstra() {
		graph = Graph.getInstance();
		uncheckedStations = new ArrayList<>();
		// adds every station of the graph to the unchecked stations list
		Station tempStation;
		for (int i=0; i<graph.getStations().size();i++) {
			tempStation = graph.getStations().get(i);
			uncheckedStations.add(tempStation);
		}
		route = new ArrayList<>();
		weights = new HashMap<>();
		procedence = new HashMap<>();
	}

	/**
	 * Executes Dijkstra algorithm to find the optimum route form origin station to
	 * destiny station
	 * 
	 * @param origin  : Station
	 * @param destiny : Station
	 * @return
	 */
	public List<Station> execute(Station origin, Station destiny) {
		uncheckedStations.remove(origin); // removes the origin station from the unchecked stations list
		weights.put(origin, (float) 0); // adds origin station with a 0 weight
		procedence.put(origin, null); // add origin station with null procedence
		Station tempStation = origin;
		while (!(uncheckedStations.isEmpty()) || tempStation!=null) {// iterates until uncheckedStations list is empty

			if (tempStation.getConnections() != null) {// if station has at least one connection
				for (Connection connection : tempStation.getConnections()) {
					Station tempDestiny = graph.getStation(connection.getDestiny());
					Float tempDistance = connection.getDistance() + tempStation.getAccumWeight();// accumulated distance
																									// from origin to
																									// current station
					if (!weights.containsKey(tempDestiny)) {// if the destiny hasn't been analyzed, it is added to the
															// map
						weights.put(tempDestiny, tempDistance);
						tempDestiny.setAccumWeight(tempDistance);
						procedence.put(tempDestiny, tempStation);
					} else if (weights.get(tempDestiny) > tempDistance) {// replaces distance if it's shorter than the
																			// actual
						// value
						weights.replace(tempDestiny, tempDistance);
						tempDestiny.setAccumWeight(tempDistance);
						procedence.replace(tempDestiny, tempStation);
					}
				}

			}
			tempStation = getNearest();
		}

		route = getOptimumRoute(procedence, origin, destiny);
		return route;
	}

	/**
	 * Searches for the station with the partial lowest weight in the weights
	 * ArrayList
	 * 
	 * @return nearest : Station
	 */
	public Station getNearest() {
		Station nearest = null;
		Float distance = Float.MAX_VALUE;
		for (Map.Entry<Station, Float> entry : this.weights.entrySet()) {
			if (entry.getValue() < distance && uncheckedStations.contains(entry.getKey())) {
				nearest = entry.getKey();
				distance = entry.getValue();
			}
		}
		uncheckedStations.remove(nearest);
		return nearest;
	}

	/**
	 * Generates the most optimum route from origin station to destiny station in
	 * function of the procedence HashMap
	 * 
	 * @param procedence
	 * @param origin
	 * @param destiny
	 * @return route : ArrayList<Station>
	 */
	public List<Station> getOptimumRoute(Map<Station, Station> procedence, Station origin, Station destiny) {
		List<Station> optimumRoute = new ArrayList<>();
		Station tempDestiny = destiny;
		boolean stop = false;
		while (!stop) {
			Iterator<Entry<Station, Station>> iterator;
			iterator = procedence.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Station, Station> mapEntry =  iterator.next();
				if (mapEntry.getKey().equals(tempDestiny)) {
					optimumRoute.add(0, tempDestiny);
					if (!mapEntry.getValue().equals(origin))tempDestiny = mapEntry.getValue();
					else stop = true;
				}

			}
		}

		optimumRoute.add(0, origin);
		return optimumRoute;

	}

}
