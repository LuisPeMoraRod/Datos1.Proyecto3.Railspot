package com.Project3.BackEnd.REST;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.Project3.BackEnd.RoutesManagement.Connection;
import com.Project3.BackEnd.RoutesManagement.Graph;
import com.Project3.BackEnd.RoutesManagement.Station;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResources {
	Graph graph = Graph.getInstance();

	@GET
	@Path("/get-stations")
	public Response getStations() {
		return Response.status(Status.OK).entity(graph.getStations()).build();
	}

	/**
	 * Add new station to the graph
	 * 
	 * @param name : String
	 * @return response : Response
	 */
	@POST
	@Path("/new-station")
	public Response addStation(@QueryParam("name") String name) {
		Station newStation = new Station(name);
		graph.addStation(newStation);

		return Response.status(Status.OK).entity("New station added succesfully to the graph.").build();
	}

	/**
	 * Get specific station
	 * 
	 * @param stationName : String
	 * @return response : Response
	 */
	@GET
	@Path("/{stationName}")
	public Response getStation(@PathParam("stationName") String stationName) {
		Station station = graph.getStation(stationName);
		if (station != null)
			return Response.status(Status.OK).entity(station).build();
		else
			return Response.status(Status.NOT_FOUND).entity("Error: station " + stationName + " doesn't exist.")
					.build();
	}

	/**
	 * Create new connection
	 * 
	 * @param stationName : String
	 * @param destinyName : String
	 * @param dist        : String
	 * @return response : Response
	 */
	@PUT
	@Path("/new-connection/{stationName}")
	public Response newConnection(@PathParam("stationName") String stationName,
			@QueryParam("destiny") String destinyName, @QueryParam("distance") String dist) {
		Station station = graph.getStation(stationName);
		Station destiny = graph.getStation(destinyName);
		if (station == null)
			return Response.status(Status.NOT_FOUND).entity("Error: station " + stationName + " doesn't exist.")
					.build();

		if (destiny == null)
			return Response.status(Status.NOT_FOUND).entity("Error: station " + destinyName + " doesn't exist.")
					.build();
		Float distance = Float.parseFloat(dist);
		Connection connection = new Connection(destiny, distance);
		station.addConnection(connection);
		return Response.status(Status.OK).entity(connection).build();
	}

	@PUT
	@Path("/edit-connection/{stationName}")
	public Response editConnection(@PathParam("stationName") String stationName,
			@QueryParam("destiny") String destinyName, @QueryParam("distance") String dist) {
		Station station = graph.getStation(stationName);
		Station destiny = graph.getStation(destinyName);
		if (station == null)
			return Response.status(Status.NOT_FOUND).entity("Error: station " + stationName + " doesn't exist.")
					.build();

		else if (destiny == null)
			return Response.status(Status.NOT_FOUND).entity("Error: station " + destinyName + " doesn't exist.")
					.build();

		if (station.getActiveTickets().size() > 0)
			return Response.status(Status.CONFLICT).entity("Error: station " + stationName + " has active tickets.")
					.build();
		else if (destiny.getActiveTickets().size() > 0)
			return Response.status(Status.CONFLICT).entity("Error: station " + destinyName + " has active tickets.")
					.build();
		else {
			Float distance = Float.parseFloat(dist);
			Connection connection = new Connection(destiny, distance);
			if (station.editConnection(connection))
				return Response.status(Status.OK).entity(connection).build();
			else
				return Response.status(Status.NOT_FOUND).entity("Error: connection not found.").build();
		}
	}

	@DELETE
	@Path("delete-connection/{stationName}")
	public Response deleteConnection(@PathParam("stationName") String stationName,
			@QueryParam("destiny") String destinyName) {
		Station station = graph.getStation(stationName);
		if (station == null)
			return Response.status(Status.NOT_FOUND).entity("Error: station " + stationName + " doesn't exist.")
					.build();
		if (station.getActiveTickets().size() > 0)
			return Response.status(Status.CONFLICT).entity("Error: station " + stationName + " has active tickets.")
					.build();
		else {
			Connection connection = station.getConnection(destinyName);
			if (station.hasConnection(connection)) {
				station.removeConnection(connection);
				return Response.status(Status.OK).entity("Connection deleted successfully").build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity("Error: connection to " + stationName + " is unexistent.").build();
			}
		}
	}

}
