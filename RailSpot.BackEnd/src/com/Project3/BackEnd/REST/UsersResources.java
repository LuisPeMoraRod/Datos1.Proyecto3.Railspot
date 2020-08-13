package com.Project3.BackEnd.REST;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.Project3.BackEnd.TicketsManagement.User;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResources {
	User user;
	RegisteredUsers users = RegisteredUsers.getInstance();

	@GET
	@Path("/{id}")
	public Response getUser(@PathParam("id") String id) {
		user = users.getUser(id);
		if (user != null)
			return Response.status(Status.OK).entity(user).build();
		return Response.status(Status.NOT_FOUND).entity("Error: user not found").build();
	}
}
