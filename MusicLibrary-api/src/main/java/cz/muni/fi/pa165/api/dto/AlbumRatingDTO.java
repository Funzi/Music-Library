package cz.muni.fi.pa165.api.dto;

import java.util.Date;

/**
 *
 * @author Jan Stourac
 */
public class AlbumRatingDTO {

	private Long id;

	private AlbumDTO album;

	private double rvalue;

	private UserDTO user;

	private String comment;

	private Date added;

	public Long getId() {
		return id;
	}

	public AlbumDTO getAlbum() {
		return album;
	}

	public void setAlbum(AlbumDTO album) {
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

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}


}
