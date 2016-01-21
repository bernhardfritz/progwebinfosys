package control;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
public class AccountApi {
	
	@Path("/{accountNumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccount(@PathParam("accountNumber") String accountNumber) {
		return Response.ok(DBManager.getInstance().getAccountByAccountNumber(accountNumber)).build();
	}
	
	@Path("/all/{username}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccounts(@PathParam("username") String username) {
		return Response.ok(DBManager.getInstance().getAccountsByUsername(username)).build();
	}
}