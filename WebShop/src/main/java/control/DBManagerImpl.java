package control;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Category;
import model.Item;
import model.ItemComment;
import model.Privileges;
import model.User;

public class DBManagerImpl implements IDBManager {
	
	private EntityManagerFactory factory;
	
    private EntityManager entityManager;
	
	private DBManagerImpl() {
		init();
	}

	private static class SingletonHelper {
        private static final IDBManager INSTANCE = new DBManagerImpl();
    }

    public static IDBManager getInstance() {
        return SingletonHelper.INSTANCE;
    }
    
    private void init() {
    	factory = Persistence.createEntityManagerFactory("WebShop");
    	entityManager = factory.createEntityManager();
    }
    
    private EntityTransaction startSaveTransaction() {
    	EntityTransaction transaction = entityManager.getTransaction();
		if (transaction.isActive()) {
			transaction.rollback();
		}
		transaction.begin();
		
		return transaction;
    }
    
    
    /* =========================== Item functions =========================== */

	@SuppressWarnings("unchecked")
	public List<Item> getItems() {
		Query query = entityManager.createQuery("SELECT i FROM " + Item.class.getSimpleName() + " i");
    	
    	return (List<Item>)query.getResultList();
	}

	public void createItem(String title, String description, BigDecimal price, Long categoryId, Long createUserId) {
		EntityTransaction transaction = startSaveTransaction();
		
		Item item = new Item(getCategoryById(categoryId), title, description, price, getUserById(createUserId), getUserById(createUserId));
    	
    	entityManager.persist(item);
        transaction.commit();
	}

	@SuppressWarnings("unchecked")
	public Item getItem(Long itemId) {
		Query query = entityManager.createQuery("SELECT i FROM " + Item.class.getSimpleName() + " i WHERE i.id = ?1");
		query.setParameter(1, itemId);
		
		List<Item> itemList = (List<Item>)query.getResultList();
		if (!itemList.isEmpty()) {
			return itemList.get(0);
		}
		
		return null;
	}

	public void editItem(Long itemId, String title, String description, BigDecimal price, Long categoryId, Long updateUserId) {
		EntityTransaction transaction = startSaveTransaction();
		
		Item item = getItem(itemId);
		if (item != null) {
			item.setTitle(title);
			item.setDescription(description);
			item.setPrice(price);
			item.setUpdateUser(getUserById(updateUserId));
			item.setUpdateTimestamp(new Timestamp(new Date().getTime()));
		}
		
		entityManager.persist(item);
		transaction.commit();
	}

	public void deleteItem(Long itemId) {
		EntityTransaction transaction = startSaveTransaction();
		
		Item item = getItem(itemId);
		if (item != null) {
			entityManager.remove(item);
		}
		
		transaction.commit();
	}

	@SuppressWarnings("unchecked")
	public List<ItemComment> getItemComments(Long itemId) {
		Query query = entityManager.createQuery("SELECT ic FROM " + ItemComment.class.getSimpleName() + " ic");
    	
    	return (List<ItemComment>)query.getResultList();
	}

	public void createItemComment(String text, Long itemId, Long createUserId) {
		EntityTransaction transaction = startSaveTransaction();
		
		ItemComment itemComment = new ItemComment(getItem(itemId), text, getUserById(createUserId), getUserById(createUserId));
    	
    	entityManager.persist(itemComment);
        transaction.commit();
	}
	
	
	/* =========================== ItemComment functions =========================== */

	@SuppressWarnings("unchecked")
	public ItemComment getItemComment(Long itemCommentId) {
		Query query = entityManager.createQuery("SELECT ic FROM " + ItemComment.class.getSimpleName() + " ic WHERE ic.id = ?1");
		query.setParameter(1, itemCommentId);
		
		List<ItemComment> itemCommentList = (List<ItemComment>)query.getResultList();
		if (!itemCommentList.isEmpty()) {
			return itemCommentList.get(0);
		}
		
		return null;
	}

	public void editItemComment(Long itemCommentId, String text, Long updateUserId) {
		EntityTransaction transaction = startSaveTransaction();
		
		ItemComment itemComment = getItemComment(itemCommentId);
		if (itemComment != null) {
			itemComment.setText(text);
			itemComment.setUpdateUser(getUserById(updateUserId));
			itemComment.setUpdateTimestamp(new Timestamp(new Date().getTime()));
		}
		
		entityManager.persist(itemComment);
		transaction.commit();
	}

	public void deleteItemComment(Long itemCommentId) {
		EntityTransaction transaction = startSaveTransaction();
		
		ItemComment itemComment = getItemComment(itemCommentId);
		if (itemComment != null) {
			entityManager.remove(itemComment);
		}
		
		transaction.commit();
	}
	
	
	/* =========================== Category functions =========================== */

