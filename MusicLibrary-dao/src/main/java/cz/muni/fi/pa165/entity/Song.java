package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.utils.Constants;
import java.util.Objects;
import javax.persistence.*;

/**
 * Class representing song entity.
 *
 * @author David Pribula
 */
@Entity
@Table(name = "SONG")
public class Song {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false, name = "id")
	private Long id;

	@Column(nullable = false, length = Constants.INT_LENGTH_SMALL)
	private String title;

	@ManyToOne
	private Album album;

	@Column(nullable = false)
	private int position;

	@ManyToOne
	private Genre genre;

	private int bitrate;

	@Column(length = Constants.INT_LENGTH_HUGE)
	private String commentary;

	public Song() {

	}

	/**
	 * Returns unique identifier of song. <strong>Warning:</strong> relevant
	 * identifier is provided only when the object is persisted!
	 *
	 * @return unique identifier
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set id of song
	 *
	 * @param id unique identifier
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns title of song
	 *
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set title of song Maximal title length is limited to
	 * {@link Constants#INT_LENGTH_SMALL}.
	 *
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns album of song
	 *
	 * @return album
	 */
	public Album getAlbum() {
		return album;
	}

	/**
	 * Set album of song
	 *
	 * @param album album
	 */
	public void setAlbum(Album album) {
		this.album = album;
	}

	/**
	 * Returns position of song in album
	 *
	 * @return position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Set position of song in album.
	 *
	 * @param position position
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Returns genre of song.
	 *
	 * @return genre
	 */
	public Genre getGenre() {
		return genre;
	}

	/**
	 * Set genre of song
	 *
	 * @param genre genre
	 */
	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	/**
	 * Returns bitrate of song
	 *
	 * @return bitrate
	 */
	public int getBitrate() {
		return bitrate;
	}

	/**
	 * Set bitrate of song
	 *
	 * @param bitrate bitrate
	 */
	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}

	/**
	 * Returns commentary of song.
	 *
	 * @return commentary
	 */
	public String getCommentary() {
		return commentary;
	}

	/**
	 * Set commentary of song. Maximal commentary length is limited to
	 * {@link Constants#INT_LENGTH_Huge}.
	 *
	 * @param commentary commentary
	 */
	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 23 * hash + Objects.hashCode(this.title);
		hash = 23 * hash + Objects.hashCode(this.album);
		hash = 23 * hash + this.position;
		hash = 23 * hash + Objects.hashCode(this.genre);
		hash = 23 * hash + this.bitrate;
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
		final Song other = (Song) obj;
		if (this.position != other.position) {
			return false;
		}
		if (this.bitrate != other.bitrate) {
			return false;
		}
		if (!Objects.equals(this.title, other.title)) {
			return false;
		}
		if (!Objects.equals(this.album, other.album)) {
			return false;
		}
		return Objects.equals(this.genre, other.genre);
	}
}
