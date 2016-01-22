package control;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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

import org.codehaus.jackson.map.ObjectMapper;

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
	
	// TODO: Fix deposit (maybe setup fake account where money can be booked to/from)
	/**
	 * @param data
	 * {
	 *     "accountNumber: "A123456789",
	 *     "amount": "100.0"
	 * }
	 */
	@SuppressWarnings("unchecked")
	@Path("/deposit")
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deposit(@Context HttpServletRequest req, String data) {
		User currentUser = getCurrentUser(req);
		Map<String, String> map = new HashMap<String, String>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			map = objectMapper.readValue(data, HashMap.class);
			Operation operation = DBManager.getInstance().createOperation(currentUser, currentUser, new BigDecimal(map.get("amount")), currentUser);
			if (operation != null) return Response.ok(operation).build();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Response.serverError().build();
	}
	
	/**
	 * @param data
	 * 	{
	 * 		"fromAccountNumber": "A000000000",
	 * 		"toAccountNumber": "A999999999",
	 * 		"amount": "100.0"
	 * 	}
	 */
	@SuppressWarnings("unchecked")
	@Path("/transfer")
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response transfer(@Context HttpServletRequest req, String data) {
		User currentUser = getCurrentUser(req);
		Map<String, String> map = new HashMap<String, String>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			map = objectMapper.readValue(data, HashMap.class);
			Operation operation = DBManager.getInstance().createOperation(map.get("fromAccountNumber"), map.get("toAccountNumber"), new BigDecimal(map.get("amount")), currentUser);
			if (operation != null) return Response.ok(operation).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Response.serverError().build();
	}
}