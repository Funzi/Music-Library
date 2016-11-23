package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Role;
import java.util.List;

/**
 *
 * @author Jan Stourac
 */
public interface RoleDao {

	/**
	 * Stores given role in database.
	 *
	 * @param role role to store
	 */
	void create(Role role);

	/**
	 * Updates given role in database.
	 *
	 * @param role role to update
	 * @return role updated instance
	 */
	Role update(Role role);

	/**
	 * Deletes given role from database.
	 *
	 * @param role role to delete
	 */
	void delete(Role role);

	/**
	 * Returns role with given id or null.
	 *
	 * @param id role id
	 * @return role with given id
	 */
	Role findById(Long id);

	/**
	 * Returns role with given name or null.
	 *
	 * @param name role name
	 * @return role with given name
	 */
	Role findByName(String name);

	/**
	 * Returns all roles from database.
	 *
	 * @return list containing all roles
	 */
	List<Role> findAll();
}
