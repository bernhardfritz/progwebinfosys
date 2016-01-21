package control;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.User;

public class DBManager implements IDBManager {
	
	private EntityManagerFactory factory;
	
    private EntityManager entityManager;
	
	private DBManager() {
		init();
	}

	private static class SingletonHelper {
        private static final IDBManager INSTANCE = new DBManager();
    }

    public static IDBManager getInstance() {
        return SingletonHelper.INSTANCE;
    }
    
    private void init() {
    	factory = Persistence.createEntityManagerFactory("FA");
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
	
	@SuppressWarnings("unchecked")
	public User getUserByUsername(String username) {
		Query query = entityManager.createQuery("SELECT u FROM " + User.class.getSimpleName() + " u WHERE u.username = ?1");
		query.setParameter(1, username);
		
		List<User> userList = (List<User>)query.getResultList();
		if (!userList.isEmpty()) {
			return userList.get(0);
		}
		
		return null;
	}

	public User createUser(String username, String password) {
		if (username.isEmpty() || password.isEmpty()) {
			return null;
		}
		EntityTransaction transaction = startSaveTransaction();
		
    	User user = new User(username, password);
    	
    	try {
    		entityManager.persist(user);
            transaction.commit();
            return user;
    	}
    	catch (Exception e) {
    		transaction.rollback();
    	}
    	
    	return null;
	}

	public User editUser(Long userId, String password) {
		if (password.isEmpty()) {
			return null;
		}
		
		EntityTransaction transaction = startSaveTransaction();
		
		User user = getUserById(userId);
		if (user != null) {
			user.setPassword(password);
		}
		
		try {
			entityManager.persist(user);
			transaction.commit();
			return user;
		} catch(Exception e) {
			transaction.rollback();
		}
		
		return null;
	}

	public boolean deleteUser(Long userId, User currentUser) {
		if (currentUser == null) {
			return false;
		}
		
		EntityTransaction transaction = startSaveTransaction();
		
		User user = getUserById(userId);
		if (user != null) {
			entityManager.remove(user);
		}
		
		try {
			transaction.commit();
			return true;
		} catch(Exception e) {
			transaction.rollback();
		}
		
		return false;
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
		
		return logout();
	}
	
	public User logout() {
		return getUserByUsername("guest");
	}
}