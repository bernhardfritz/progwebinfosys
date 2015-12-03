package control;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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

import model.ShoppingCart;
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
	
	@POST()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postUser(@FormParam("username") String username, @FormParam("password") String password) throws URISyntaxException {
		User user = DBManager.getInstance().createUser(username, password, 100100110000L);
		if(user != null) return Response.ok(user).build();
		else return Response.serverError().build();
	}
	
	@Path("/{userId}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("userId") Long userId) {
		return Response.ok(DBManager.getInstance().getUserById(userId)).build();
	}
	
	@Path("/{userId}")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putUser(@PathParam("userId") Long userId, @FormParam("password") String password, @FormParam("privileges") Long bitmap) {
		User user = null;
		System.out.println("hallo");
		if (password != null) {
			user = DBManager.getInstance().editUser(userId, password, bitmap);
		}
		else {
			user = DBManager.getInstance().editUserPrivileges(userId, bitmap);
		}
		
		if(user != null) return Response.ok(user).build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/{userId}")
	@DELETE()
	public Response deleteUser(@Context HttpServletRequest req, @PathParam("userId") Long userId) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		if(DBManager.getInstance().deleteUser(userId, currentUser)) return Response.noContent().build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/login")
	@POST()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response loginUser(@Context HttpServletRequest req, @FormParam("username") String username, @FormParam("password") String password) {
		HttpSession session = req.getSession();
		User user = DBManager.getInstance().login(username, password);
		if(user != null) {
			session.setAttribute("user", user);
			if (session.getAttribute("shoppingCart") == null) {
				session.setAttribute("shoppingCart", new ShoppingCart());
			}
			return Response.ok().build();
		} else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/logout")
	@POST()
	public Response logoutUser(@Context HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.setAttribute("shoppingCart", null);
		User user = DBManager.getInstance().logout();
		if(user != null) {
			session.setAttribute("user", user);
			return Response.ok().build();
		} else return Response.status(Status.UNAUTHORIZED).build();
	}
}