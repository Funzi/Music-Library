/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.AlbumRating;
import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.entity.Song;

import cz.muni.fi.pa165.entity.User;

import cz.muni.fi.pa165.util.EntityUtils;
import static cz.muni.fi.pa165.util.EntityUtils.getValidAlbum;
import static cz.muni.fi.pa165.util.EntityUtils.getValidAlbumRating;
import cz.muni.fi.pa165.util.TestUtils;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for Album DAO.
 *
 * @author Martin Kulisek
 * @see SongDao
 */
@ContextConfiguration(classes = AppContext.class)
public class AlbumDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private AlbumDao albumDao;

    @Test
    public void createAndFindByIdTest() {
        Album album = EntityUtils.getValidAlbum();
        albumDao.create(album);

        Album album2 = albumDao.findById(album.getId());
        assertEquals(album, album2);
    }

    @Test
    public void multiCreateTest() {
        Album album = EntityUtils.getValidAlbum();
        albumDao.create(album);

        Album album2 = EntityUtils.getValidAlbum();
        albumDao.create(album2);

        assertEquals(albumDao.findById(album.getId()), album);
        assertEquals(albumDao.findById(album2.getId()), album2);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void doubleCreateTest() {
        Album album = EntityUtils.getValidAlbum();
        albumDao.create(album);
        albumDao.create(album);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNullTest() {
        albumDao.create(null);
    }

    @Test
    public void findNoneTest() {
        assertNull(albumDao.findById(10L));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findNullTest() {
        albumDao.findById(null);
    }

    @Test
    public void findAllNoneTest() {
        assertTrue(albumDao.findAll().isEmpty());
    }

    @Test
    public void findAllTest() {
        Album album = EntityUtils.getValidAlbum();
        albumDao.create(album);

        Album album2 = EntityUtils.getValidAlbum();
        albumDao.create(album2);

        List<Album> albums = albumDao.findAll();
        assertEquals(albums.size(), 2);
        assertTrue(albums.contains(album) && albums.contains(album2));

        assertEquals(albumDao.findById(album.getId()), album);
        assertEquals(albumDao.findById(album2.getId()), album2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findAllNullTest() {
        albumDao.findById(null);
    }

    @Test
    public void deleteTest() {
        Album album = EntityUtils.getValidAlbum();
        albumDao.create(album);
        assertEquals(albumDao.findById(album.getId()), album);

        albumDao.delete(album);
        assertNull(albumDao.findById(album.getId()));
    }

    @Test
    public void multiDeleteTest() {
        Album album = EntityUtils.getValidAlbum();
        albumDao.create(album);

        Album album2 = EntityUtils.getValidAlbum();
        albumDao.create(album2);

        assertEquals(albumDao.findAll().size(), 2);

        albumDao.delete(album);
        assertEquals(albumDao.findAll().size(), 1);
        assertEquals(albumDao.findById(album2.getId()), album2);

        albumDao.delete(album2);
        assertEquals(albumDao.findAll().size(), 0);
    }

    @Test()
    public void deleteInvalidTest() {
        Album album = EntityUtils.getValidAlbum();
        Album album2 = EntityUtils.getValidAlbum();

        albumDao.create(album2);
        albumDao.delete(album);
        assertEquals(albumDao.findById(album2.getId()), album2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNullTest() {
        albumDao.delete(null);
    }

    @Test
    public void updateTest() {
        Album album = EntityUtils.getValidAlbum();
        albumDao.create(album);
        assertEquals(albumDao.findById(album.getId()).getTitle(), album.getTitle());

        album.setTitle("random");
        assertNotEquals(albumDao.findById(album.getId()).getTitle(), album.getTitle());
        albumDao.update(album);
        assertEquals(albumDao.findById(album.getId()).getTitle(), album.getTitle());
    }

    @Test
    public void multiUpdateTest() {
        Album album = EntityUtils.getValidAlbum();
        albumDao.create(album);

        Album album2 = EntityUtils.getValidAlbum();
        albumDao.create(album2);

        album.setTitle("random");
        albumDao.update(album);
        assertEquals(albumDao.findById(album.getId()).getTitle(), album.getTitle());
        assertEquals(albumDao.findById(album2.getId()).getTitle(), album2.getTitle());

        album2.setTitle("random #2");
        albumDao.update(album2);
        assertEquals(albumDao.findById(album.getId()).getTitle(), album.getTitle());
        assertEquals(albumDao.findById(album2.getId()).getTitle(), album2.getTitle());
    }

    @Test
    public void updateInvalidTest() {
        Album album = EntityUtils.getValidAlbum();
        Album album2 = EntityUtils.getValidAlbum();
        albumDao.create(album2);

        album.setTitle("random");
        albumDao.update(album);
        assertEquals(albumDao.findById(album2.getId()).getTitle(), album2.getTitle());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullTest() {
        albumDao.update(null);
    }

	@Test
	public void testBestRating() {
        Album album = EntityUtils.getValidAlbum();
        Album album2 = EntityUtils.getValidAlbum();
		album2.setTitle("aaaaa");

		User user1 = EntityUtils.getValidUser();
		User user2 = EntityUtils.getValidUser();
		user2.setUsername("user 2");

		AlbumRating rating = getValidAlbumRating(album, user1);
		rating.setRvalue(1.0);

		AlbumRating rating2 = getValidAlbumRating(album, user2);
		rating2.setRvalue(0.3);

		AlbumRating rating3 = getValidAlbumRating(album2, user1);
		rating2.setRvalue(0.3);

		albumDao.create(album);
        albumDao.create(album2);

		TestUtils.persistObjects(emf, user1, user2, rating3);
		try {
			Thread.sleep(2000);
		} catch(Exception ex) {
			fail();
		}
		Date date = new Date();
		TestUtils.persistObjects(emf, rating, rating2);

		album = albumDao.findById(album.getId());
		album2 = albumDao.findById(album2.getId());

		List<Album> best;
		best = albumDao.findBestRated(Integer.MAX_VALUE);
		assertEquals(best.size(), 2);
		assertTrue(best.contains(album) && best.contains(album2));

		best = albumDao.findBestRated(1);
		assertEquals(best.size(), 1);
		assertTrue(best.contains(album));

		best = albumDao.findBestRated(1, date);
		assertEquals(best.size(), 1);
		assertTrue(best.contains(album2));
	}

	@Test
	public void testBestRatingForMusician() {
		Album album1 = getValidAlbum();
		Album album2 = getValidAlbum();

		Musician musician = EntityUtils.getPersistedValidMusician(emf);
		Song song1 = EntityUtils.getValidSong();
		song1.setMusician(musician);
		song1.setAlbum(album1);
		Song song2 = EntityUtils.getValidSong();
		song2.setMusician(musician);
		song2.setAlbum(album2);

		User user = EntityUtils.getPersistedValidUser(emf);
		AlbumRating rating1 = getValidAlbumRating(album1, user);
		rating1.setRvalue(1.0);
		AlbumRating rating2 = getValidAlbumRating(album2, user);
		rating2.setRvalue(0.60);

		TestUtils.persistObjects(emf, song1, song2, album1, album2, rating1, rating2);

		List<Album> bestRated = albumDao.findBestRatedForMusician(1, musician);
		assertEquals(bestRated.size(), 1);
		assertTrue(bestRated.contains(album1));
	}

    @Test
    public void getAlbumByMusician() {
        Musician musician = EntityUtils.getPersistedValidMusician(emf);
        Song song1 = new Song();
        Song song2 = new Song();
        Album album1 = new Album();
        Album album2 = new Album();

        song1.setAlbum(album1);
        song1.setTitle("songWithMusician");
        song1.setBitrate(1);
        song1.setCommentary("qwe");
        song1.setMusician(musician);

        album1.setTitle("albumWithMusician");
        album1.setCommentary("qwe");
        album1.addSong(song1);

        TestUtils.persistObjects(emf, album1, song1);

        song2.setAlbum(album2);
        song2.setTitle("songWithOUTmusician");
        song2.setCommentary("asd");
        song2.setBitrate(1);

        album2.setTitle("albumWITHOUTMusician");
        album2.addSong(song2);
        TestUtils.persistObjects(emf, album2, song2);

        assertTrue(this.albumDao.findAlbumByMusicianId(musician.getId()).get(0).equals(album1));

    }


    @AfterMethod
    public void deleteData() {
        TestUtils.deleteAllData(emf);

    }





}
