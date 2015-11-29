package control;

import java.math.BigDecimal;
import java.util.List;

import model.*;

public interface IDBManager {
	public List<Item> getItems();
	public Item createItem(String title, String description, BigDecimal price, Long categoryId, User createUser);
	public Item getItem(Long itemId);
	public Item editItem(Long itemId, String title, String description, BigDecimal price, Long categoryId, User updateUser);
	public boolean deleteItem(Long itemId);
	public List<ItemComment> getItemComments(Long itemId);
	
	public ItemComment createItemComment(String text, Long itemId, Integer rating, User createUser);
	public ItemComment getItemComment(Long itemCommentId);
	public ItemComment editItemComment(Long itemCommentId, String text, Integer rating, User updateUser);
	public boolean deleteItemComment(Long itemCommentId);
	
	public List<Category> getCategories();
	public Category getCategoryById(Long categoryId);
	public Category createCategory(String name, String description, User createUser);
	public Category editCategory(Long categoryId, String name, String description, User updateUser);
	public boolean deleteCategory(Long categoryId);
	public List<Item> getItems(Long categoryId);
	
	public List<User> getUsers();
	public User getUserById(Long userId);
	public User createUser(String username, String password, long bitmap);
	public User editUser(Long userId, String password, long bitmap);
	public boolean deleteUser(Long userId);
	public User login(String username, String password);
	public User logout();
}