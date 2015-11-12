package control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	public void postCategory(@Context HttpServletRequest req, @Context HttpServletResponse res, @FormParam("name") String name, @FormParam("description") String description) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		Category category = DBManager.getInstance().createCategory(name, description, currentUser);
		
		try {
			if (category != null) {
				res.sendRedirect("/WebShop/index.jsp?categoryId=" + category.getId());
			}
			else {
				res.sendRedirect("/WebShop/index.jsp");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Path("/{categoryId}")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void putCategory(@Context HttpServletRequest req, @PathParam("categoryId") Long categoryId, @FormParam("name") String name, @FormParam("description") String description) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		DBManager.getInstance().editCategory(categoryId, name, description, currentUser);
	}
	
	@Path("/{categoryId}")
	@DELETE()
	public void deleteCategory(@PathParam("categoryId") Long categoryId) {
		DBManager.getInstance().deleteCategory(categoryId);
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
