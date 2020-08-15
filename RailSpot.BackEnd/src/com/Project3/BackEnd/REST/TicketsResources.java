package com.Project3.BackEnd.REST;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
import com.Project3.BackEnd.RoutesManagement.Dijkstra;
import com.Project3.BackEnd.RoutesManagement.Graph;
import com.Project3.BackEnd.RoutesManagement.Station;
import com.Project3.BackEnd.TicketsManagement.Ticket;
import com.Project3.BackEnd.TicketsManagement.User;

@Path("/tickets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TicketsResources {
	Graph graph = Graph.getInstance();
	RegisteredUsers users = RegisteredUsers.getInstance();

	@GET
	@Path("/get-stations")
	public Response getStations() {
		return Response.status(Status.OK).entity(graph.getStations()).build();
	}

	/**
	 * Buy new tickets depending on the most optimum route
	 * 
	 * @param ticketRequest
	 * @return response : Response
	 */
	@POST
	@Path("/buy-ticket")
	public Response buyTicket(@QueryParam("user") String id, @QueryParam("origin") String originName,
			@QueryParam("destiny") String destinyName, @QueryParam("date") String date, @QueryParam("hour") String hour,
			@QueryParam("amount") String amountString) {
		int amount = Integer.parseInt(amountString);

		Station origin = graph.getStation(originName);
		Station destiny = graph.getStation(destinyName);
		Dijkstra dijkstra = new Dijkstra();
		List<Station> route = dijkstra.execute(origin, destiny);
		List<Ticket> tickets = new ArrayList<>();
		Station tempOrigin;
		Station tempDestiny;
		Connection connection;
		Float previousDistance = (float) 0;
		for (int i = 0; i < route.size() - 1; i++) {
			tempOrigin = route.get(i);
			tempDestiny = route.get(i + 1);
			connection = tempOrigin.getConnection(tempDestiny.getName());
			hour = this.setHour(hour, previousDistance);
			Ticket ticket = new Ticket(id, tempOrigin.getName(), tempDestiny.getName(), connection.getPrice(), date,
					hour, amount);
			tickets.add(ticket);
			addTicketToQueues(ticket);
			previousDistance = connection.getDistance();

		}

		return Response.status(Status.OK).entity(tickets).build();
	}

	/**
	 * Returns active tickets in specific station
	 * 
	 * @param stationName : String
	 * @return response : Response
	 */
	@GET
	@Path("/get-by-station/{stationName}")
	public Response getByStation(@PathParam("stationName") String stationName) {
		Station station = graph.getStation(stationName);
		List<Ticket> tickets = station.getActiveTickets();
		if (tickets != null)
			return Response.status(Status.OK).entity(tickets).build();
		return Response.status(Status.NO_CONTENT).entity("There are no active tickets for this station.").build();
	}

	/**
	 * Returns active tickets owned by an specific user
	 * 
	 * @param id : String
	 * @return response : Response
	 */
	@GET
	@Path("/get-by-user/{id}")
	public Response getByUser(@PathParam("id") String id) {
		User user = users.getUser(id);
		List<Ticket> tickets = user.getTickets();
		if (tickets != null)
			return Response.status(Status.OK).entity(tickets).build();
		return Response.status(Status.NO_CONTENT).entity("User has no tickets.").build();
	}

	/**
	 * Returns active tickets in specific date
	 * 
	 * @param date : String
	 * @return response : Response
	 */
	@GET
	@Path("/get-by-date/{date}")
	public Response getByDate(@PathParam("date") String date) {
		List<Ticket> tickets = graph.getTickets();
		List<Ticket> ticketsByDate = new ArrayList<>();
		for (Ticket ticket : tickets) {
			if (ticket.getDate().equals(date))
				ticketsByDate.add(ticket);
		}
		if (!(ticketsByDate.isEmpty()))
			return Response.status(Status.OK).entity(ticketsByDate).build();
		return Response.status(Status.NO_CONTENT).entity("There are no active tickets for this date.").build();
	}

	@GET
	@Path("/get-all-tickets")
	public Response getAllTickets() {
		List<Ticket> tickets = graph.getTickets();
		return Response.status(Status.OK).entity(tickets).build();
	}

	/**
	 * Sets departure hour for next ticket
	 * 
	 * @param hour     : String
	 * @param distance : Float
	 * @return
	 */
	public String setHour(String hour, Float distance) {
		int hourInt = parseHour(hour);
		Float travelTime = (float) (distance / 45 + 0.1666);
		if (distance > 0)
			hourInt = (int) (hourInt + travelTime + (1 - travelTime % 1));
		return Integer.toString(hourInt) + ":00";

	}

	/**
	 * Parses string hour to int
	 * 
	 * @param hour
	 * @return
	 */
	public int parseHour(String hour) {
		StringTokenizer tokenizer = new StringTokenizer(hour, ":");
		return Integer.parseInt(tokenizer.nextToken());
		
	}

	/**
	 * Adds ticket to every list
	 * 
	 * @param ticket : String
	 */
	public void addTicketToQueues(Ticket ticket) {
		String destinyName = ticket.getArrivalStation();
		String originName = ticket.getDepartureStation();
		Station destiny = graph.getStation(destinyName);
		Station origin = graph.getStation(originName);
		User user = users.getUser(ticket.getUserId());
		destiny.addActiveTicket(ticket);
		origin.addActiveTicket(ticket);
		user.addTicket(ticket);
		graph.addTicket(ticket);
	}

}
