package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.RoleDao;
import cz.muni.fi.pa165.entity.Role;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jan Stourac
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public void create(Role role) {
		roleDao.create(role);
	}

	@Override
	public Role findById(Long id) {
		return roleDao.findById(id);
	}

	@Override
	public void delete(Role role) {
		roleDao.delete(role);
	}

	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}

}
