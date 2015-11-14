package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
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
import control.ItemApi;
import model.Category;
import model.Item;
import model.ItemComment;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(DBManager.class)
public class ItemApiTest {
	
	private IDBManager dbManager;

	@Before
	public void setUp() {
		dbManager = PowerMockito.mock(DBManager.class);
		
		PowerMockito.mockStatic(DBManager.class);
		PowerMockito.when(DBManager.getInstance()).thenReturn(dbManager);
		
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
		
		assertEquals(200, response.getStatus());
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
		
		assertEquals(200, response.getStatus());
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
		
		assertEquals(200, response.getStatus());
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
}