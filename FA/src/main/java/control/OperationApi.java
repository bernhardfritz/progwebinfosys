package control;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Operation;
import model.User;

@Path("/operation")
public class OperationApi {
	
	private User getCurrentUser(HttpServletRequest req) {
		return ((User)req.getSession().getAttribute("user"));
	}
	
	@Path("/all/{accountNumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOperationsByAccountNumber(@PathParam("accountNumber") String accountNumber) {
		return Response.ok(DBManager.getInstance().getOperationsByAccountNumber(accountNumber)).build();
	}
	
	@Path("/deposit")
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deposit(@Context HttpServletRequest req, BigDecimal amount) {
		User currentUser = getCurrentUser(req);
		
		Operation operation = DBManager.getInstance().createOperation(currentUser, currentUser, amount, currentUser);
		if (operation != null) return Response.ok(operation).build();
		return Response.serverError().build();
	}
	
	@Path("/transfer")
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response transfer(@Context HttpServletRequest req, String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
		User currentUser = getCurrentUser(req);
		
		Operation operation = DBManager.getInstance().createOperation(fromAccountNumber, toAccountNumber, amount, currentUser);
		if (operation != null) return Response.ok(operation).build();
		return Response.serverError().build();
	}
}