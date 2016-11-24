package cz.muni.fi.pa165.api.dto;

import cz.muni.fi.pa165.entity.Role;
import java.util.Set;

/**
 *
 * @author Jan Stourac
 */
public class UserDTO {

	private Long id;

	private String username;

	private Set<Role> roles;

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


}
