package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.config.PasswordStrengthValidator;
import cz.muni.fi.pa165.config.ValidationReport;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exceptions.DataAccessException;
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
		if (user != null) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}
                try {
                    userDao.create(user);
                }catch(Exception e) {
                    throw new DataAccessException(e);
                }
	}

	@Override
	public void update(User user) {
                try {
                    userDao.update(user);
                }catch(Exception e) {
                    throw new DataAccessException(e);
                }
	}

	@Override
	public User findById(Long id) {
                try {
                    return userDao.findById(id);
                }catch(Exception e) {
                    throw new DataAccessException(e);
                }
	}

	@Override
	public void delete(User user) {
                try {
                    userDao.delete(user);
                }catch(Exception e) {
                    throw new DataAccessException(e);
                }
	}

	@Override
	public List<User> findAll() {
                try {
                    return userDao.findAll();
                }catch(Exception e) {
                    throw new DataAccessException(e);
                }
        }
	@Override
	public void changePassword(User user, String newPassword) {
		ValidationReport report = passwordStrengthValidator.validate(newPassword);
		if (!report.hasPassed()) {
			throw new IllegalArgumentException("<ul>" + report.getErrors().stream().map((e) -> "<li>" + e + "</li>").reduce("", String::concat).trim() + "</li>");
		}

		user.setPassword(bCryptPasswordEncoder.encode(newPassword));
                try {
                    userDao.update(user);
                }catch(Exception e) {
                    throw new DataAccessException(e);
                }
	}

	@Override
	public User findByUsername(String username) {
                try {
                    return userDao.findByUsername(username);
                }catch(Exception e) {
                    throw new DataAccessException(e);
                }
	}

}
