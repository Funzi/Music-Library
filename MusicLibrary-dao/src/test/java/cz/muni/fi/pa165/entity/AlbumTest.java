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

    @AfterMethod
    public void deleteData() {
        TestUtils.deleteData(emf, "Song", "Album","Musician");
    }

    private Album createMinimalValidAlbum() {
        Album album = new Album();
        album.setTitle("Minimal Album");
        return album;
    }
}
