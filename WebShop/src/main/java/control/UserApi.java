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
	public Response putUser(@Context HttpServletRequest req, @PathParam("userId") Long userId, @FormParam("password") String password, @FormParam("privileges") Long bitmap) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		User user = null;
		if (password != null) {
			user = DBManager.getInstance().editUser(userId, password, bitmap);
		}
		else {
			user = DBManager.getInstance().editUserPrivileges(userId, bitmap, currentUser);
		}
		
		if(user != null) return Response.ok(user).build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/{userId}/country")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCountry(@Context HttpServletRequest req, @PathParam("userId") Long userId, @FormParam("country") String country) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		User user = null;
		if(currentUser.getId() == userId && country != null) {
			user = DBManager.getInstance().editCountry(userId, country);
		}
		
		if(user != null) return Response.ok(user).build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/{userId}/state")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putState(@Context HttpServletRequest req, @PathParam("userId") Long userId, @FormParam("state") String state) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		User user = null;
		if(currentUser.getId() == userId && state != null) {
			user = DBManager.getInstance().editState(userId, state);
		}
		
		if(user != null) return Response.ok(user).build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/{userId}/county")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCounty(@Context HttpServletRequest req, @PathParam("userId") Long userId, @FormParam("county") String county) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		User user = null;
		if(currentUser.getId() == userId && county != null) {
			user = DBManager.getInstance().editCounty(userId, county);
		}
		
		if(user != null) return Response.ok(user).build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/{userId}/postcode")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putPostcode(@Context HttpServletRequest req, @PathParam("userId") Long userId, @FormParam("postcode") String postcode) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		User user = null;
		if(currentUser.getId() == userId && postcode != null) {
			user = DBManager.getInstance().editPostcode(userId, postcode);
		}
		
		if(user != null) return Response.ok(user).build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/{userId}/city")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCity(@Context HttpServletRequest req, @PathParam("userId") Long userId, @FormParam("city") String city) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		User user = null;
		if(currentUser.getId() == userId && city != null) {
			user = DBManager.getInstance().editCity(userId, city);
		}
		
		if(user != null) return Response.ok(user).build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/{userId}/streetname")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putStreetname(@Context HttpServletRequest req, @PathParam("userId") Long userId, @FormParam("streetname") String streetname) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		User user = null;
		if(currentUser.getId() == userId && streetname != null) {
			user = DBManager.getInstance().editStreetname(userId, streetname);
		}
		
		if(user != null) return Response.ok(user).build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/{userId}/housenumber")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putHousenumber(@Context HttpServletRequest req, @PathParam("userId") Long userId, @FormParam("housenumber") String housenumber) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		User user = null;
		if(currentUser.getId() == userId && housenumber != null) {
			user = DBManager.getInstance().editHousenumber(userId, housenumber);
		}
		
		if(user != null) return Response.ok(user).build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/coordinates/{userId}")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCoordinates(@PathParam("userId") Long userId, @FormParam("lat") Double lat, @FormParam("lon") Double lon) {
		User user = null;
		user = DBManager.getInstance().editUserCoordinates(userId, lat, lon);
		
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
		User user = null;
		if(password == null) {
			if(username.startsWith("facebookuser")) {
				user = DBManager.getInstance().getUserByUsername(username);
				if(user == null) user = DBManager.getInstance().createUser(username, "2^}dnQN.Q#C%,F=Q", 100100110000L);
			}
		} else {
			user = DBManager.getInstance().login(username, password);
		}
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