package unit.model;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Privileges;

public class PrivilegesTest {

	@Test
	public void testCategoryReadOnly() {
		Privileges privileges = new Privileges(100000000000L);
		
		assertTrue(privileges.isCategoryRead());
		assertFalse(privileges.isCategoryWrite());
		assertFalse(privileges.isCategoryDelete());
	}
	
	@Test
	public void testCategoryWriteOnly() {
		Privileges privileges = new Privileges(10000000000L);
		
		assertFalse(privileges.isCategoryRead());
		assertTrue(privileges.isCategoryWrite());
		assertFalse(privileges.isCategoryDelete());
	}
	
	@Test
	public void testCategoryDeleteOnly() {
		Privileges privileges = new Privileges(1000000000L);
		
		assertFalse(privileges.isCategoryRead());
		assertFalse(privileges.isCategoryWrite());
		assertTrue(privileges.isCategoryDelete());
	}
	
	@Test
	public void testItemReadOnly() {
		Privileges privileges = new Privileges(100000000L);
		
		assertTrue(privileges.isItemRead());
		assertFalse(privileges.isItemWrite());
		assertFalse(privileges.isItemDelete());
	}
	
	@Test
	public void testItemWriteOnly() {
		Privileges privileges = new Privileges(10000000L);
		
		assertFalse(privileges.isItemRead());
		assertTrue(privileges.isItemWrite());
		assertFalse(privileges.isItemDelete());
	}
	
	@Test
	public void testItemDeleteOnly() {
		Privileges privileges = new Privileges(1000000L);
		
		assertFalse(privileges.isItemRead());
		assertFalse(privileges.isItemWrite());
		assertTrue(privileges.isItemDelete());
	}
	
	@Test
	public void testItemCommentReadOnly() {
		Privileges privileges = new Privileges(100000L);
		
		assertTrue(privileges.isItemCommentRead());
		assertFalse(privileges.isItemCommentWrite());
		assertFalse(privileges.isItemCommentDelete());
	}
	
	@Test
	public void testItemCommentWriteOnly() {
		Privileges privileges = new Privileges(10000L);
		
		assertFalse(privileges.isItemCommentRead());
		assertTrue(privileges.isItemCommentWrite());
		assertFalse(privileges.isItemCommentDelete());
	}
	
	@Test
	public void testItemCommentDeleteOnly() {
		Privileges privileges = new Privileges(1000L);
		
		assertFalse(privileges.isItemCommentRead());
		assertFalse(privileges.isItemCommentWrite());
		assertTrue(privileges.isItemCommentDelete());
	}
	
	@Test
	public void testUserPromoteOnly() {
		Privileges privileges = new Privileges(100L);
		
		assertTrue(privileges.isUserPromote());
		assertFalse(privileges.isUserDemote());
		assertFalse(privileges.isUserDelete());
	}
	
	@Test
	public void testUserDemoteOnly() {
		Privileges privileges = new Privileges(10L);
		
		assertFalse(privileges.isUserPromote());
		assertTrue(privileges.isUserDemote());
		assertFalse(privileges.isUserDelete());
	}
	
	@Test
	public void testUserDeleteOnly() {
		Privileges privileges = new Privileges(1L);
		
		assertFalse(privileges.isUserPromote());
		assertFalse(privileges.isUserDemote());
		assertTrue(privileges.isUserDelete());
	}
	
	@Test
	public void testAllReadOnly() {
		Privileges privileges = new Privileges(100100100000L);
		
		assertTrue(privileges.isCategoryRead());
		assertTrue(privileges.isItemRead());
		assertTrue(privileges.isItemCommentRead());
		assertFalse(privileges.isCategoryWrite());
		assertFalse(privileges.isItemWrite());
		assertFalse(privileges.isItemCommentWrite());
		assertFalse(privileges.isCategoryDelete());
		assertFalse(privileges.isItemDelete());
		assertFalse(privileges.isItemCommentDelete());
	}
	
	@Test
	public void testAllWriteOnly() {
		Privileges privileges = new Privileges(10010010000L);
		
		assertFalse(privileges.isCategoryRead());
		assertFalse(privileges.isItemRead());
		assertFalse(privileges.isItemCommentRead());
		assertTrue(privileges.isCategoryWrite());
		assertTrue(privileges.isItemWrite());
		assertTrue(privileges.isItemCommentWrite());
		assertFalse(privileges.isCategoryDelete());
		assertFalse(privileges.isItemDelete());
		assertFalse(privileges.isItemCommentDelete());
	}
	
	@Test
	public void testAllDeleteOnly() {
		Privileges privileges = new Privileges(1001001001L);
		
		assertFalse(privileges.isCategoryRead());
		assertFalse(privileges.isItemRead());
		assertFalse(privileges.isItemCommentRead());
		assertFalse(privileges.isCategoryWrite());
		assertFalse(privileges.isItemWrite());
		assertFalse(privileges.isItemCommentWrite());
		assertTrue(privileges.isCategoryDelete());
		assertTrue(privileges.isItemDelete());
		assertTrue(privileges.isItemCommentDelete());
		assertTrue(privileges.isUserDelete());
	}
}