	@SuppressWarnings("unchecked")
	public List<Category> getCategories() {
		Query query = entityManager.createQuery("SELECT c FROM " + Category.class.getSimpleName() + " c");
    	
    	return (List<Category>)query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public Category getCategoryById(Long categoryId) {		
		Query query = entityManager.createQuery("SELECT c FROM " + Category.class.getSimpleName() + " c WHERE c.id = ?1");
		query.setParameter(1, categoryId);
		
		List<Category> categoryList = (List<Category>)query.getResultList();
		if (!categoryList.isEmpty()) {
			return categoryList.get(0);
		}
		
		return null;
	}
	
	public void createCategory(String name, String description, Long createUserId) {
		EntityTransaction transaction = startSaveTransaction();
		
    	Category category = new Category(name, description, getUserById(createUserId), getUserById(createUserId));
    	
    	entityManager.persist(category);
        transaction.commit();
	}

	public void editCategory(Long categoryId, String name, String description, Long updateUserId) {
		EntityTransaction transaction = startSaveTransaction();
		
		Category category = getCategoryById(categoryId);
		if (category != null) {
			category.setName(name);
			category.setDescription(description);
			category.setUpdateUser(getUserById(updateUserId));
			category.setUpdateTimestamp(new Timestamp(new Date().getTime()));
		}
		
		entityManager.persist(category);
		transaction.commit();
	}

	public void deleteCategory(Long categoryId) {
		EntityTransaction transaction = startSaveTransaction();
		
		Category category = getCategoryById(categoryId);
		if (category != null) {
			entityManager.remove(category);
		}
		
		transaction.commit();
	}

	@SuppressWarnings("unchecked")
	public List<Item> getItems(Long categoryId) {
		Query query = entityManager.createQuery("SELECT i FROM " + Item.class.getSimpleName() + " i WHERE i.category = ?1");
		query.setParameter(1, getCategoryById(categoryId));
		
		List<Item> items = (List<Item>)query.getResultList();
		return items;
	}
	
	
	/* =========================== User functions =========================== */

	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		Query query = entityManager.createQuery("SELECT u FROM " + User.class.getSimpleName() + " u");
    	
    	return (List<User>)query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public User getUserById(Long userId) {		
		Query query = entityManager.createQuery("SELECT u FROM " + User.class.getSimpleName() + " u WHERE u.id = ?1");
		query.setParameter(1, userId);
		
		List<User> userList = (List<User>)query.getResultList();
		if (!userList.isEmpty()) {
			return userList.get(0);
		}
		
		return null;
	}

	public void createUser(String username, String password, int bitmap) {
		Privileges privileges = new Privileges(bitmap);		
		EntityTransaction transaction = startSaveTransaction();
		
    	User user = new User(username, password, privileges.isCategoryRead(), privileges.isCategoryWrite(), 
    			privileges.isCategoryDelete(), privileges.isItemRead(), privileges.isItemWrite(), 
    			privileges.isItemDelete(), privileges.isItemCommentRead(), privileges.isItemCommentWrite(), 
    			privileges.isItemCommentDelete());
    	
    	entityManager.persist(user);
        transaction.commit();
	}

	public void editUser(Long userId, String password, int bitmap) {
		Privileges privileges = new Privileges(bitmap);		
		EntityTransaction transaction = startSaveTransaction();
		
		User user = getUserById(userId);
		if (user != null) {
			user.setPassword(password);
			user.setCategoryRead(privileges.isCategoryRead());
			user.setCategoryWrite(privileges.isCategoryWrite());
			user.setCategoryDelete(privileges.isCategoryDelete());
			user.setItemRead(privileges.isItemRead());
			user.setItemWrite(privileges.isItemWrite());
			user.setItemDelete(privileges.isItemDelete());
			user.setItemCommentRead(privileges.isItemCommentRead());
			user.setItemCommentWrite(privileges.isItemCommentWrite());
			user.setItemCommentDelete(privileges.isItemCommentDelete());
		}
		
		entityManager.persist(user);
		transaction.commit();
	}

	public void deleteUser(Long userId) {
		EntityTransaction transaction = startSaveTransaction();
		
		User user = getUserById(userId);
		if (user != null) {
			
			entityManager.remove(user);
		}
		
		transaction.commit();
	}
	
	@SuppressWarnings("unchecked")
	public User login(String username, String password) {
		Query query = entityManager.createQuery("SELECT u FROM " + User.class.getSimpleName() + " u WHERE u.username = ?1 AND u.password = ?2");
		query.setParameter(1, username);
		query.setParameter(2, password);
		
		List<User> userList = (List<User>)query.getResultList();
		if (!userList.isEmpty()) {
			return userList.get(0);
		}
		
		return null;
	}
}