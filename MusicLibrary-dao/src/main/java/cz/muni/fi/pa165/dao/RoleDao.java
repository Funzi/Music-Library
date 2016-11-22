package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Role;
import java.util.List;

/**
 *
 * @author Jan Stourac
 */
public interface RoleDao {

	/**
	 * Stores given song in database.
	 *
	 * @param song song to store
	 */
	void create(Role role);

	/**
	 * Updates given song in database.
	 *
	 * @param song song to update
	 * @return song updated instance
	 */
	Role update(Role role);

	/**
	 * Deletes given song from database.
	 *
	 * @param song song to delete
	 */
	void delete(Role role);

	/**
	 * Returns song with given id or null.
	 *
	 * @param id song id
	 * @return song with given id
	 */
	Role findById(Long id);

	/**
	 * Returns song with given id or null.
	 *
	 * @param id song id
	 * @return song with given id
	 */
	Role findByName(String name);

	/**
	 * Returns all songs from database.
	 *
	 * @return list containing all songs
	 */
	List<Role> findAll();
}
