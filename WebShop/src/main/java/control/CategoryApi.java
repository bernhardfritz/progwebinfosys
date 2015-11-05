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

@Path("/category")
public class CategoryApi {
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategories() {
		return Response.ok(DBManagerImpl.getInstance().getCategories()).build();
	}
	
	@POST()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postCategory(@FormParam("name") String name, @FormParam("description") String description, @FormParam("createUserId") Integer createUserId) {
		DBManagerImpl.getInstance().createCategory(name, description, createUserId);
	}
	
	@Path("/{categoryId}")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void putCategory(@PathParam("categoryId") Integer categoryId, @FormParam("name") String name, @FormParam("description") String description, @FormParam("updateUserId") Integer updateUserId) {
		DBManagerImpl.getInstance().editCategory(categoryId, name, description, updateUserId);
	}
	
	@Path("/{categoryId}")
	@DELETE()
	public void deleteCategory(@PathParam("categoryId") Integer categoryId) {
		DBManagerImpl.getInstance().deleteCategory(categoryId);
	}
	
	@Path("/{categoryId}/item")
	@GET()
	public Response getItems(@PathParam("categoryId") Integer categoryId) {
		return Response.ok(DBManagerImpl.getInstance().getItems(categoryId)).build();
	}
}
