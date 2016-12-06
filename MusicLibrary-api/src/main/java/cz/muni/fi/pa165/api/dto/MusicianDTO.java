package cz.muni.fi.pa165.api.dto;

import java.util.Objects;

/**
 *
 * @author Jan Stourac
 */
public class MusicianDTO {

	private long id;
	private String name;
	private double avgAlbumRating;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAvgAlbumRating() {
		return avgAlbumRating;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17 * hash + Objects.hashCode(this.name);
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
		final MusicianDTO other = (MusicianDTO) obj;
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return true;
	}

	

}
