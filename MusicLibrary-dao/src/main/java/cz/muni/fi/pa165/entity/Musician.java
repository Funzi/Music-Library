package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.utils.Constants;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;

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

	@Formula("(select coalesce(avg(r.rvalue), 0) from Album_Rating r, Song s where r.album_id = s.album_id and s.musician_id = id)")
	private double avgAlbumRating;

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
	 * Set unique identifier of musician.
	 *
	 * @param id unique identifier
	 */
	public void setId(Long id) {
		this.id = id;
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

	public double getAvgAlbumRating() {
		return avgAlbumRating;
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
		return "Musician{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
