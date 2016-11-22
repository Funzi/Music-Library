package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;
import java.util.List;

/**
 *
 * @author Jan Stourac
 */
public interface UserDao {

	/**
	 * Stores given song in database.
	 *
	 * @param song song to store
	 */
	void create(User user);

	/**
	 * Updates given song in database.
	 *
	 * @param song song to update
	 * @return song updated instance
	 */
	User update(User user);

	/**
	 * Deletes given song from database.
	 *
	 * @param song song to delete
	 */
	void delete(User user);

	/**
	 * Returns song with given id or null.
	 *
	 * @param id song id
	 * @return song with given id
	 */
	User findById(Long id);

	/**
	 * Returns song with given id or null.
	 *
	 * @param id song id
	 * @return song with given id
	 */
	User findByUsername(String username);

	/**
	 * Returns all songs from database.
	 *
	 * @return list containing all songs
	 */
	List<User> findAll();
}
