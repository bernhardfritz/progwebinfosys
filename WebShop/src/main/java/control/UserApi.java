package control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import model.User;

@Path("/user")
public class UserApi {
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		return Response.ok(DBManager.getInstance().getUsers()).build();
	}
	
	@POST()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postUser(@FormParam("username") String username, @FormParam("password") String password, @FormParam("privileges") Integer bitmap) {
		DBManager.getInstance().createUser(username, password, bitmap);
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
	public void putUser(@PathParam("userId") Long userId, @FormParam("password") String password, @FormParam("privileges") Integer bitmap) {
		DBManager.getInstance().editUser(userId, password, bitmap);
	}
	
	@Path("/{userId}")
	@DELETE()
	public void deleteUser(@PathParam("userId") Long userId) {
		DBManager.getInstance().deleteUser(userId);
	}
	
	@Path("/login")
	@POST()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void loginUser(@Context HttpServletRequest req, @Context HttpServletResponse res, @FormParam("username") String username, @FormParam("password") String password) {
		HttpSession session = req.getSession(true);
		User user = DBManager.getInstance().login(username, password);
		session.setAttribute("user", user);
		try {
			res.sendRedirect("/WebShop/index.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Path("/logout")
	@POST()
	public void logoutUser(@Context HttpServletRequest req, @Context HttpServletResponse res) {
		HttpSession session = req.getSession(true);
		User user = DBManager.getInstance().logout();
		session.setAttribute("user", user);
		try {
			res.sendRedirect("/WebShop/index.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}