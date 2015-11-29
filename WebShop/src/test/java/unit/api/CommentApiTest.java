package unit.api;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

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

import control.CommentApi;
import control.DBManager;
import control.IDBManager;
import model.Category;
import model.Item;
import model.ItemComment;
import model.User;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(DBManager.class)
public class CommentApiTest {
	
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
		
		Category cat1 = new Category("Category1", "", null, null);
		cat1.setId(1L);
		
		Item item1 = new Item(cat1, "Item1", "", new BigDecimal(11.11), null, null);
		item1.setId(1L);
		
		ItemComment itCo1 = new ItemComment(item1, "ItemComment1", 1, null, null);
		itCo1.setId(1L);
		
		PowerMockito.when(dbManager.getItemComment(1L)).thenReturn(itCo1);
	}
	
	@Test
	public void testGetCategory() {
		CommentApi commentApi = new CommentApi();
		Response response = commentApi.getComment(1L);
		ItemComment result = (ItemComment)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(new Long(1), result.getId());
		assertEquals(new Long(1), result.getItem().getId());
		assertEquals("ItemComment1", result.getText());
		assertEquals(new Integer(1), result.getRating());
		assertNull(result.getCreateUser());
		assertNotNull(result.getCreateTimestamp());
		assertNull(result.getUpdateUser());
		assertNotNull(result.getUpdateTimestamp());
	}
	
	@Test
	public void testAuthorizedPutComment() {
		User adminUser = new User("admin", "", true, true, true, true, true, true, true, true, true, false, false, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(adminUser);
		
		Item item = new Item();
		
		ItemComment itemComment = new ItemComment(item, "itemComment", 1, adminUser, adminUser);
		itemComment.setId(1L);
		ItemComment itemCommentEdit = new ItemComment(item, "itemCommentEdit", 2, itemComment.getCreateUser(), adminUser);
		itemCommentEdit.setId(itemComment.getId());
		PowerMockito.when(dbManager.editItemComment(itemComment.getId(), itemCommentEdit.getText(), itemCommentEdit.getRating(), adminUser)).thenReturn(itemCommentEdit);
		
		CommentApi commentApi = new CommentApi();
		Response response = null;
		try {
			response = commentApi.putComment(req, itemComment.getId(), itemCommentEdit.getText(), itemCommentEdit.getRating());
		} catch (UnsupportedEncodingException e) {
			fail("ItemComment Put Error:");
			e.printStackTrace();
		}
		ItemComment result = (ItemComment)response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(itemCommentEdit.getId(), result.getId());
		assertEquals(itemCommentEdit.getItem(), item);
		assertEquals(itemCommentEdit.getText(), result.getText());
		assertEquals(itemCommentEdit.getRating(), result.getRating());
		assertEquals(itemCommentEdit.getCreateUser(), result.getCreateUser());
		assertNotNull(result.getCreateTimestamp());
		assertEquals(itemCommentEdit.getUpdateUser(), result.getUpdateUser());
		assertNotNull(result.getUpdateTimestamp());
	}
	
	@Test
	public void testUnauthorizedPutComment() {
		User unauthorizedUser = new User("unauthorized", "", true, true, true, true, true, true, true, false, true, false, false, false);
		PowerMockito.when(session.getAttribute("user")).thenReturn(unauthorizedUser);
		
		CommentApi commentApi = new CommentApi();
		Response response = null;
		try {
			response = commentApi.putComment(req, 1L, "itemComment", 1);
		} catch (UnsupportedEncodingException e) {
			fail("ItemComment Put Error:");
			e.printStackTrace();
		}
		ItemComment result = (ItemComment)response.getEntity();
		
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
		assertNull(result);
	}
	
	@Test
	public void testAuthorizedDeleteComment() {
		User adminUser = new User("admin", "", true, true, true, true, true, true, true, true, true, false, false, true);
		PowerMockito.when(session.getAttribute("user")).thenReturn(adminUser);
		
		PowerMockito.when(dbManager.deleteItemComment(1L)).thenReturn(true);
		
		CommentApi commentApi = new CommentApi();
		Response response = commentApi.deleteComment(1L);
		
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testUnauthorizedDeleteComment() {
		User unauthorizedUser = new User("unauthorized", "", true, true, true, true, true, true, true, true, false, false, false, false);
		PowerMockito.when(session.getAttribute("user")).thenReturn(unauthorizedUser);
		
		CommentApi commentApi = new CommentApi();
		Response response = commentApi.deleteComment(1L);
		
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
	}
}