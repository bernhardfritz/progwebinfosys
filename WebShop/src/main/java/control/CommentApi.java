package control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import model.User;

@Path("/comment")
public class CommentApi {
	@Path("/{commentId}")
	@GET()
	@Produces("application/json")
	public Response getComment(@PathParam("commentId") Long itemCommentId) {
		return Response.ok(DBManager.getInstance().getItemComment(itemCommentId)).build();
	}
	
	@Path("/{commentId}")
	@PUT()
	@Consumes("application/x-www-form-urlencoded")
	public void putComment(@Context HttpServletRequest req, @Context HttpServletResponse res, @PathParam("commentId") Long itemCommentId, @FormParam("text") String text, @FormParam("rating") Integer rating) {
		User currentUser = ((User)req.getSession().getAttribute("user"));
		DBManager.getInstance().editItemComment(itemCommentId, text, rating, currentUser);
		
		try {
			res.sendRedirect("/WebShop/index.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Path("/{commentId}")
	@DELETE()
	public void deleteComment(@PathParam("commentId") Long itemCommentId) {
		DBManager.getInstance().deleteItemComment(itemCommentId);
	}
}
