package control;

import java.math.BigDecimal;
import java.util.List;

import model.*;

public interface IDBManager {
	public List<Item> getItems();
	public Item createItem(String title, String description, BigDecimal price, Long categoryId, User createUser);
	public Item getItem(Long itemId);
	public void editItem(Long itemId, String title, String description, BigDecimal price, Long categoryId, User updateUser);
	public void deleteItem(Long itemId);
	public List<ItemComment> getItemComments(Long itemId);
	
	public void createItemComment(String text, Long itemId, User createUser);
	public ItemComment getItemComment(Long itemCommentId);
	public void editItemComment(Long itemCommentId, String text, User updateUser);
	public void deleteItemComment(Long itemCommentId);
	
	public List<Category> getCategories();
	public Category getCategoryById(Long categoryId);
	public Category createCategory(String name, String description, User createUser);
	public void editCategory(Long categoryId, String name, String description, User updateUser);
	public void deleteCategory(Long categoryId);
	public List<Item> getItems(Long categoryId);
	
	public List<User> getUsers();
	public User getUserById(Long userId);
	public void createUser(String username, String password, int bitmap);
	public void editUser(Long userId, String password, int bitmap);
	public void deleteUser(Long userId);
	public User login(String username, String password);
	public User logout();
}