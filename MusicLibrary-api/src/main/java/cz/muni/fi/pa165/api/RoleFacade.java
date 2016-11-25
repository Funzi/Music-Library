package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.api.dto.RoleDTO;
import java.util.List;

/**
 *
 * @author Jan Stourac
 */
public interface RoleFacade {

	Long createRole(RoleDTO role);

	RoleDTO getRoleById(Long id);

	List<RoleDTO> getAllRoles();
}
