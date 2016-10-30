package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.utils.Constants;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Class representing musician entity.
 *
 * @author Jan Stourac
 */
@Entity
@Table(name = "MUSICIAN")
public class Musician {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false, name = "id")
	private Long id;

	@NotNull
	@Column(nullable = false, unique = true, length = Constants.INT_LENGTH_SMALL)
	private String name;

	@ManyToMany
	private Set<Album> albums = new HashSet<>();

	public Musician(Long musicianId) {
		this.id = musicianId;
	}

	public Musician() {
	}

	/**
	 * Returns unique identifier of musician. <strong>Warning:</strong> relevant
	 * identifier is provided only when the object is persisted!
	 *
	 * @return unique identifier
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Returns musician name.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets musician name. Maximal name length is limited to
	 * {@link Constants#INT_LENGTH_SMALL}.
	 *
	 * @param name name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Adds album associated to this musician.
	 *
	 * @param album album
	 */
	public void addAlbum(Album album) {
		this.albums.add(album);
	}

	/**
	 * Returns all albums associated to this musician.
	 *
	 * @return albums
	 */
	public Set<Album> getAlbums() {
		return albums;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 29 * hash + Objects.hashCode(this.name);
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
		final Musician other = (Musician) obj;
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Musician{" + "id=" + id + ", name=" + name + ", albums=" + albums + '}';
	}
}
