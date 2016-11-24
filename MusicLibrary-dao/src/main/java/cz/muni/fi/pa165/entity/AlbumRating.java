package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.utils.Constants;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author Jan Stourac
 */
@Entity
@Table(name = "ALBUM_RATING",
		uniqueConstraints = {
			@UniqueConstraint(columnNames
					= {"album_id", "user_id"})})

public class AlbumRating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false, name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "album_id", nullable = false)
	private Album album;

	@Column(nullable = false)
	private double rvalue;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = true, length = Constants.INT_LENGTH_LONG)
	private String comment;

	@Column(nullable = false)
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date added;

	public Long getId() {
		return id;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public double getRvalue() {
		return rvalue;
	}

	public void setRvalue(double rvalue) {
		this.rvalue = rvalue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 97 * hash + Objects.hashCode(this.album);
		hash = 97 * hash + (int) (Double.doubleToLongBits(this.rvalue) ^ (Double.doubleToLongBits(this.rvalue) >>> 32));
		hash = 97 * hash + Objects.hashCode(this.user);
		hash = 97 * hash + Objects.hashCode(this.comment);
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
		final AlbumRating other = (AlbumRating) obj;
		if (Double.doubleToLongBits(this.rvalue) != Double.doubleToLongBits(other.rvalue)) {
			return false;
		}
		if (!Objects.equals(this.comment, other.comment)) {
			return false;
		}
		if (!Objects.equals(this.album, other.album)) {
			return false;
		}
		if (!Objects.equals(this.user, other.user)) {
			return false;
		}
		return true;
	}


}
