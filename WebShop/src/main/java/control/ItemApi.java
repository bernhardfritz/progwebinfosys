package control;

import java.io.IOException;
import java.math.BigDecimal;

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

import model.Item;
import model.User;

@Path("/item")
public class ItemApi {
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItems() {
		return Response.ok(DBManager.getInstance().getItems()).build();
	}
	
	@POST()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postItem(@Context HttpServletRequest req, @Context HttpServletResponse res, @FormParam("title") String title, @FormParam("description") String description, @FormParam("price") BigDecimal price, @FormParam("categoryId") Long categoryId) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		Item item = DBManager.getInstance().createItem(title, description, price, categoryId, currentUser);
		
		try {
			if (item != null) {	
				res.sendRedirect("/WebShop/item.jsp?itemId=" + item.getId());
			}
			else {
				res.sendRedirect("/WebShop/item.jsp");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Path("/{itemId}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItem(@PathParam("itemId") Long itemId) {
		return Response.ok(DBManager.getInstance().getItem(itemId)).build();
	}
	
	@Path("/{itemId}")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void putItem(@Context HttpServletRequest req, @Context HttpServletResponse res, @PathParam("itemId") Long itemId, @FormParam("title") String title, @FormParam("description") String description, @FormParam("price") BigDecimal price, @FormParam("categoryId") Long categoryId) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		DBManager.getInstance().editItem(itemId, title, description, price, categoryId, currentUser);
		
		try {
			res.sendRedirect("/WebShop/index.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Path("/{itemId}")
	@DELETE()
	public void deleteItem(@PathParam("itemId") Long itemId) {
		DBManager.getInstance().deleteItem(itemId);
	}
	
	@Path("/{itemId}/comment")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItemComments(@PathParam("itemId") Long itemId) {
		return Response.ok(DBManager.getInstance().getItemComments(itemId)).build();
	}
	
	@Path("/{itemId}/comment")
	@POST()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postItemComment(@Context HttpServletRequest req, @Context HttpServletResponse res, @FormParam("text") String text, @PathParam("itemId") Long itemId, @FormParam("rating") Integer rating) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		DBManager.getInstance().createItemComment(text, itemId, rating, currentUser);
		
		try {
			res.sendRedirect("/WebShop/item.jsp?itemId=" + itemId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
