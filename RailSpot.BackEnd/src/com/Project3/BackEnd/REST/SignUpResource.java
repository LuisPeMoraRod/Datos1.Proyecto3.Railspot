package com.Project3.BackEnd.REST;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.Project3.BackEnd.TicketsManagement.User;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/sign-up")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SignUpResource {
	RegisteredUsers users = RegisteredUsers.getInstance();

	@POST
	public Response signUpUser(JSONObject userJson) {
		String id = (String) userJson.get("id");
		String name = (String) userJson.get("name");
		String email = (String) userJson.get("email");
		String password = (String) userJson.get("password");
		boolean admin = (boolean) userJson.get("admin");
		User user = new User(id, name, email, password, admin);
		if (users.addUser(user))
			return Response.status(Status.OK).entity("User registered successfully.").build();
		else
			return Response.status(Status.CONFLICT).entity("Error: user already exists").build();

	}

}
