package unit.model;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Privileges;

public class PrivilegesTest {

	@Test
	public void testCategoryRead() {
		Privileges privileges = new Privileges(100000000);
		
		assertTrue(privileges.isCategoryRead());
	}
	
	@Test
	public void testCategoryWrite() {
		Privileges privileges = new Privileges(10000000);
		
		assertTrue(privileges.isCategoryWrite());
	}
	
	@Test
	public void testCategoryDelete() {
		Privileges privileges = new Privileges(1000000);
		
		assertTrue(privileges.isCategoryDelete());
	}
	
	@Test
	public void testItemRead() {
		Privileges privileges = new Privileges(100000);
		
		assertTrue(privileges.isItemRead());
	}
	
	@Test
	public void testItemWrite() {
		Privileges privileges = new Privileges(10000);
		
		assertTrue(privileges.isItemWrite());
	}
	
	@Test
	public void testItemDelete() {
		Privileges privileges = new Privileges(1000);
		
		assertTrue(privileges.isItemDelete());
	}
	
	@Test
	public void testItemCommentRead() {
		Privileges privileges = new Privileges(100);
		
		assertTrue(privileges.isItemCommentRead());
	}
	
	@Test
	public void testItemCommentWrite() {
		Privileges privileges = new Privileges(10);
		
		assertTrue(privileges.isItemCommentWrite());
	}
	
	@Test
	public void testItemCommentDelete() {
		Privileges privileges = new Privileges(1);
		
		assertTrue(privileges.isItemCommentDelete());
	}
	
	@Test
	public void testAllRead() {
		Privileges privileges = new Privileges(100100100);
		
		assertTrue(privileges.isCategoryRead());
		assertTrue(privileges.isItemRead());
		assertTrue(privileges.isItemCommentRead());
	}
	
	@Test
	public void testAllWrite() {
		Privileges privileges = new Privileges(10010010);
		
		assertTrue(privileges.isCategoryWrite());
		assertTrue(privileges.isItemWrite());
		assertTrue(privileges.isItemCommentWrite());
	}
	
	@Test
	public void testAllDelete() {
		Privileges privileges = new Privileges(1001001);
		
		assertTrue(privileges.isCategoryDelete());
		assertTrue(privileges.isItemDelete());
		assertTrue(privileges.isItemCommentDelete());
	}
}