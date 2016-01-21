package control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.map.ObjectMapper;

import model.User;

@Path("/user")
public class UserApi {
	
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		return Response.ok(DBManager.getInstance().getUsers()).build();
	}
	
	@Path("/current")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentUser(@Context HttpServletRequest req) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		return Response.ok(currentUser).build();
	}
	
	/**
	 * @param data
	 * 	{
	 * 		"username": "user",
	 * 		"password": "test"
	 * 	}
	 */
	@SuppressWarnings("unchecked")
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postUser(String data) {
		Map<String, String> map = new HashMap<String, String>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			map = objectMapper.readValue(data, HashMap.class);
			User user = DBManager.getInstance().createUser(map.get("username"), map.get("password"));
			if(user != null) return Response.ok(user).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Response.serverError().build();
	}
	
	@Path("/{userId}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("userId") Long userId) {
		return Response.ok(DBManager.getInstance().getUserById(userId)).build();
	}
	
	/**
	 * @param data
	 * 	{
	 * 		"password": "test"
	 * 	}
	 */
	@SuppressWarnings("unchecked")
	@Path("/{userId}")
	@PUT()
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putUser(@Context HttpServletRequest req, @PathParam("userId") Long userId, String data) {
		Map<String, String> map = new HashMap<String, String>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			map = objectMapper.readValue(data, HashMap.class);
			User user = DBManager.getInstance().editUser(userId, map.get("password"));
			if(user != null) return Response.ok(user).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Response.status(Status.UNAUTHORIZED).build();
	}

	@Path("/{userId}")
	@DELETE()
	public Response deleteUser(@Context HttpServletRequest req, @PathParam("userId") Long userId) {
		if(DBManager.getInstance().deleteUser(userId)) return Response.noContent().build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	/**
	 * @param data
	 * 	{
	 * 		"username": "user",
	 * 		"password": "test"
	 * 	}
	 */
	@SuppressWarnings("unchecked")
	@Path("/login")
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loginUser(@Context HttpServletRequest req, String data) {
		HttpSession session = req.getSession();
		Map<String, String> map = new HashMap<String, String>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			map = objectMapper.readValue(data, HashMap.class);
			User user = DBManager.getInstance().login(map.get("username"), map.get("password"));
			if(user != null) {
				session.setAttribute("user", user);
				return Response.ok().build();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/logout")
	@POST()
	public Response logoutUser(@Context HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.setAttribute("user", null);
		return Response.ok().build();
	}
}