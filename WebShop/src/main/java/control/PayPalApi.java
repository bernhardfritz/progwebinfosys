package control;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import model.ShoppingCart;
import model.ShoppingCartItem;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentReq;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsReq;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DoExpressCheckoutPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;

@Path("/paypal")
public class PayPalApi {
	
	private final String USERNAME = "g2-business_api1.webinfo.at";
	private final String PASSWORD = "6FVNYD7JDFXYMT5A";
	private final String SIGNATURE = "AQU0e5vuZCvSg-XJploSa.sGUDlpAzTv8oRpQbjBUkP-rfF44elunZS7";
		
	@Path("/startTransaction")
	@GET()
	public Response startTransaction(@Context HttpServletRequest req) {
		HttpSession session = req.getSession();
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		if(shoppingCart == null) session.setAttribute("shoppingCart", new ShoppingCart());
		
		PaymentDetailsType paymentDetails = new PaymentDetailsType();
		paymentDetails.setPaymentAction(PaymentActionCodeType.fromValue("Sale"));
		
		
		List<PaymentDetailsItemType> lineItems = new ArrayList<PaymentDetailsItemType>();
		Double total = new Double(0.0);
		
		for (ShoppingCartItem sci : shoppingCart.getContent()) {
			PaymentDetailsItemType item = new PaymentDetailsItemType();
			BasicAmountType amt = new BasicAmountType();
			amt.setCurrencyID(CurrencyCodeType.fromValue("EUR"));
			amt.setValue(sci.getItem().getPrice().toString());
			item.setQuantity(sci.getQuantity());
			item.setName(sci.getItem().getTitle());
			item.setAmount(amt);
			item.setDescription(sci.getItem().getDescription());
			total += (sci.getItem().getPrice().doubleValue() * sci.getQuantity());
			
			lineItems.add(item);
		}
		
		paymentDetails.setPaymentDetailsItem(lineItems);
		BasicAmountType orderTotal = new BasicAmountType();
		orderTotal.setCurrencyID(CurrencyCodeType.fromValue("EUR"));
		orderTotal.setValue(total.toString()); 
		paymentDetails.setOrderTotal(orderTotal);
		List<PaymentDetailsType> paymentDetailsList = new ArrayList<PaymentDetailsType>();
		paymentDetailsList.add(paymentDetails);
		
		SetExpressCheckoutRequestDetailsType setExpressCheckoutRequestDetails = new SetExpressCheckoutRequestDetailsType();
		setExpressCheckoutRequestDetails.setReturnURL("http://localhost:8080/WebShop/api/paypal/commitTransaction/?total=" + total);
		setExpressCheckoutRequestDetails.setCancelURL("http://www.google.at");
		
		setExpressCheckoutRequestDetails.setPaymentDetails(paymentDetailsList);
		
		SetExpressCheckoutRequestType setExpressCheckoutRequest = new SetExpressCheckoutRequestType(setExpressCheckoutRequestDetails);
		setExpressCheckoutRequest.setVersion("104.0");
		
		SetExpressCheckoutReq setExpressCheckoutReq = new SetExpressCheckoutReq();
		setExpressCheckoutReq.setSetExpressCheckoutRequest(setExpressCheckoutRequest);
		
		Map<String, String> sdkConfig = new HashMap<String, String>();
		sdkConfig.put("mode", "sandbox");
		sdkConfig.put("acct1.UserName", USERNAME);
		sdkConfig.put("acct1.Password", PASSWORD);
		sdkConfig.put("acct1.Signature", SIGNATURE);
		PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(sdkConfig);
		
		try {
			SetExpressCheckoutResponseType setExpressCheckoutResponse = service.setExpressCheckout(setExpressCheckoutReq);
			for (ErrorType error : setExpressCheckoutResponse.getErrors()) {
				System.err.println("startTransaction: " + error.getLongMessage());
			}
			URI uri = new URI("https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + setExpressCheckoutResponse.getToken());
			return Response.seeOther(uri).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Path("/commitTransaction")
	@GET()
	public Response commitTransaction(@Context HttpServletRequest req) {
		Double total = Double.parseDouble(req.getParameter("total"));
		String token = req.getParameter("token");
		String payerId = req.getParameter("PayerID");
		
		GetExpressCheckoutDetailsRequestType getExpressCheckoutDetailsRequest = new GetExpressCheckoutDetailsRequestType(token);
		getExpressCheckoutDetailsRequest.setVersion("104.0");

		GetExpressCheckoutDetailsReq getExpressCheckoutDetailsReq = new GetExpressCheckoutDetailsReq();
		getExpressCheckoutDetailsReq.setGetExpressCheckoutDetailsRequest(getExpressCheckoutDetailsRequest);

		Map<String, String> sdkConfig = new HashMap<String, String>();
		sdkConfig.put("mode", "sandbox");
		sdkConfig.put("acct1.UserName", USERNAME);
		sdkConfig.put("acct1.Password", PASSWORD);
		sdkConfig.put("acct1.Signature",SIGNATURE);
		PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(sdkConfig);
		try {
			GetExpressCheckoutDetailsResponseType getExpressCheckoutDetailsResponse = service.getExpressCheckoutDetails(getExpressCheckoutDetailsReq);
			for (ErrorType error : getExpressCheckoutDetailsResponse.getErrors()) {
				System.err.println("commitTransaction: " + error.getLongMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		PaymentDetailsType paymentDetail = new PaymentDetailsType();
		paymentDetail.setNotifyURL("http://replaceIpnUrl.com");
		BasicAmountType orderTotal = new BasicAmountType();
		orderTotal.setValue(total.toString());
		orderTotal.setCurrencyID(CurrencyCodeType.fromValue("EUR"));
		paymentDetail.setOrderTotal(orderTotal);
		paymentDetail.setPaymentAction(PaymentActionCodeType.fromValue("Sale"));
		List<PaymentDetailsType> paymentDetails = new ArrayList<PaymentDetailsType>();
		paymentDetails.add(paymentDetail);
						
		DoExpressCheckoutPaymentRequestDetailsType doExpressCheckoutPaymentRequestDetails = new DoExpressCheckoutPaymentRequestDetailsType();
		doExpressCheckoutPaymentRequestDetails.setToken(token);
		doExpressCheckoutPaymentRequestDetails.setPayerID(payerId);
		doExpressCheckoutPaymentRequestDetails.setPaymentDetails(paymentDetails);

		DoExpressCheckoutPaymentRequestType doExpressCheckoutPaymentRequest = new DoExpressCheckoutPaymentRequestType(doExpressCheckoutPaymentRequestDetails);
		doExpressCheckoutPaymentRequest.setVersion("104.0");

		DoExpressCheckoutPaymentReq doExpressCheckoutPaymentReq = new DoExpressCheckoutPaymentReq();
		doExpressCheckoutPaymentReq.setDoExpressCheckoutPaymentRequest(doExpressCheckoutPaymentRequest);
		
		try {
			DoExpressCheckoutPaymentResponseType doExpressCheckoutPaymentResponse = service.doExpressCheckoutPayment(doExpressCheckoutPaymentReq);
			for (ErrorType error : doExpressCheckoutPaymentResponse.getErrors()) {
				System.err.println("commitTransaction: " + error.getLongMessage());
			}
			System.out.println("Transaktion erfolgreich!");
			return Response.seeOther(new URI("http://localhost:8080/WebShop/")).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}