package cz.muni.fi.pa165.api.dto;

import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Jan Stourac
 */
public class UserDTO {

	private Long id;

	private String username;

	private Set<RoleDTO> roles;

	private Set<AlbumDTO> wishlist;

	public void setId(Long id) {
		this.id = id;
	}

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

	public Set<AlbumDTO> getWishlist() {
		return wishlist;
	}

	public void setWishlist(Set<AlbumDTO> wishlist) {
		this.wishlist = wishlist;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 47 * hash + Objects.hashCode(this.username);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final UserDTO other = (UserDTO) obj;
		if (!Objects.equals(this.username, other.username)) {
			return false;
		}
		return true;
	}

}
