package unit.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

import model.Category;
import model.Item;
import model.User;

public class ItemTest {

	@Test
	public void testConstructor() {
		Item item = new Item();
		
		assertNull(item.getId());
		assertNull(item.getTitle());
		assertNull(item.getDescription());
		assertNull(item.getCategory());
		assertNull(item.getPrice());
		assertNull(item.getCreateUser());
		assertNull(item.getCreateTimestamp());
		assertNull(item.getUpdateUser());
		assertNull(item.getUpdateTimestamp());
	}
	
	@Test
	public void testConstructorWithFields() {
		Category category = new Category();
		User user = new User();
		Item item = new Item(category, "item", "desc", new BigDecimal(11.11), user, user);
		
		assertNull(item.getId());
		assertEquals(category, item.getCategory());
		assertEquals("item", item.getTitle());
		assertEquals("desc", item.getDescription());
		assertEquals(new BigDecimal(11.11), item.getPrice());
		assertEquals(user, item.getCreateUser());
		assertNotNull(item.getCreateTimestamp());
		assertEquals(user, item.getUpdateUser());
		assertNotNull(item.getUpdateTimestamp());
	}
	
	@Test
	public void testGetterAndSetter() {
		Category category = new Category();
		User user = new User();
		Item item = new Item();
		Timestamp timestamp = new Timestamp(new Date().getTime());
		
		item.setId(1L);
		item.setCategory(category);
		item.setTitle("item");
		item.setDescription("desc");
		item.setPrice(new BigDecimal(11.11));
		item.setCreateUser(user);
		item.setCreateTimestamp(timestamp);
		item.setUpdateUser(user);
		item.setUpdateTimestamp(timestamp);
		
		assertEquals(new Long(1), item.getId());
		assertEquals(category, item.getCategory());
		assertEquals("item", item.getTitle());
		assertEquals("desc", item.getDescription());
		assertEquals(new BigDecimal(11.11), item.getPrice());
		assertEquals(user, item.getCreateUser());
		assertEquals(timestamp, item.getCreateTimestamp());
		assertEquals(user, item.getUpdateUser());
		assertEquals(timestamp, item.getUpdateTimestamp());
	}
}