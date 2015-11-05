package control;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Category;
import model.User;

public class DBManager {
	
	private EntityManagerFactory factory;
	
    private EntityManager entityManager;
	
	private DBManager() {
        init();
    }

    private static class SingletonHelper {
        private static final DBManager INSTANCE = new DBManager();
    }

    public static DBManager getInstance() {
        return SingletonHelper.INSTANCE;
    }
    
    public void disconnect() {
    	entityManager.close();
    	factory.close();
    }
    
    private void init() {
    	factory = Persistence.createEntityManagerFactory("WebShop");
    	entityManager = factory.createEntityManager();
    }
    
    public User registerUser(String username, String password, boolean categoryRead, boolean categoryWrite, boolean categoryDelete,
			boolean itemRead, boolean itemWrite, boolean itemDelete, boolean itemCommentRead, boolean itemCommentWrite,
			boolean itemCommentDelete) {
    	EntityTransaction transaction = entityManager.getTransaction();
    	transaction.begin();
    	
    	User user = new User(username, password, categoryRead, categoryWrite, categoryDelete, itemRead, itemWrite, itemDelete, 
    			itemCommentRead, itemCommentWrite, itemCommentDelete);
    	entityManager.persist(user);
        transaction.commit();
        
        return user;
    }
    
    @SuppressWarnings("unchecked")
	public User findUserById(Long id) {
    	Query query = entityManager.createQuery("SELECT u FROM " + User.class.getSimpleName() + " u WHERE u.id = ?1");
    	query.setParameter(1, id);
    	
    	List<User> userList = (List<User>)query.getResultList();
    	if (!userList.isEmpty()) {
    		return userList.get(0);
    	}
    	
    	return null;
    }
    
    public Category createCategory(User user, String name, String description) {
    	EntityTransaction transaction = entityManager.getTransaction();
    	transaction.begin();
    	
    	Category category = new Category(name, description, user, user);
    	entityManager.persist(category);
    	transaction.commit();
    	
    	return category;
    }
}