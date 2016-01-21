package control;

import java.util.List;

import model.User;

public interface IDBManager {
	public List<User> getUsers();
	public User getUserById(Long userId);
	public User getUserByUsername(String username);
	public User createUser(String username, String password);
	public User editUser(Long userId, String password);
	public boolean deleteUser(Long userId, User currentUser);
	public User login(String username, String password);
	public User logout();
}