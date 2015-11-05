package control;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloWorld {
	@GET()
	public String hello() {
		return "Hello World!";
	}
}
