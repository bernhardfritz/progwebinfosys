package control;

import java.math.BigDecimal;
import java.util.List;

import model.Account;
import model.Operation;
import model.User;

public interface IDBManager {
	public List<Account> getAccounts();
	public Account getAccountByAccountNumber(String accountNumber);
	public List<Account> getAccountsByUsername(String username);
	
	public List<Operation> getOperationsByAccountNumber(String accountNumber);
	public Operation createOperation(String fromAccountNumber, String toAccountNumber, BigDecimal amount, User currentUser);
	public Operation createOperation(User fromUser, User toUser, BigDecimal amount, User currentUser);
	
	public List<User> getUsers();
	public User getUserById(Long userId);
	public User getUserByUsername(String username);
	public User createUser(String username, String password);
	public User editUser(Long userId, String password);
	public boolean deleteUser(Long userId, User currentUser);
	public User login(String username, String password);
}