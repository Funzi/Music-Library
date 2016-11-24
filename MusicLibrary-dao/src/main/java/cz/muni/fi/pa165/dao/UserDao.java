package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;
import java.util.List;

/**
 *
 * @author Jan Stourac
 */
public interface UserDao {

	/**
	 * Stores given user in database.
	 *
	 * @param user user to store
	 */
	void create(User user);

	/**
	 * Updates given user in database.
	 *
	 * @param user user to update
	 * @return user updated instance
	 */
	User update(User user);

	/**
	 * Deletes given user from database.
	 *
	 * @param user user to delete
	 */
	void delete(User user);

	/**
	 * Returns user with given id or null.
	 *
	 * @param id user id
	 * @return user with given id
	 */
	User findById(Long id);

	/**
	 * Returns user with given username or null.
	 *
	 * @param username username
	 * @return user with given username
	 */
	User findByUsername(String username);

	/**
	 * Returns all users from database.
	 *
	 * @return list containing all users
	 */
	List<User> findAll();
}
