package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.User;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jan Stourac
 */
@Service
public interface UserService {

	void create(User user);

	void changePassword(User user, String newPassword);

    User findByUsername(String username);
}
