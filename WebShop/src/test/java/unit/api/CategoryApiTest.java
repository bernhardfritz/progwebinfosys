package unit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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

import control.CategoryApi;
import control.DBManager;
import control.IDBManager;
import model.Category;
import model.Item;
import model.User;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(DBManager.class)
public class CategoryApiTest {
	
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
		
		Category cat1 = new Category("Kategorie1", "", null, null);
		cat1.setId(1L);
		
		List<Category> categoryList = new ArrayList<Category>();
		categoryList.add(cat1);
		categoryList.add(new Category("Kategorie2", "desc", null, null));
		categoryList.add(new Category("Kategorie3", "", null, null));
		
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(cat1, "Item1", "", new BigDecimal(11.11), null, null));
		itemList.add(new Item(cat1, "Item2", "", new BigDecimal(22.22), null, null));
		itemList.add(new Item(cat1, "Item3", "desc", new BigDecimal(33.33), null, null));
		
		PowerMockito.when(dbManager.getCategories()).thenReturn(categoryList);
		PowerMockito.when(dbManager.getCategoryById(1L)).thenReturn(categoryList.get(0));
		PowerMockito.when(dbManager.getItems(1L)).thenReturn(itemList);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetCategories() {
		CategoryApi categoryApi = new CategoryApi();
		Response response = categoryApi.getCategories();
		List<Category> result = (List<Category>)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(3, result.size());
		assertEquals(new Long(1), result.get(0).getId());
		assertEquals("Kategorie1", result.get(0).getName());
		assertEquals("desc", result.get(1).getDescription());
		assertNull(result.get(2).getCreateUser());
		assertNotNull(result.get(2).getCreateTimestamp());
		assertNull(result.get(2).getUpdateUser());
		assertNotNull(result.get(2).getUpdateTimestamp());
	}
	
	@Test
	public void testGetCategory() {
		CategoryApi categoryApi = new CategoryApi();
		Response response = categoryApi.getCategory(1L);
		Category result = (Category)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(new Long(1), result.getId());
		assertEquals("Kategorie1", result.getName());
		assertEquals("", result.getDescription());
		assertNull(result.getCreateUser());
		assertNotNull(result.getCreateTimestamp());
		assertNull(result.getUpdateUser());
		assertNotNull(result.getUpdateTimestamp());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetItems() {
		CategoryApi categoryApi = new CategoryApi();
		Response response = categoryApi.getItems(1L);
		List<Item> result = (List<Item>)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(3, result.size());
		assertEquals(new Long(1), result.get(0).getCategory().getId());
		assertEquals("Item1", result.get(0).getTitle());
		assertEquals("desc", result.get(2).getDescription());
		assertNull(result.get(1).getCreateUser());
		assertNotNull(result.get(1).getCreateTimestamp());
		assertNull(result.get(1).getUpdateUser());
		assertNotNull(result.get(1).getUpdateTimestamp());
	}
	
	@Test
	public void testAuthorizedPostCategory() {
		User adminUser = new User("admin", "", true, true, true, true, true, true, true, true, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(adminUser);
		
		Category category = new Category("category", "desc", adminUser, adminUser);
		PowerMockito.when(dbManager.createCategory(category.getName(), category.getDescription(), category.getCreateUser())).thenReturn(category);
		
		CategoryApi categoryApi = new CategoryApi();
		Response response = null;
		try {
			response = categoryApi.postCategory(req, category.getName(), category.getDescription());
		} catch (Exception e) {
			fail("Category Post Error:");
			e.printStackTrace();
		}
		Category result = (Category)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertNull(result.getId());
		assertEquals(category.getName(), result.getName());
		assertEquals(category.getDescription(), result.getDescription());
		assertEquals(category.getCreateUser(), result.getCreateUser());
		assertNotNull(result.getCreateTimestamp());
		assertEquals(category.getUpdateUser(), result.getUpdateUser());
		assertNotNull(result.getUpdateTimestamp());
	}
	
	@Test
	public void testUnauthorizedPostCategory() {
		User unauthorizedUser = new User("unauthorized", "", true, false, true, true, true, true, true, true, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(unauthorizedUser);
		
		CategoryApi categoryApi = new CategoryApi();
		Response response = null;
		try {
			response = categoryApi.postCategory(req, "category", "desc");
		} catch (Exception e) {
			fail("Category Post Error:");
			e.printStackTrace();
		}
		Category result = (Category)response.getEntity();
		
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
		assertNull(result);
	}
	
	@Test
	public void testAuthorizedPutCategory() {
		User adminUser = new User("admin", "", true, true, true, true, true, true, true, true, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(adminUser);
		
		Category category = new Category("category", "desc", adminUser, adminUser);
		category.setId(1L);
		Category categoryEdit = new Category("categoryEdit", "descEdit", category.getCreateUser(), adminUser);
		categoryEdit.setId(category.getId());
		PowerMockito.when(dbManager.editCategory(category.getId(), categoryEdit.getName(), categoryEdit.getDescription(), adminUser)).thenReturn(categoryEdit);
		
		CategoryApi categoryApi = new CategoryApi();
		Response response = null;
		try {
			response = categoryApi.putCategory(req, category.getId(), categoryEdit.getName(), categoryEdit.getDescription());
		} catch (UnsupportedEncodingException e) {
			fail("Category Put Error:");
			e.printStackTrace();
		}
		Category result = (Category)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(categoryEdit.getId(), result.getId());
		assertEquals(categoryEdit.getName(), result.getName());
		assertEquals(categoryEdit.getDescription(), result.getDescription());
		assertEquals(categoryEdit.getCreateUser(), result.getCreateUser());
		assertNotNull(result.getCreateTimestamp());
		assertEquals(categoryEdit.getUpdateUser(), result.getUpdateUser());
		assertNotNull(result.getUpdateTimestamp());
	}
	
	@Test
	public void testUnauthorizedPutCategory() {
		User unauthorizedUser = new User("unauthorized", "", true, false, true, true, true, true, true, true, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(unauthorizedUser);
		
		CategoryApi categoryApi = new CategoryApi();
		Response response = null;
		try {
			response = categoryApi.putCategory(req, 1L, "category", "desc");
		} catch (UnsupportedEncodingException e) {
			fail("Category Put Error:");
			e.printStackTrace();
		}
		Category result = (Category)response.getEntity();
		
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
		assertNull(result);
	}
	
	@Test
	public void testAuthorizedDeleteCategory() {
		User adminUser = new User("admin", "", true, true, true, true, true, true, true, true, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(adminUser);
		
		PowerMockito.when(dbManager.deleteCategory(1l)).thenReturn(true);
		
		CategoryApi categoryApi = new CategoryApi();
		Response response = categoryApi.deleteCategory(1l);
		
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testUnauthorizedDeleteCategory() {
		User unauthorizedUser = new User("unauthorized", "", true, true, false, true, true, true, true, true, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(unauthorizedUser);
		
		CategoryApi categoryApi = new CategoryApi();
		Response response = categoryApi.deleteCategory(1L);
		
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
	}
}