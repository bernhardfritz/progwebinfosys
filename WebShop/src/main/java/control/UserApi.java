package control;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserApi {
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		return Response.ok(DBManagerImpl.getInstance().getUsers()).build();
	}
	
	@POST()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postUser(@FormParam("username") String username, @FormParam("password") String password, @FormParam("privileges") Integer bitmap) {
		DBManagerImpl.getInstance().createUser(username, password, bitmap);
	}
	
	@Path("/{userId}")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void putUser(@PathParam("userId") Integer userId, @FormParam("password") String password, @FormParam("privileges") Integer bitmap) {
		DBManagerImpl.getInstance().editUser(userId, password, bitmap);
	}
	
	@Path("/{userId}")
	@DELETE()
	public void deleteUser(@PathParam("userId") Integer userId) {
		DBManagerImpl.getInstance().deleteUser();
	}
}
