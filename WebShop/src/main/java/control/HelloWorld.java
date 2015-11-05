package control;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloWorld {
	@GET()
	public String hello() {
		DBManager dbManager = DBManager.getInstance();
 		dbManager.registerUser("admin", "test", true, true, true, true, true, true, true, true, true);
// 		out.println(dbManager.findUserById(1L));
// 		dbManager.disconnect();
//		out.println("Hallo");
		return "Hello World!";
	}
}
