package control;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/comment")
public class CommentApi {
	@Path("/{commentId}")
	@GET()
	@Produces("application/json")
	public Response getComment(@PathParam("itemCommentId") Long itemCommentId) {
		return Response.ok(DBManager.getInstance().getItemComment(itemCommentId)).build();
	}
	
	@Path("/{commentId}")
	@PUT()
	@Consumes("application/x-www-form-urlencoded")
	public void putComment(@PathParam("itemCommentId") Long itemCommentId, @FormParam("text") String text, @FormParam("updateUserId") Long updateUserId) {
		DBManager.getInstance().editItemComment(itemCommentId, text, updateUserId);
	}
	
	@Path("/{commentId}")
	@DELETE()
	public void deleteComment(@PathParam("itemCommentId") Long itemCommentId) {
		DBManager.getInstance().deleteItemComment(itemCommentId);
	}
}
