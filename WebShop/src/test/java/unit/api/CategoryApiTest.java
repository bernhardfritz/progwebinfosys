package unit.api;

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

import control.CategoryApi;
import control.DBManager;
import control.IDBManager;
import model.Category;
import model.Item;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(DBManager.class)
public class CategoryApiTest {
	
	private IDBManager dbManager;

	@Before
	public void setUp() {
		dbManager = PowerMockito.mock(DBManager.class);
		
		PowerMockito.mockStatic(DBManager.class);
		PowerMockito.when(DBManager.getInstance()).thenReturn(dbManager);
		
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
		
		assertEquals(200, response.getStatus());
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
		
		assertEquals(200, response.getStatus());
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
		
		assertEquals(200, response.getStatus());
		assertEquals(3, result.size());
		assertEquals(new Long(1), result.get(0).getCategory().getId());
		assertEquals("Item1", result.get(0).getTitle());
		assertEquals("desc", result.get(2).getDescription());
		assertNull(result.get(1).getCreateUser());
		assertNotNull(result.get(1).getCreateTimestamp());
		assertNull(result.get(1).getUpdateUser());
		assertNotNull(result.get(1).getUpdateTimestamp());
	}
}