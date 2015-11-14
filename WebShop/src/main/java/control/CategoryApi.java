package control;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
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

import model.Category;
import model.User;

@Path("/category")
public class CategoryApi {
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategories() {
		return Response.ok(DBManager.getInstance().getCategories()).build();
	}
	
	@POST()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postCategory(@Context HttpServletRequest req, @FormParam("name") String name, @FormParam("description") String description) throws URISyntaxException {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		Category category = DBManager.getInstance().createCategory(name, description, currentUser);
		if(category != null) return Response.ok(category).build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/{categoryId}")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCategory(@Context HttpServletRequest req, @PathParam("categoryId") Long categoryId, @FormParam("name") String name, @FormParam("description") String description) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		Category category = DBManager.getInstance().editCategory(categoryId, name, description, currentUser);
		if(category != null) return Response.ok(category).build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/{categoryId}")
	@DELETE()
	public Response deleteCategory(@PathParam("categoryId") Long categoryId) {
		if(DBManager.getInstance().deleteCategory(categoryId)) return Response.noContent().build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/{categoryId}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategory(@PathParam("categoryId") Long categoryId) {
		return Response.ok(DBManager.getInstance().getCategoryById(categoryId)).build();
	}
	
	@Path("/{categoryId}/item")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItems(@PathParam("categoryId") Long categoryId) {
		return Response.ok(DBManager.getInstance().getItems(categoryId)).build();
	}
}
