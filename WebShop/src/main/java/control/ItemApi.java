package control;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URLDecoder;

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

import model.Item;
import model.ItemComment;
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response postItem(@Context HttpServletRequest req, @FormParam("title") String title, @FormParam("description") String description, @FormParam("price") BigDecimal price, @FormParam("categoryId") Long categoryId) throws URISyntaxException {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		Item item = null;
		try {
			item = DBManager.getInstance().createItem(URLDecoder.decode(title, "UTF-8"), URLDecoder.decode(description, "UTF-8"), price, categoryId, currentUser);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(item != null) return Response.ok(item).build();
		else return Response.status(Status.UNAUTHORIZED).build();
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response putItem(@Context HttpServletRequest req, @PathParam("itemId") Long itemId, @FormParam("title") String title, @FormParam("description") String description, @FormParam("price") BigDecimal price, @FormParam("categoryId") Long categoryId) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		Item item = null;
		try {
			item = DBManager.getInstance().editItem(itemId, URLDecoder.decode(title, "UTF-8"), URLDecoder.decode(description, "UTF-8"), price, categoryId, currentUser);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(item != null) return Response.ok(item).build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/{itemId}")
	@DELETE()
	public Response deleteItem(@PathParam("itemId") Long itemId) {
		if(DBManager.getInstance().deleteItem(itemId)) return Response.noContent().build();
		else return Response.status(Status.UNAUTHORIZED).build();
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response postItemComment(@Context HttpServletRequest req, @FormParam("text") String text, @PathParam("itemId") Long itemId, @FormParam("rating") Integer rating) throws URISyntaxException {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		ItemComment itemComment = null;
		try {
			itemComment = DBManager.getInstance().createItemComment(URLDecoder.decode(text, "UTF-8"), itemId, rating, currentUser);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(itemComment != null) return Response.ok(itemComment).build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
}
