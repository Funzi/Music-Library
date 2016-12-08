package cz.muni.fi.pa165.mvc.forms;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jan Stourac
 */
public class FilterForm {
	private List<Long> musicians = new ArrayList<>();
	private List<Long> genres = new ArrayList<>();

	public void addMusician(Long id) {
		this.musicians.add(id);
	}

	public void setMusicians(List<Long> musicians) {
		this.musicians = musicians;
	}

	public List<Long> getMusicians() {
		return musicians;
	}

	public void addGenre(Long id) {
		this.genres.add(id);
	}

	public List<Long> getGenres() {
		return genres;
	}

	public void setGenres(List<Long> genres) {
		this.genres = genres;
	}

}
