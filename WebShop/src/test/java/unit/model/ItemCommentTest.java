package unit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

import model.Item;
import model.ItemComment;
import model.User;

public class ItemCommentTest {

	@Test
	public void testConstructor() {
		ItemComment itemComment = new ItemComment();
		
		assertNull(itemComment.getId());
		assertNull(itemComment.getItem());
		assertNull(itemComment.getText());
		assertNull(itemComment.getRating());
		assertNull(itemComment.getCreateUser());
		assertNull(itemComment.getCreateTimestamp());
		assertNull(itemComment.getUpdateUser());
		assertNull(itemComment.getUpdateTimestamp());
	}
	
	@Test
	public void testConstructorWithFields() {
		Item item = new Item();
		User user = new User();
		ItemComment itemComment = new ItemComment(item, "comment", 1, user, user);
		
		assertNull(itemComment.getId());
		assertEquals(item, itemComment.getItem());
		assertEquals("comment", itemComment.getText());
		assertEquals(new Integer(1), itemComment.getRating());
		assertEquals(user, itemComment.getCreateUser());
		assertNotNull(itemComment.getCreateTimestamp());
		assertEquals(user, itemComment.getUpdateUser());
		assertNotNull(itemComment.getUpdateTimestamp());
	}
	
	@Test
	public void testGetterAndSetter() {
		Item item = new Item();
		User user = new User();
		ItemComment itemComment = new ItemComment();
		Timestamp timestamp = new Timestamp(new Date().getTime());
		
		itemComment.setId(1L);
		itemComment.setItem(item);
		itemComment.setText("comment");
		itemComment.setRating(1);
		itemComment.setCreateUser(user);
		itemComment.setCreateTimestamp(timestamp);
		itemComment.setUpdateUser(user);
		itemComment.setUpdateTimestamp(timestamp);
		
		assertEquals(new Long(1), itemComment.getId());
		assertEquals(item, itemComment.getItem());
		assertEquals("comment", itemComment.getText());
		assertEquals(new Integer(1), itemComment.getRating());
		assertEquals(user, itemComment.getCreateUser());
		assertEquals(timestamp, itemComment.getCreateTimestamp());
		assertEquals(user, itemComment.getUpdateUser());
		assertEquals(timestamp, itemComment.getUpdateTimestamp());
	}
}