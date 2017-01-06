package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jan Stourac
 */
@Service
public interface UserService {

	void create(User user);

	void update(User user);

	User findById(Long id);

	void delete(User user);

	List<User> findAll();

	void changePassword(User user, String newPassword);

    User findByUsername(String username);
}
