package cz.muni.fi.pa165.util;

import cz.muni.fi.pa165.entity.*;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jan Stourac
 */
public class EntityUtils {

    public static Genre getValidGenre() {
        Genre genre = new Genre();
        genre.setName("Rock");
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

    public static Album getValidAlbum() {
        Album album = new Album();
        album.setTitle("Testing album");

        return album;
    }

    public static Album getPersistedValidAlbum(EntityManagerFactory emf) {
        Album album = getValidAlbum();
        TestUtils.persistObjects(emf, album);
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

    public static Art getArt() {
        Art art = new Art();
        art.setImageName("TestArtName");
        art.setImageType("TestArtType");
        art.setImage("TestArtImage".getBytes());
        return art;
    }


	public static User getValidUser() {
        User user = new User();
        user.setUsername("user");
		user.setName("Valid User");
		user.setPassword("password");
        return user;
    }

    public static User getPersistedValidUser(EntityManagerFactory emf) {
        User user = getValidUser();
        TestUtils.persistObjects(emf, user);
        return user;
    }

	public static Role getValidRole() {
        Role role = new Role();
        role.setName("role");
        return role;
    }

    public static Role getPersistedValidRole(EntityManagerFactory emf) {
        Role role = getValidRole();
        TestUtils.persistObjects(emf, role);
        return role;
    }

	public static AlbumRating getValidAlbumRating(Album album, User user) {
		AlbumRating rating = new AlbumRating();
		rating.setAlbum(album);
		rating.setUser(user);
		rating.setRvalue(0.35f);
		return rating;
	}

	public static AlbumRating getPersistedValidAlbumRating(EntityManagerFactory emf) {
		Album album = getValidAlbum();
		User user = getValidUser();
		AlbumRating rating = getValidAlbumRating(album, user);
		TestUtils.persistObjects(emf, album, user, rating);
		return rating;
	}

}
