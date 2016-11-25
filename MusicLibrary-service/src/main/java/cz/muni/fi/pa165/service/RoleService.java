package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Role;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jan Stourac
 */
@Service
public interface RoleService {

	void create(Role role);

	Role findById(Long id);

	void delete(Role role);

	List<Role> findAll();

}