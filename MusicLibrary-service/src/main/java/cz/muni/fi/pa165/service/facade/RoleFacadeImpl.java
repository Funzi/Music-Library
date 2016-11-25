package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.RoleFacade;
import cz.muni.fi.pa165.api.dto.RoleDTO;
import cz.muni.fi.pa165.entity.Role;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.RoleService;
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
public class RoleFacadeImpl implements RoleFacade {

	@Autowired
	private RoleService roleService;

	@Autowired
	private BeanMappingService beanMappingService;

	@Override
	public Long createRole(RoleDTO role) {
		Role r = beanMappingService.mapTo(role, Role.class);
		r.setId(null);
		roleService.create(r);
		return r.getId();
	}

	@Override
	public RoleDTO getRoleById(Long id) {
		return beanMappingService.mapTo(roleService.findById(id), RoleDTO.class);
	}

	@Override
	public List<RoleDTO> getAllRoles() {
		return beanMappingService.mapTo(roleService.findAll(), RoleDTO.class);
	}

}
