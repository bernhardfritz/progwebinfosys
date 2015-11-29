package unit.api;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import control.DBManager;
import control.IDBManager;
import control.UserApi;
import model.User;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(DBManager.class)
public class UserApiTest {
	
	private IDBManager dbManager;
	
	private HttpServletRequest req;
	
	private HttpSession session;

	@Before
	public void setUp() {
		dbManager = PowerMockito.mock(DBManager.class);
		req = PowerMockito.mock(HttpServletRequest.class);
		session = PowerMockito.mock(HttpSession.class);
		
		PowerMockito.mockStatic(DBManager.class);
		PowerMockito.when(DBManager.getInstance()).thenReturn(dbManager);
		
		PowerMockito.when(req.getSession()).thenReturn(session);
		
		User user1 = new User("admin", "adminpwd", true, true, true, true, true, true, true, true, true, false, false, true);
		user1.setId(1L);
		
		List<User> userList = new ArrayList<User>();
		userList.add(user1);
		userList.add(new User("user", "userpwd", true, false, false, true, false, false, true, true, false, false, false, false));
		userList.add(new User("guest", "guestpwd", true, false, false, true, false, false, true, false, false, false, false, false));
		
		PowerMockito.when(dbManager.getUsers()).thenReturn(userList);
		PowerMockito.when(dbManager.getUserById(1L)).thenReturn(user1);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetUsers() {
		UserApi userApi = new UserApi();
		Response response = userApi.getUsers();
		List<User> result = (List<User>)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(3, result.size());
		assertEquals(new Long(1), result.get(0).getId());
		assertEquals("admin", result.get(0).getUsername());
		assertEquals("adminpwd", result.get(0).getPassword());
		assertNotNull(result.get(0).getCreateTimestamp());
	}
	
	@Test
	public void testGetUser() {
		UserApi userApi = new UserApi();
		Response response = userApi.getUser(1L);
		User result = (User)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(new Long(1), result.getId());
		assertEquals("admin", result.getUsername());
		assertEquals("adminpwd", result.getPassword());
		assertNotNull(result.getCreateTimestamp());
	}
	
	@Test
	public void testSuccessfulPostUser() {
		User user = new User("user", "pwd", true, false, false, true, false, false, true, true, false, false, false, false);
		PowerMockito.when(dbManager.createUser(user.getUsername(), user.getPassword(), 100100110000L)).thenReturn(user);
		
		UserApi userApi = new UserApi();
		Response response = null;
		try {
			response = userApi.postUser(user.getUsername(), user.getPassword());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		User result = (User)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(user.getId(), result.getId());
		assertEquals(user.getUsername(), result.getUsername());
		assertEquals(user.getPassword(), result.getPassword());
		assertEquals(user.isCategoryRead(), result.isCategoryRead());
		assertEquals(user.isCategoryWrite(), result.isCategoryWrite());
		assertEquals(user.isCategoryDelete(), result.isCategoryDelete());
		assertEquals(user.isItemRead(), result.isItemRead());
		assertEquals(user.isItemWrite(), result.isItemWrite());
		assertEquals(user.isItemDelete(), result.isItemDelete());
		assertEquals(user.isItemCommentRead(), result.isItemCommentRead());
		assertEquals(user.isItemCommentWrite(), result.isItemCommentWrite());
		assertEquals(user.isItemCommentDelete(), result.isItemCommentDelete());
		assertNotNull(result.getCreateTimestamp());
	}
	
	@Test
	public void testFailPostUser() {
		UserApi userApi = new UserApi();
		Response response = null;
		try {
			response = userApi.postUser("", "");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testAuthorizedPutUser() {
		User user = new User("user", "pwd", true, false, false, true, false, false, true, true, false, false, false, false);
		user.setId(1L);
		User userEdit = new User(user.getUsername(), "pwdEdit", false, true, true, false, true, true, false, false, true, false, false, false);
		userEdit.setId(user.getId());
		PowerMockito.when(dbManager.editUser(user.getId(), userEdit.getPassword(), 11011001000L)).thenReturn(userEdit);
		
		UserApi userApi = new UserApi();
		Response response = userApi.putUser(user.getId(), userEdit.getPassword(), 11011001000L);
		User result = (User)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(user.getId(), result.getId());
		assertEquals(userEdit.getUsername(), result.getUsername());
		assertEquals(userEdit.getPassword(), result.getPassword());
		assertEquals(userEdit.isCategoryRead(), result.isCategoryRead());
		assertEquals(userEdit.isCategoryWrite(), result.isCategoryWrite());
		assertEquals(userEdit.isCategoryDelete(), result.isCategoryDelete());
		assertEquals(userEdit.isItemRead(), result.isItemRead());
		assertEquals(userEdit.isItemWrite(), result.isItemWrite());
		assertEquals(userEdit.isItemDelete(), result.isItemDelete());
		assertEquals(userEdit.isItemCommentRead(), result.isItemCommentRead());
		assertEquals(userEdit.isItemCommentWrite(), result.isItemCommentWrite());
		assertEquals(userEdit.isItemCommentDelete(), result.isItemCommentDelete());
		assertNotNull(result.getCreateTimestamp());
	}
	
	@Test
	public void testUnauthorizedPutUser() {
		UserApi userApi = new UserApi();
		Response response = userApi.putUser(1L, "", 0L);
		User result = (User)response.getEntity();
		
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
		assertNull(result);
	}
	
	@Test
	public void testAuthorizedDeleteUser() {
		PowerMockito.when(dbManager.deleteUser(1L)).thenReturn(true);
		
		UserApi userApi = new UserApi();
		Response response = userApi.deleteUser(1L);
		
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testUnauthorizedDeleteUser() {
		PowerMockito.when(dbManager.deleteUser(1L)).thenReturn(false);
		
		UserApi userApi = new UserApi();
		Response response = userApi.deleteUser(1L);
		
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testSuccessfulLogin() {
		User user = new User("user", "pwd", true, false, false, true, false, false, true, true, false, false, false, false);
		PowerMockito.when(dbManager.login(user.getUsername(), user.getPassword())).thenReturn(user);
		
		UserApi userApi = new UserApi();
		Response response = userApi.loginUser(req, user.getUsername(), user.getPassword());
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testFailLogin() {
		PowerMockito.when(dbManager.login("user", "abc")).thenReturn(null);
		
		UserApi userApi = new UserApi();
		Response response = userApi.loginUser(req, "user", "abc");
		
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testSuccessfulLogout() {
		User guestUser = new User("guest", "pwd", true, false, false, true, false, false, true, false, false, false, false, false);
		PowerMockito.when(dbManager.logout()).thenReturn(guestUser);
		
		UserApi userApi = new UserApi();
		Response response = userApi.logoutUser(req);
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testFailLogout() {
		PowerMockito.when(dbManager.logout()).thenReturn(null);
		
		UserApi userApi = new UserApi();
		Response response = userApi.logoutUser(req);
		
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
	}
}