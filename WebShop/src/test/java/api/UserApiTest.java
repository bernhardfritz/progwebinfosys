package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

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

	@Before
	public void setUp() {
		dbManager = PowerMockito.mock(DBManager.class);
		
		PowerMockito.mockStatic(DBManager.class);
		PowerMockito.when(DBManager.getInstance()).thenReturn(dbManager);
		
		User user1 = new User("admin", "adminpwd", true, true, true, true, true, true, true, true, true);
		user1.setId(1L);
		
		List<User> userList = new ArrayList<User>();
		userList.add(user1);
		userList.add(new User("user", "userpwd", true, false, false, true, false, false, true, true, false));
		userList.add(new User("guest", "guestpwd", true, false, false, true, false, false, true, false, false));
		
		PowerMockito.when(dbManager.getUsers()).thenReturn(userList);
		PowerMockito.when(dbManager.getUserById(1L)).thenReturn(user1);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetUsers() {
		UserApi userApi = new UserApi();
		Response response = userApi.getUsers();
		List<User> result = (List<User>)response.getEntity();
		
		assertEquals(200, response.getStatus());
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
		
		assertEquals(200, response.getStatus());
		assertEquals(new Long(1), result.getId());
		assertEquals("admin", result.getUsername());
		assertEquals("adminpwd", result.getPassword());
		assertNotNull(result.getCreateTimestamp());
	}
}