package control;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import model.ItemComment;
import model.User;

@Path("/comment")
public class CommentApi {
	@Path("/{commentId}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getComment(@PathParam("commentId") Long itemCommentId) {
		return Response.ok(DBManager.getInstance().getItemComment(itemCommentId)).build();
	}
	
	@Path("/{commentId}")
	@PUT()
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putComment(@Context HttpServletRequest req, @PathParam("commentId") Long itemCommentId, @FormParam("text") String text, @FormParam("rating") Integer rating) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		ItemComment itemComment = null;
		try {
			itemComment = DBManager.getInstance().editItemComment(itemCommentId, URLDecoder.decode(text, "UTF-8"), rating, currentUser);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(itemComment != null) return Response.ok(itemComment).build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Path("/{commentId}")
	@DELETE()
	public Response deleteComment(@PathParam("commentId") Long itemCommentId) {
		if(DBManager.getInstance().deleteItemComment(itemCommentId)) return Response.noContent().build();
		else return Response.status(Status.UNAUTHORIZED).build();
	}
}
