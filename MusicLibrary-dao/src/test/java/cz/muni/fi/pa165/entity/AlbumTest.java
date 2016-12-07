/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.util.EntityUtils;
import cz.muni.fi.pa165.util.TestUtils;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for Album entity.
 *
 * @author Martin Kulisek
 * @see Album
 */
@ContextConfiguration(classes = AppContext.class)
public class AlbumTest extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;

	@Test
	public void testEquals() {
		Album album1 = createMinimalValidAlbum();
		TestUtils.persistObjects(emf, album1);

		EntityManager em = emf.createEntityManager();
		Album album2 = em.find(Album.class, album1.getId());
		assertEquals(album1, album2);
	}

	@Test
	public void testHashCode() {
		Album album1 = createMinimalValidAlbum();
		TestUtils.persistObjects(emf, album1);

		EntityManager em2 = emf.createEntityManager();
		Album album2 = em2.find(Album.class, album1.getId());
		assertEquals(album1.hashCode(), album2.hashCode());
	}

	@Test
	public void testGeneratedId() {
		Album album = createMinimalValidAlbum();
		TestUtils.persistObjects(emf, album);

		EntityManager em2 = emf.createEntityManager();
		Album album2 = em2.find(Album.class, album.getId());
		assertEquals(album.getId(), album2.getId());
	}

	@Test
	public void testTitle() {
		String title = "Test";
		Album album = createMinimalValidAlbum();
		album.setTitle(title);
		TestUtils.persistObjects(emf, album);

		EntityManager em2 = emf.createEntityManager();
		Album album2 = em2.find(Album.class, album.getId());
		assertEquals(album2.getTitle(), title);
	}

	@Test(expectedExceptions = ConstraintViolationException.class)
	public void throwIfTitleIsNull() {
		Album album = createMinimalValidAlbum();
		album.setTitle(null);
		TestUtils.persistObjects(emf, album);
	}

	@Test
	public void testMaxTitleLength() {
		Album album = createMinimalValidAlbum();
		album.setTitle(TestUtils.generateString(64));
		TestUtils.persistObjects(emf, album);
	}

	@Test(expectedExceptions = PersistenceException.class)
	public void throwIfTitleIsTooLong() {
		Album album = createMinimalValidAlbum();
		album.setTitle(TestUtils.generateString(65));
		TestUtils.persistObjects(emf, album);
	}

	@Test
	public void testReleaseDate() {
		LocalDate date = LocalDate.now();
		Album album = createMinimalValidAlbum();
		album.setReleaseDate(date);
		TestUtils.persistObjects(emf, album);

		EntityManager em2 = emf.createEntityManager();
		Album album2 = em2.find(Album.class, album.getId());
		assertEquals(album2.getReleaseDate(), date);
	}

	@Test
	public void testSongs() {
		Album album = createMinimalValidAlbum();

		Song song1 = EntityUtils.getValidSong();
		song1.setTitle("super cool title");
		song1.setAlbum(album);

		Song song2 = EntityUtils.getValidSong();
		song2.setAlbum(album);
		Set songs = new HashSet();
		songs.add(song1);
		songs.add(song2);
		album.addSongs(songs);

		TestUtils.persistObjects(emf, album, song1, song2);

		EntityManager em2 = emf.createEntityManager();
		Album album2 = em2.find(Album.class, album.getId());
		assertEquals(album2.getSongs(), songs);
	}

	@Test
	public void testCommentary() {
		String com = "commentary";
		Album album = createMinimalValidAlbum();
		album.setCommentary(com);
		TestUtils.persistObjects(emf, album);

		EntityManager em2 = emf.createEntityManager();
		Album album2 = em2.find(Album.class, album.getId());
		assertEquals(album2.getCommentary(), com);
	}

	@Test
	public void testDefaultCommentary() {
		Album album = createMinimalValidAlbum();
		TestUtils.persistObjects(emf, album);

		EntityManager em2 = emf.createEntityManager();
		Album album2 = em2.find(Album.class, album.getId());
		assertNull(album2.getCommentary());
	}

	@Test
	public void testNullCommentary() {
		Album album = createMinimalValidAlbum();
		album.setCommentary(null);
		TestUtils.persistObjects(emf, album);

		EntityManager em2 = emf.createEntityManager();
		Album album2 = em2.find(Album.class, album.getId());
		assertNull(album2.getCommentary());
	}

	@Test
	public void testCommentaryMaxLength() {
		String com = TestUtils.generateString(2048);
		Album album = createMinimalValidAlbum();
		album.setCommentary(com);
		TestUtils.persistObjects(emf, album);

		EntityManager em2 = emf.createEntityManager();
		Album album2 = em2.find(Album.class, album.getId());
		assertEquals(album2.getCommentary(), com);
	}

	@Test(expectedExceptions = PersistenceException.class)
	public void throwIfCommentaryIsTooLong() {
		Album album = createMinimalValidAlbum();
		album.setCommentary(TestUtils.generateString(2049));
		TestUtils.persistObjects(emf, album);
	}

	@Test
	public void testSimpleAlbumRating() {
		Album album = createMinimalValidAlbum();
		User user = EntityUtils.getPersistedValidUser(emf);
		AlbumRating rating = EntityUtils.getValidAlbumRating(album, user);
		TestUtils.persistObjects(emf, album, rating);

		EntityManager em2 = emf.createEntityManager();
		Album album2 = em2.find(Album.class, album.getId());
		assertTrue(album2.getRatings().size() == 1);
		assertTrue(album2.getRatings().contains(rating));
	}

	@Test
	public void testComplexAlbumRating() {
		Album album = createMinimalValidAlbum();
		Album album2 = createMinimalValidAlbum();
		User user1 = EntityUtils.getValidUser();
		User user2 = EntityUtils.getValidUser();
		user2.setUsername("user 2");
		AlbumRating rating1 = EntityUtils.getValidAlbumRating(album, user1);
		AlbumRating rating2 = EntityUtils.getValidAlbumRating(album, user2);
		AlbumRating rating3 = EntityUtils.getValidAlbumRating(album2, user1);
		TestUtils.persistObjects(emf, user1, user2, album, album2);

		EntityManager em2 = emf.createEntityManager();
		Album album3 = em2.find(Album.class, album.getId());
		assertEquals(album3.getRatings().size(), 2);
		assertTrue(album3.getRatings().contains(rating1) && album3.getRatings().contains(rating2));
	}

	@Test
	public void testAlbumAvgRating() {
		double rvalue1 = 0.5;
		double rvalue2 = 0.35;
		double avg = (rvalue1 + rvalue2) / 2;
		Album album = createMinimalValidAlbum();
		Album album2 = createMinimalValidAlbum();
		User user1 = EntityUtils.getValidUser();
		User user2 = EntityUtils.getValidUser();
		user2.setUsername("user 2");
		AlbumRating rating1 = EntityUtils.getValidAlbumRating(album, user1);
		rating1.setRvalue(rvalue1);
		AlbumRating rating2 = EntityUtils.getValidAlbumRating(album, user2);
		rating2.setRvalue(rvalue2);
		TestUtils.persistObjects(emf, user1, user2, album, album2);

		EntityManager em2 = emf.createEntityManager();
		Album album3 = em2.find(Album.class, album.getId());
		assertEquals(album2.getAvgRating(), 0.0);
		assertEquals(album3.getAvgRating(), avg);
	}

	@AfterMethod
	public void deleteData() {
		//TestUtils.deleteData(emf, "Song", "users", "AlbumRating", "Album", "Musician");
		TestUtils.deleteAllData(emf);
	}

	private Album createMinimalValidAlbum() {
		Album album = new Album();
		album.setTitle("Minimal Album");
		return album;
	}
}
