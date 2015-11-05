package control;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class DBManagerImpl implements IDBManager {

	private static class SingletonHelper {
        private static final IDBManager INSTANCE = new DBManagerImpl();
    }

    public static IDBManager getInstance() {
        return SingletonHelper.INSTANCE;
    }

	public List<Item> getItems() {
		List<Item> items = new ArrayList<Item>();
		return items;
	}

	public void createItem(String title, String description, BigDecimal price, int categoryId, int createUserId) {
		// create item
	}

	public Item getItem(int itemId) {
		Item item = null;
		return item;
	}

	public void editItem(int itemId, String title, String description, BigDecimal price, int categoryId, int updateUserId) {
		// TODO edit Item
	}

	public void deleteItem(int itemId) {
		// TODO delete Item
	}

	public List<ItemComment> getComments(int itemId) {
		List<ItemComment> itemComments = new ArrayList<ItemComment>();
		// TODO fill list
		return itemComments;
	}

	public void createComment(String text, int itemId, int createUserId) {
		// TODO create comment
	}

	public ItemComment getComment(int commentId) {
		ItemComment comment = null;
		return comment;
	}

	public void editComment(int commentId, String text, int updateUserId) {
		// TODO edit comment
	}

	public void deleteComment(int commentId) {
		// TODO delete comment
	}

	public List<Category> getCategories() {
		List<Category> categories = new ArrayList<Category>();
		// TODO fill list
		return categories;
	}
	public void createCategory(String name, String description, int createUserId) {
		// TODO create category
	}

	public void editCategory(int categoryId, String name, String description, int updateUserId) {
		// TODO edit category
	}

	public void deleteCategory(int categoryId) {
		// TODO deleteCategory
	}

	public List<Item> getItems(int categoryId) {
		List<Item> items = new ArrayList<Item>();
		// TODO fill list with category specific items
		return items;
	}

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		// TODO fill list
		return users;
	}

	public void createUser(String username, String password, int bitmap) {
		Privileges privileges = new Privileges(bitmap);
		privileges.isCategoryDelete();
		// TODO create user
	}

	public void editUser(int userId, String password, int bitmap) {
		Privileges privileges = new Privileges(bitmap);
		privileges.isCategoryDelete();
		// TODO edit user
	}

	public void deleteUser() {
		// TODO delete user
	}
}
