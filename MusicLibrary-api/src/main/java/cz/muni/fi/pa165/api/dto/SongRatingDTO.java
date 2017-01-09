/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.api.dto;

import java.time.LocalDate;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Jan Stourac
 */
public class SongRatingDTO implements Comparable<SongRatingDTO> {

	private Long id;

	private SongDTO song;

	private double rvalue;

	private UserDTO user;

	private String comment;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate added;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public SongDTO getSong() {
		return song;
	}

	public void setSong(SongDTO song) {
		this.song = song;
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

	public LocalDate getAdded() {
		return added;
	}

	public void setAdded(LocalDate added) {
		this.added = added;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + Objects.hashCode(this.song);
		hash = 67 * hash + Objects.hashCode(this.user);
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
		final SongRatingDTO other = (SongRatingDTO) obj;
		if (!Objects.equals(this.song, other.song)) {
			return false;
		}
		if (!Objects.equals(this.user, other.user)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(SongRatingDTO o) {
		int ret = -1 * added.compareTo(o.added);
		return ret != 0 ? ret : 1;
	}

}
