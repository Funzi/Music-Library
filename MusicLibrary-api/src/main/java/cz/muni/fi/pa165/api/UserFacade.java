package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.api.dto.UserCreateDTO;
import cz.muni.fi.pa165.api.dto.UserDTO;
import java.util.List;

/**
 *
 * @author Jan Stourac
 */
public interface UserFacade {

	Long createUser(UserCreateDTO user);

	UserDTO getUserById(Long id);

	List<UserDTO> getAllUsers();

	void changePassword(UserDTO user, String newPassword);

    UserDTO getUserByUsername(String username);
}
