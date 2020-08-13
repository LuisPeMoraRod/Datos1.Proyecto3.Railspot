package com.Project3.BackEnd.REST;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import com.Project3.BackEnd.TicketsManagement.Ticket;
import com.Project3.BackEnd.TicketsManagement.User;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResources {
	Graph graph = Graph.getInstance();
	RegisteredUsers users = RegisteredUsers.getInstance();

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
	 * Delete a station only if it has no active tickets
	 * @param stationName : String
	 * @return response : Response
	 */
	@POST
	@Path("/delete-station/{stationName}")
	public Response deleteStation(@PathParam("stationName") String stationName) {
		Station station = graph.getStation(stationName);
		if (station != null) {
			if (station.getActiveTickets().size()>0)
				return Response.status(Status.CONFLICT).entity("Error: station has active tickets.").build();
			graph.removeStation(station);
			return Response.status(Status.OK).entity("Station removed from the graph successfully.").build();
		}
		return Response.status(Status.NOT_FOUND).entity("Error: no station found with the name "+stationName).build();
	}

	/**
	 * Create new connection
	 * 
	 * @param stationName : String
	 * @param destinyName : String
	 * @param dist        : String
	 * @return response : Response
	 */
	@POST
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

	@POST
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

	@POST
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

	/**
	 * Removes active tickets in specific date and hour
	 * 
	 * @param date : String
	 * @param hour : String
	 * @return response : Response
	 */
	@POST
	@Path("/remove-unactive-tickets")
	public Response removeTicketsByDate(@QueryParam("date") String date, @QueryParam("hour") String hour) {
		ArrayList<Ticket> tickets = graph.getTickets();
		for (Ticket ticket : tickets) {
			if (ticket.getDate().equals(date) && ticket.getHour().equals(hour))
				removeTicketFromQueues(ticket);
		}
		return Response.status(Status.OK).entity("Tickets for " + date + " at " + hour + " were removed successfully.")
				.build();
	}

	/**
	 * Removes ticket from every list
	 * 
	 * @param ticket : String
	 */
	public void removeTicketFromQueues(Ticket ticket) {
		String destinyName = ticket.getArrivalStation();
		String originName = ticket.getDepartureStation();
		Station destiny = graph.getStation(destinyName);
		Station origin = graph.getStation(originName);
		User user = users.getUser(ticket.getUserId());
		destiny.removeActiveTicket(ticket);
		origin.removeActiveTicket(ticket);
		user.removeTicket(ticket);
		graph.removeTicket(ticket);
	}

}
