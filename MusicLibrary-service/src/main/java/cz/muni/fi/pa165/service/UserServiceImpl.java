package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Jan Stourac
 */
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void create(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userDao.create(user);
	}

	@Override
	public void update(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userDao.update(user);
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

}
