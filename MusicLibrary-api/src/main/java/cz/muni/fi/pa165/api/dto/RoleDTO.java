package cz.muni.fi.pa165.api.dto;

import java.util.Set;

/**
 *
 * @author Jan Stourac
 */
public class RoleDTO {

	private Long id;

	private String name;

	private Set<UserDTO> users;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(Set<UserDTO> users) {
		this.users = users;
	}


}
