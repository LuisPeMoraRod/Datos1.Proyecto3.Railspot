package com.Project3.BackEnd.REST;

import java.util.List;

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
	private String noStation = "Error: station %s doesn't exist.";
	private String activeTickets = "Error: station %s has active tickets.";

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
		String entity = String.format(noStation, stationName);
		Station station = graph.getStation(stationName);
		if (station != null)
			return Response.status(Status.OK).entity(station).build();
		else
			return Response.status(Status.NOT_FOUND).entity(entity).build();
	}

	/**
	 * Delete a station only if it has no active tickets
	 * 
	 * @param stationName : String
	 * @return response : Response
	 */
	@POST
	@Path("/delete-station/{stationName}")
	public Response deleteStation(@PathParam("stationName") String stationName) {
		Station station = graph.getStation(stationName);
		String entity = String.format(noStation, stationName);
		if (station != null) {
			if (!(station.getActiveTickets().isEmpty())) {
				entity=String.format(activeTickets, stationName);
				return Response.status(Status.CONFLICT).entity(entity).build();
			}
			graph.removeStation(station);
			return Response.status(Status.OK).entity("Station removed from the graph successfully.").build();
		}
		return Response.status(Status.NOT_FOUND).entity(entity).build();
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
		String entity = String.format(noStation, stationName);
		if (station == null)
			return Response.status(Status.NOT_FOUND).entity(entity).build();

		if (destiny == null) {
			entity = String.format(noStation, destinyName);
			return Response.status(Status.NOT_FOUND).entity(entity).build();
		}
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
		String entity = String.format(noStation, stationName);
		if (station == null)
			return Response.status(Status.NOT_FOUND).entity(entity).build();

		else if (destiny == null) {
			entity = String.format(noStation, destinyName);
			return Response.status(Status.NOT_FOUND).entity(entity).build();
		}

		if (!(station.getActiveTickets().isEmpty())) {
			entity = String.format(activeTickets, stationName);
			return Response.status(Status.CONFLICT).entity(entity).build();
		} else if (!(destiny.getActiveTickets().isEmpty())) {
			entity = String.format(activeTickets, destinyName);
			return Response.status(Status.CONFLICT).entity(entity).build();
		} else {
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
		String entity = String.format(noStation, stationName);
		if (station == null)
			return Response.status(Status.NOT_FOUND).entity(entity).build();
		if (!(station.getActiveTickets().isEmpty())) {
			entity = String.format(activeTickets, stationName);
			return Response.status(Status.CONFLICT).entity(entity).build();
		}
		if (station.getConnections().size() <= 1) {
			entity = String.format("Error: station %s can't have no connections.", stationName);
			return Response.status(Status.CONFLICT).entity(entity).build();
		} else {
			Connection connection = station.getConnection(destinyName);
			if (station.hasConnection(connection)) {
				station.removeConnection(connection);
				return Response.status(Status.OK).entity("Connection deleted successfully").build();
			} else {
				entity = String.format("Error: connection to %s is unexistent.", destinyName);
				return Response.status(Status.NOT_FOUND).entity(entity).build();
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
		List<Ticket> tickets = graph.getTickets();
		String entity = String.format("Tickets for %s at %s were removes successfully.", date, hour);
		for (Ticket ticket : tickets) {
			if (ticket.getDate().equals(date) && ticket.getHour().equals(hour))
				removeTicketFromQueues(ticket);
		}
		return Response.status(Status.OK).entity(entity).build();
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
