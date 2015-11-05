package control;

import java.math.BigDecimal;
import java.util.List;

import model.*;

public interface IDBManager {
	public List<Item> getItems();
	public void createItem(String title, String description, BigDecimal price, int categoryId, int createUserId);
	public Item getItem(int itemId);
	public void editItem(int itemId, String title, String description, BigDecimal price, int categoryId, int updateUserId);
	public void deleteItem(int itemId);
	public List<ItemComment> getComments(int itemId);
	public void createComment(String text, int itemId, int createUserId);
	
	public ItemComment getComment(int commentId);
	public void editComment(int commentId, String text, int updateUserId);
	public void deleteComment(int commentId);
	
	public List<Category> getCategories();
	public void createCategory(String name, String description, int createUserId);
	public void editCategory(int categoryId, String name, String description, int updateUserId);
	public void deleteCategory(int categoryId);
	public List<Item> getItems(int categoryId);
	
	public List<User> getUsers();
	public void createUser(String username, String password, int bitmap);
	public void editUser(int userId, String password, int bitmap);
	public void deleteUser();
}
