package cz.muni.fi.pa165.util;

import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.entity.Song;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jan Stourac
 */
public class EntityUtils {

	public static Genre getValidGenre() {
		Genre genre = new Genre();
		return genre;
	}

	public static Genre getPersistedValidGenre(EntityManagerFactory emf) {
		Genre genre = getValidGenre();
		TestUtils.persistObjects(emf, genre);
		return genre;
	}

	public static Musician getValidMusician() {
		Musician musician = new Musician();
		musician.setName("Testing musician");
		return musician;
	}

	public static Musician getPersistedValidMusician(EntityManagerFactory emf) {
		Musician musician = getValidMusician();
		TestUtils.persistObjects(emf, musician);
		return musician;
	}

	public static Album getValidAlbum(Musician musician) {
		Album album = new Album();
		album.setTitle("Testing album");
		List<Musician> list = new ArrayList<>();
		list.add(musician);
		album.setMusicians(list);
		album.setReleaseDate(LocalDate.now());
		return album;
	}

	public static Album getPersistedValidAlbum(EntityManagerFactory emf) {
		Musician musician = getValidMusician();
		Album album = getValidAlbum(musician);
		TestUtils.persistObjects(emf, musician, album);
		return album;
	}

	public static Song getValidSong() {
		Song song = new Song();
		song.setTitle("Testing song");
		return song;
	}

	public static Song getPersistedValidSong(EntityManagerFactory emf) {
		Song song = getValidSong();
		TestUtils.persistObjects(emf, song);
		return song;
	}
}
