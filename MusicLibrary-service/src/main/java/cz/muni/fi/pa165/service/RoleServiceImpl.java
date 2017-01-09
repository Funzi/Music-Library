package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.RoleDao;
import cz.muni.fi.pa165.entity.Role;
import cz.muni.fi.pa165.exceptions.DataAccessException;
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
            try {
		roleDao.create(role);
            }catch(Exception e) {
                throw new DataAccessException(e);
            }
	}

	@Override
	public Role findById(Long id) {
            try {
		return roleDao.findById(id);
            }catch(Exception e) {
                throw new DataAccessException(e);
            }
	}

	@Override
	public void delete(Role role) {
            try {
		roleDao.delete(role);
            }catch(Exception e) {
                throw new DataAccessException(e);
            }
	}

	@Override
	public List<Role> findAll() {
            try {
		return roleDao.findAll();
            }catch(Exception e) {
                throw new DataAccessException(e);
            }
	}

}
