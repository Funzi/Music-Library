package cz.muni.fi.pa165.api.dto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Created by olda on 20.11.2016.
 */
public class AlbumDTO {

	private Long id;

	private String title;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate releaseDate;

	private String commentary;

	private SortedSet<SongDTO> songs = new TreeSet<>();

	private SortedSet<AlbumRatingDTO> ratings = new TreeSet<>();

	private ArtDTO art;

	private double avgRating;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public Set<SongDTO> getSongs() {
		return songs;
	}

	public void setSongs(SortedSet<SongDTO> songs) {
		this.songs = songs;
	}

	public SortedSet<AlbumRatingDTO> getRatings() {
		return ratings;
	}

	public void setRatings(SortedSet<AlbumRatingDTO> ratings) {
		this.ratings = ratings;
	}

	public ArtDTO getArt() {
		return art;
	}

	public void setArt(ArtDTO art) {
		this.art = art;
	}

	public void addSong(SongDTO songDTO) {
		this.songs.add(songDTO);
	}

	public void addSong(Collection<SongDTO> songs) {
		this.songs.addAll(songs);
	}

	public double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		AlbumDTO albumDTO = (AlbumDTO) o;

		if (title != null ? !title.equals(albumDTO.title) : albumDTO.title != null) {
			return false;
		}
		if (releaseDate != null ? !releaseDate.equals(albumDTO.releaseDate) : albumDTO.releaseDate != null) {
			return false;
		}
		return commentary != null ? commentary.equals(albumDTO.commentary) : albumDTO.commentary == null;

	}

	@Override
	public int hashCode() {
		int result = title != null ? title.hashCode() : 0;
		result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
		result = 31 * result + (commentary != null ? commentary.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		String songsIdName = "";
		for (SongDTO s : songs) {
			songsIdName.concat("#" + s.getId() + " title:" + s.getTitle() + ", ");
		}
		return "AlbumDTO{"
				+ "id=" + id
				+ ", title='" + title + '\''
				+ ", releaseDate=" + releaseDate
				+ ", commentary='" + commentary + '\''
				+ ", songs=" + songsIdName
				+ ", art=" + art
				+ '}';
	}
}
