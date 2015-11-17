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

import control.DBManager;
import control.IDBManager;
import control.ItemApi;
import model.Category;
import model.Item;
import model.ItemComment;
import model.User;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(DBManager.class)
public class ItemApiTest {
	
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
		
		Item item1 = new Item(cat1, "Item1", "desc", new BigDecimal(11.11), null, null);
		item1.setId(1L);
		
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(item1);
		itemList.add(new Item(cat1, "Item2", "", new BigDecimal(22.22), null, null));
		itemList.add(new Item(cat1, "Item3", "", new BigDecimal(33.33), null, null));
		
		List<ItemComment> itemCommentList = new ArrayList<ItemComment>();
		itemCommentList.add(new ItemComment(item1, "Comment1", 1, null, null));
		itemCommentList.add(new ItemComment(item1, "Comment2", 2, null, null));
		
		PowerMockito.when(dbManager.getItems()).thenReturn(itemList);
		PowerMockito.when(dbManager.getItem(1L)).thenReturn(item1);
		PowerMockito.when(dbManager.getItemComments(1L)).thenReturn(itemCommentList);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetItems() {
		ItemApi itemApi = new ItemApi();
		Response response = itemApi.getItems();
		List<Item> result = (List<Item>)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(3, result.size());
		assertEquals(new Long(1), result.get(0).getId());
		assertEquals("Item1", result.get(0).getTitle());
		assertEquals("desc", result.get(0).getDescription());
		assertNull(result.get(0).getCreateUser());
		assertNotNull(result.get(0).getCreateTimestamp());
		assertNull(result.get(0).getUpdateUser());
		assertNotNull(result.get(0).getUpdateTimestamp());
	}
	
