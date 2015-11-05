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
	public Response getComment(@PathParam("commentId") Integer commentId) {
		return Response.ok(DBManagerImpl.getInstance().getComment(commentId)).build();
	}
	
	@Path("/{commentId}")
	@PUT()
	@Consumes("application/x-www-form-urlencoded")
	public void putComment(@PathParam("commentId") Integer commentId, @FormParam("text") String text, @FormParam("updateUserId") Integer updateUserId) {
		DBManagerImpl.getInstance().editComment(commentId, text, updateUserId);
	}
	
	@Path("/{commentId}")
	@DELETE()
	public void deleteComment(@PathParam("commentId") Integer commentId) {
		DBManagerImpl.getInstance().deleteComment(commentId);
	}
}
