package control;

import java.math.BigDecimal;

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

@Path("/item")
public class ItemApi {
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItems() {
		return Response.ok(DBManagerImpl.getInstance().getItems()).build();
	}
	
	@POST()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postItem(@FormParam("title") String title, @FormParam("description") String description, @FormParam("price") BigDecimal price, @FormParam("categoryId") Integer categoryId, @FormParam("createUserId") Integer createUserId) {
		DBManagerImpl.getInstance().createItem(title, description, price, categoryId, createUserId);
	}
	
	@Path("/{itemId}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItem(@PathParam("itemId") Integer itemId) {
		return Response.ok(DBManagerImpl.getInstance().getItem(itemId)).build();
	}
	
	@Path("/{itemId}")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void putItem(@PathParam("itemId") Integer itemId, @FormParam("title") String title, @FormParam("description") String description, @FormParam("price") BigDecimal price, @FormParam("categoryId") Integer categoryId, @FormParam("updateUserId") Integer updateUserId) {
		DBManagerImpl.getInstance().editItem(itemId, title, description, price, categoryId, updateUserId);
	}
	
	@Path("/{itemId}")
	@DELETE()
	public void deleteItem(@PathParam("itemId") Integer itemId) {
		DBManagerImpl.getInstance().deleteItem(itemId);
	}
	
	@Path("/{itemId}/comment")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getComments(@PathParam("itemId") Integer itemId) {
		return Response.ok(DBManagerImpl.getInstance().getComments(itemId)).build();
	}
	
	@Path("/{itemId}/comment")
	@POST()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postComment(@FormParam("text") String text, @PathParam("itemId") Integer itemId, @PathParam("createUserId") Integer createUserId) {
		DBManagerImpl.getInstance().createComment(text, itemId, createUserId);
	}
}