	@Test
	public void testGetItem() {
		ItemApi itemApi = new ItemApi();
		Response response = itemApi.getItem(1L);
		Item result = (Item)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(new Long(1), result.getId());
		assertEquals("Item1", result.getTitle());
		assertEquals("desc", result.getDescription());
		assertNull(result.getCreateUser());
		assertNotNull(result.getCreateTimestamp());
		assertNull(result.getUpdateUser());
		assertNotNull(result.getUpdateTimestamp());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetItemComments() {
		ItemApi itemApi = new ItemApi();
		Response response = itemApi.getItemComments(1L);
		List<ItemComment> result = (List<ItemComment>)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(2, result.size());
		assertNull(result.get(0).getId());
		assertEquals(new Long(1), result.get(0).getItem().getId());
		assertEquals("Comment1", result.get(0).getText());
		assertEquals(new Integer(1), result.get(0).getRating());
		assertNull(result.get(0).getCreateUser());
		assertNotNull(result.get(0).getCreateTimestamp());
		assertNull(result.get(0).getUpdateUser());
		assertNotNull(result.get(0).getUpdateTimestamp());
	}
	
	@Test
	public void testAuthorizedPostItem() {
		User adminUser = new User("admin", "", true, true, true, true, true, true, true, true, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(adminUser);
		
		Category category = new Category();
		category.setId(1L);
		Item item = new Item(category, "item", "desc", new BigDecimal(11.11), adminUser, adminUser);
		item.setId(1L);
		PowerMockito.when(dbManager.createItem(item.getTitle(), item.getDescription(), item.getPrice(), item.getCategory().getId(), item.getCreateUser())).thenReturn(item);
		
		ItemApi itemApi = new ItemApi();
		Response response = null;
		try {
			response = itemApi.postItem(req, item.getTitle(), item.getDescription(), item.getPrice(), item.getCategory().getId());
		} catch (Exception e) {
			fail("Item Post Error:");
			e.printStackTrace();
		}
		Item result = (Item)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(item.getId(), result.getId());
		assertEquals(item.getCategory(), result.getCategory());
		assertEquals(item.getTitle(), result.getTitle());
		assertEquals(item.getDescription(), result.getDescription());
		assertEquals(item.getPrice(), result.getPrice());
		assertEquals(item.getCreateUser(), result.getCreateUser());
		assertNotNull(result.getCreateTimestamp());
		assertEquals(item.getUpdateUser(), result.getUpdateUser());
		assertNotNull(result.getUpdateTimestamp());
	}
	
	@Test
	public void testUnauthorizedPostItem() {
		User unauthorizedUser = new User("unauthorized", "", true, true, true, true, false, true, true, true, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(unauthorizedUser);
		
		ItemApi itemApi = new ItemApi();
		Response response = null;
		try {
			response = itemApi.postItem(req, "item", "desc", new BigDecimal(11.11), 1L);
		} catch (Exception e) {
			fail("Item Post Error:");
			e.printStackTrace();
		}
		Item result = (Item)response.getEntity();
		
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
		assertNull(result);
	}
	
	@Test
	public void testAuthorizedPutItem() {
		User adminUser = new User("admin", "", true, true, true, true, true, true, true, true, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(adminUser);
		
		Category category = new Category();
		category.setId(1L);
		Item item = new Item(category, "item", "desc", new BigDecimal(11.11), adminUser, adminUser);
		item.setId(1L);
		Item itemEdit = new Item(item.getCategory(), "itemEdit", "descEdit", new BigDecimal(22.22), item.getCreateUser(), item.getUpdateUser());
		itemEdit.setId(item.getId());
		PowerMockito.when(dbManager.editItem(item.getId(), itemEdit.getTitle(), itemEdit.getDescription(), itemEdit.getPrice(), itemEdit.getCategory().getId(), adminUser)).thenReturn(itemEdit);
		
		ItemApi itemApi = new ItemApi();
		Response response = null;
		try {
			response = itemApi.putItem(req, itemEdit.getId(), itemEdit.getTitle(), itemEdit.getDescription(), itemEdit.getPrice(), itemEdit.getCategory().getId());
		} catch (UnsupportedEncodingException e) {
			fail("Item Put Error:");
			e.printStackTrace();
		}
		Item result = (Item)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(itemEdit.getId(), result.getId());
		assertEquals(itemEdit.getCategory(), result.getCategory());
		assertEquals(itemEdit.getTitle(), result.getTitle());
		assertEquals(itemEdit.getDescription(), result.getDescription());
		assertEquals(itemEdit.getPrice(), result.getPrice());
		assertEquals(itemEdit.getCreateUser(), result.getCreateUser());
		assertNotNull(result.getCreateTimestamp());
		assertEquals(itemEdit.getUpdateUser(), result.getUpdateUser());
		assertNotNull(result.getUpdateTimestamp());
	}
	
	@Test
	public void testUnauthorizedPutItem() {
		User unauthorizedUser = new User("unauthorized", "", true, true, true, true, false, true, true, true, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(unauthorizedUser);
		
		ItemApi itemApi = new ItemApi();
		Response response = null;
		try {
			response = itemApi.putItem(req, 1L, "item", "desc", new BigDecimal(11.11), 1L);
		} catch (UnsupportedEncodingException e) {
			fail("Item Put Error:");
			e.printStackTrace();
		}
		Item result = (Item)response.getEntity();
		
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
		assertNull(result);
	}
	
	@Test
	public void testAuthorizedDeleteItem() {
		User adminUser = new User("admin", "", true, true, true, true, true, true, true, true, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(adminUser);
		
		PowerMockito.when(dbManager.deleteItem(1L)).thenReturn(true);
		
		ItemApi itemApi = new ItemApi();
		Response response = itemApi.deleteItem(1L);
		
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testUnauthorizedDeleteItem() {
		User unauthorizedUser = new User("unauthorized", "", true, true, true, true, true, false, true, true, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(unauthorizedUser);
		
		PowerMockito.when(dbManager.deleteItem(1L)).thenReturn(false);
		
		ItemApi itemApi = new ItemApi();
		Response response = itemApi.deleteItem(1L);
		
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testAuthorizedPostItemComment() {
		User adminUser = new User("admin", "", true, true, true, true, true, true, true, true, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(adminUser);
		
		Category category = new Category();
		category.setId(1L);
		Item item = new Item(category, "item", "desc", new BigDecimal(11.11), adminUser, adminUser);
		item.setId(1L);
		ItemComment itemComment = new ItemComment(item, "itemComment", 1, adminUser, adminUser);
		itemComment.setId(1L);
		PowerMockito.when(dbManager.createItemComment(itemComment.getText(), itemComment.getItem().getId(), itemComment.getRating(), itemComment.getCreateUser())).thenReturn(itemComment);
		
		ItemApi itemApi = new ItemApi();
		Response response = null;
		try {
			response = itemApi.postItemComment(req, itemComment.getText(), itemComment.getItem().getId(), itemComment.getRating());
		} catch (Exception e) {
			fail("ItemComment Post Error:");
			e.printStackTrace();
		}
		ItemComment result = (ItemComment)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(itemComment.getId(), result.getId());
		assertEquals(itemComment.getItem(), result.getItem());
		assertEquals(itemComment.getText(), result.getText());
		assertEquals(itemComment.getRating(), result.getRating());
		assertEquals(itemComment.getCreateUser(), result.getCreateUser());
		assertNotNull(itemComment.getCreateTimestamp());
		assertEquals(itemComment.getUpdateUser(), result.getUpdateUser());
		assertNotNull(itemComment.getUpdateTimestamp());
	}
	
	@Test
	public void testUnauthorizedPostItemComment() {
		User unauthorizedUser = new User("unauthorized", "", true, true, true, true, true, true, true, false, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(unauthorizedUser);
		
		ItemApi itemApi = new ItemApi();
		Response response = null;
		try {
			response = itemApi.postItemComment(req, "itemComment", 1L, 1);
		} catch (Exception e) {
			fail("ItemComment Post Error:");
			e.printStackTrace();
		}
		ItemComment result = (ItemComment)response.getEntity();
		
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
		assertNull(result);
	}
}