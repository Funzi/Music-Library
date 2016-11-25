package cz.muni.fi.pa165.api.dto;

import java.util.Set;

/**
 *
 * @author Jan Stourac
 */
public class UserDTO {

	private Long id;

	private String username;

	private Set<RoleDTO> roles;

	public Long getId() {
		return id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleDTO> roles) {
		this.roles = roles;
	}

}
