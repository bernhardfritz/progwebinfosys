package unit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

import model.Category;
import model.User;

public class CategoryTest {

	@Test
	public void testConstructor() {
		Category category = new Category();
		
		assertNull(category.getId());
		assertNull(category.getName());
		assertNull(category.getDescription());
		assertNull(category.getCreateUser());
		assertNull(category.getCreateTimestamp());
		assertNull(category.getUpdateUser());
		assertNull(category.getUpdateTimestamp());
	}
	
	@Test
	public void testConstructorWithFields() {
		User user = new User();
		Category category = new Category("category", "desc", user, user);
		
		assertNull(category.getId());
		assertEquals("category", category.getName());
		assertEquals("desc", category.getDescription());
		assertEquals(user, category.getCreateUser());
		assertNotNull(category.getCreateTimestamp());
		assertEquals(user, category.getUpdateUser());
		assertNotNull(category.getUpdateTimestamp());
	}
	
	@Test
	public void testGetterAndSetter() {
		User user = new User();
		Category category = new Category();
		Timestamp timestamp = new Timestamp(new Date().getTime());
		
		category.setId(1L);
		category.setName("category");
		category.setDescription("desc");
		category.setCreateUser(user);
		category.setCreateTimestamp(timestamp);
		category.setUpdateUser(user);
		category.setUpdateTimestamp(timestamp);
		
		assertEquals(new Long(1), category.getId());
		assertEquals("category", category.getName());
		assertEquals("desc", category.getDescription());
		assertEquals(user, category.getCreateUser());
		assertEquals(timestamp, category.getCreateTimestamp());
		assertEquals(user, category.getUpdateUser());
		assertEquals(timestamp, category.getUpdateTimestamp());
	}
}