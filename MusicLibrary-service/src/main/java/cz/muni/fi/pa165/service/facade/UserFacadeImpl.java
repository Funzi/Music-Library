/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.UserFacade;
import cz.muni.fi.pa165.api.dto.UserCreateDTO;
import cz.muni.fi.pa165.api.dto.UserDTO;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jan Stourac
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

	@Autowired
	private UserService userService;

	@Autowired
	private BeanMappingService beanMappingService;

	@Override
	public Long createUser(UserCreateDTO user) {
		if (!user.getPassword().equals(user.getPasswordConfirm())) {
			throw new IllegalStateException("Entered passwords are not the same");
		}

		User u = beanMappingService.mapTo(user, User.class);
		u.setId(null);
		userService.create(u);
		return u.getId();
	}

	@Override
	public UserDTO getUserById(Long id) {
		return beanMappingService.mapTo(userService.findById(id), UserDTO.class);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		return beanMappingService.mapTo(userService.findAll(), UserDTO.class);
	}

	@Override
	public void changePassword(UserDTO user, String newPassword) {
		userService.changePassword(beanMappingService.mapTo(user, User.class), newPassword);
	}

	@Override
	public UserDTO getUserByUsername(String username) {
		return beanMappingService.mapTo(userService.findByUsername(username), UserDTO.class);
	}

}
