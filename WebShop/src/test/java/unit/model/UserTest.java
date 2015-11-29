package unit.model;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

import model.User;

public class UserTest {

	@Test
	public void testConstructor() {
		User user = new User();
		
		assertNull(user.getId());
		assertNull(user.getUsername());
		assertNull(user.getPassword());
		assertNull(user.getCreateTimestamp());
	}
	
	@Test
	public void testConstructorWithFields() {
		User user = new User("user", "pwd", true, false, true, false, true, false, true, false, true, false, true, false);
		
		assertNull(user.getId());
		assertEquals("user", user.getUsername());
		assertEquals("pwd", user.getPassword());
		assertTrue(user.isCategoryRead());
		assertFalse(user.isCategoryWrite());
		assertTrue(user.isCategoryDelete());
		assertFalse(user.isItemRead());
		assertTrue(user.isItemWrite());
		assertFalse(user.isItemDelete());
		assertTrue(user.isItemCommentRead());
		assertFalse(user.isItemCommentWrite());
		assertTrue(user.isItemCommentDelete());
		assertFalse(user.isUserPromote());
		assertTrue(user.isUserDemote());
		assertFalse(user.isUserDelete());
		assertNotNull(user.getCreateTimestamp());
	}
	
	@Test
	public void testGetterAndSetter() {
		User user = new User();
		Timestamp timestamp = new Timestamp(new Date().getTime());
		
		user.setId(1L);
		user.setUsername("user");
		user.setPassword("pwd");
		user.setCategoryRead(true);
		user.setCategoryWrite(false);
		user.setCategoryDelete(true);
		user.setItemRead(false);
		user.setItemWrite(true);
		user.setItemDelete(false);
		user.setItemCommentRead(true);
		user.setItemCommentWrite(false);
		user.setItemCommentDelete(true);
		user.setUserPromote(false);
		user.setUserDemote(true);
		user.setUserDelete(false);
		user.setCreateTimestamp(timestamp);
		
		assertEquals(new Long(1), user.getId());
		assertEquals("user", user.getUsername());
		assertEquals("pwd", user.getPassword());
		assertTrue(user.isCategoryRead());
		assertFalse(user.isCategoryWrite());
		assertTrue(user.isCategoryDelete());
		assertFalse(user.isItemRead());
		assertTrue(user.isItemWrite());
		assertFalse(user.isItemDelete());
		assertTrue(user.isItemCommentRead());
		assertFalse(user.isItemCommentWrite());
		assertTrue(user.isItemCommentDelete());
		assertFalse(user.isUserPromote());
		assertTrue(user.isUserDemote());
		assertFalse(user.isUserDelete());
		assertEquals(timestamp, user.getCreateTimestamp());
	}
}