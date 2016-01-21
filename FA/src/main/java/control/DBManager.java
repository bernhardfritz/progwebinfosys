package control;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Account;
import model.Operation;
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
    
    
    /* =========================== Account functions =========================== */
    
    @SuppressWarnings("unchecked")
	public List<Account> getAccounts() {
    	Query query = entityManager.createQuery("SELECT a FROM " + Account.class.getSimpleName() + " a");
		
		return (List<Account>)query.getResultList();
    }
    
    
    @SuppressWarnings("unchecked")
	public Account getAccountByAccountNumber(String accountNumber) {
    	Query query = entityManager.createQuery("SELECT a FROM " + Account.class.getSimpleName() + " a WHERE a.accountNumber = ?1");
		query.setParameter(1, accountNumber);
		
		List<Account> accountList = (List<Account>)query.getResultList();
		if (!accountList.isEmpty()) {
			return accountList.get(0);
		}
		
		return null;
    }
    
    @SuppressWarnings("unchecked")
	public List<Account> getAccountsByUsername(String username) {
    	Query query = entityManager.createQuery("SELECT a FROM " + Account.class.getSimpleName() + " a WHERE a.owner.username = ?1");
		query.setParameter(1, username);
		
		return (List<Account>)query.getResultList();
    }
    
    
    /* =========================== Operation functions =========================== */
    
    @SuppressWarnings("unchecked")
	public List<Operation> getOperationsByAccountNumber(String accountNumber) {
    	Query query = entityManager.createQuery("SELECT o FROM " + Operation.class.getSimpleName() + " o WHERE o.fromAccount.accountNumber = ?1 OR o.toAccount.accountNumber = ?1 ORDER BY o.createTimestamp DESC");
		query.setParameter(1, accountNumber);
		
		return (List<Operation>)query.getResultList();
    }
    
    private Operation createOperation(Account fromAccount, Account toAccount, BigDecimal amount, User currentUser, boolean isDeposition) {
    	if (fromAccount == null || toAccount == null || amount == null || currentUser == null) {
    		return null;
    	}
    	if (fromAccount.getBalance().subtract(amount.abs()).compareTo(fromAccount.getLowerLimit()) < 0) {
    		return null;
    	}
    	
    	if (fromAccount.getAccountNumber().equals(toAccount.getAccountNumber())) {
    		if (!isDeposition) {
    			return null;
    		}
    		fromAccount.setBalance(fromAccount.getBalance().add(amount));
    	} else {
    		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
    			return null;
    		}
    		fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        	toAccount.setBalance(toAccount.getBalance().add(amount));
    	}
    	
    	EntityTransaction transaction = startSaveTransaction();
    	Operation operation = new Operation(fromAccount, toAccount, amount, currentUser);
    	try {
    		entityManager.persist(fromAccount);
    		entityManager.persist(toAccount);
    		entityManager.persist(operation);
    		transaction.commit();
    		return operation;
    	}
    	catch (Exception e) {
    		transaction.rollback();
    	}
    	
    	return null;
    }
    
    public Operation createOperation(String fromAccountNumber, String toAccountNumber, BigDecimal amount, User currentUser) {
    	if (fromAccountNumber == null || toAccountNumber == null || amount == null || currentUser == null) {
    		return null;
    	}
    	
    	return createOperation(getAccountByAccountNumber(fromAccountNumber), getAccountByAccountNumber(toAccountNumber), amount, currentUser, false);
    }
    
    public Operation createOperation(User fromUser, User toUser, BigDecimal amount, User currentUser) {
    	if (fromUser == null || toUser == null || amount == null || currentUser == null) {
    		return null;
    	}
    	
    	for (Account fromAccount : getAccountsByUsername(fromUser.getUsername())) {
    		if (fromAccount.getAccountType().getLabel().equals("Account")) {
    			for (Account toAccount : getAccountsByUsername(toUser.getUsername())) {
    				if (toAccount.getAccountType().getLabel().equals("Account")) {
    					return createOperation(fromAccount, toAccount, amount, currentUser, true);
    				}
    			}
    		}
    	}
    	
    	return null;
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

	public boolean deleteUser(Long userId) {		
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
		
		return null;
	}
}