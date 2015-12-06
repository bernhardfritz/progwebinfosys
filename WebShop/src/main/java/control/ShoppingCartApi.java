package control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.ShoppingCart;

@Path("/shoppingcart")
public class ShoppingCartApi {
	public ShoppingCart initShoppingCart(HttpSession session) {
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		if(shoppingCart == null) session.setAttribute("shoppingCart", new ShoppingCart());
		return shoppingCart;
	}
	
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShoppingCart(@Context HttpServletRequest req) {
		ShoppingCart shoppingCart = initShoppingCart(req.getSession());
		return Response.ok(shoppingCart).build();
	}
	
	@Path("/{itemId}")
	@POST()
	@Produces(MediaType.APPLICATION_JSON)
	public Response postShoppingCart(@Context HttpServletRequest req, @PathParam("itemId") Long itemId) {
		ShoppingCart shoppingCart = initShoppingCart(req.getSession());
		shoppingCart.addItem(DBManager.getInstance().getItem(itemId));
		return Response.ok(shoppingCart).build();
	}
	
	@Path("/{itemId}/{amount}")
	@PUT()
	@Produces(MediaType.APPLICATION_JSON)
	public Response putShoppingCart(@Context HttpServletRequest req, @PathParam("itemId") Long itemId, @PathParam("amount") int amount) {
		ShoppingCart shoppingCart = initShoppingCart(req.getSession());
		shoppingCart.setQuantity(DBManager.getInstance().getItem(itemId), amount); // ShoppingCartItem is created if it doesn't exist
		return Response.ok(shoppingCart).build();
	}
	
	@Path("/{itemId}")
	@DELETE()
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteShoppingCart(@Context HttpServletRequest req, @PathParam("itemId") Long itemId) {
		ShoppingCart shoppingCart = initShoppingCart(req.getSession());
		shoppingCart.deleteItem(DBManager.getInstance().getItem(itemId));
		return Response.ok(shoppingCart).build();
	}
}
