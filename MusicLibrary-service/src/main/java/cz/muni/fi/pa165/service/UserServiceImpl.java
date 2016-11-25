package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.config.PasswordStrengthValidator;
import cz.muni.fi.pa165.config.ValidationReport;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jan Stourac
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private PasswordStrengthValidator passwordStrengthValidator;

	@Override
	public void create(User user) {
		if(user != null) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}
		userDao.create(user);
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id);
	}

	@Override
	public void delete(User user) {
		userDao.delete(user);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public void changePassword(User user, String newPassword) {
		ValidationReport report = passwordStrengthValidator.validate(newPassword);
		if (!report.hasPassed()) {
			throw new IllegalArgumentException("<ul>" + report.getErrors().stream().map((e) -> "<li>" + e + "</li>").reduce("", String::concat).trim() + "</li>");
		}

		user.setPassword(bCryptPasswordEncoder.encode(newPassword));
		userDao.update(user);
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

}
