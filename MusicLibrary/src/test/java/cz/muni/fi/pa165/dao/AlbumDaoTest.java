/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.util.EntityUtils;
import cz.muni.fi.pa165.util.TestUtils;
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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Martin Kulisek
 */
@ContextConfiguration(classes = AppContext.class)
public class AlbumDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private AlbumDao albumDao;

    @Test
    public void createAndFindByIdTest() {
        Album album = EntityUtils.getValidAlbum(EntityUtils.getPersistedValidMusician(emf));
        albumDao.create(album);

        Album album2 = albumDao.findById(album.getId());
        assertEquals(album, album2);
    }

    /*@Test
    public void multiCreateTest() {
        Album album = EntityUtils.getValidAlbum(EntityUtils.getPersistedValidMusician(emf));
        albumDao.create(album);

        Album album2 = EntityUtils.getValidAlbum(EntityUtils.getPersistedValidMusician(emf));
        albumDao.create(album2);

        assertEquals(albumDao.findById(album.getId()), album);
        assertEquals(albumDao.findById(album2.getId()), album2);
    }*/

    @Test(expectedExceptions = PersistenceException.class)
    public void doubleCreateTest() {
        Album album = EntityUtils.getValidAlbum(EntityUtils.getPersistedValidMusician(emf));
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

    /*@Test
    public void findAllTest() {
        Album album = EntityUtils.getValidAlbum(EntityUtils.getPersistedValidMusician(emf));
        albumDao.create(album);

        Album album2 = EntityUtils.getValidAlbum(EntityUtils.getPersistedValidMusician(emf));
        albumDao.create(album2);

        List<Album> albums = albumDao.findAll();
        assertEquals(albums.size(), 2);
        assertTrue(albums.contains(album) && albums.contains(album2));

        assertEquals(albumDao.findById(album.getId()), album);
        assertEquals(albumDao.findById(album2.getId()), album2);
    }*/

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findAllNullTest() {
        albumDao.findById(null);
    }

    @Test
    public void deleteTest() {
        Album album = EntityUtils.getValidAlbum(EntityUtils.getPersistedValidMusician(emf));
        albumDao.create(album);
        assertEquals(albumDao.findById(album.getId()), album);

        albumDao.delete(album);
        assertNull(albumDao.findById(album.getId()));
    }

    /*@Test
    public void multiDeleteTest() {
        Album album = EntityUtils.getValidAlbum(EntityUtils.getPersistedValidMusician(emf));
        albumDao.create(album);

        Album album2 = EntityUtils.getValidAlbum(EntityUtils.getPersistedValidMusician(emf));
        albumDao.create(album2);

        assertEquals(albumDao.findAll().size(), 2);

        albumDao.delete(album);
        assertEquals(albumDao.findAll().size(), 1);
        assertEquals(albumDao.findById(album2.getId()), album2);

        albumDao.delete(album2);
        assertEquals(albumDao.findAll().size(), 0);
    }*/

    @Test()
    public void deleteInvalidTest() {
        Album album = EntityUtils.getValidAlbum(EntityUtils.getPersistedValidMusician(emf));
        Musician musician2 = EntityUtils.getValidMusician();
        musician2.setName("Jaromir Nohavica");
        TestUtils.persistObjects(emf, musician2);
        Album album2 = EntityUtils.getValidAlbum(musician2);

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
        Album album = EntityUtils.getValidAlbum(EntityUtils.getPersistedValidMusician(emf));
        albumDao.create(album);
        assertEquals(albumDao.findById(album.getId()).getTitle(), album.getTitle());

        album.setTitle("random");
        assertNotEquals(albumDao.findById(album.getId()).getTitle(), album.getTitle());
        albumDao.update(album);
        assertEquals(albumDao.findById(album.getId()).getTitle(), album.getTitle());
    }

    /*@Test
    public void multiUpdateTest() {
        Album album = EntityUtils.getValidAlbum(EntityUtils.getPersistedValidMusician(emf));
        albumDao.create(album);

        Album album2 = EntityUtils.getValidAlbum(EntityUtils.getPersistedValidMusician(emf));
        albumDao.create(album2);

        album.setTitle("random");
        albumDao.update(album);
        assertEquals(albumDao.findById(album.getId()).getTitle(), album.getTitle());
        assertEquals(albumDao.findById(album2.getId()).getTitle(), album2.getTitle());

        album2.setTitle("random #2");
        albumDao.update(album2);
        assertEquals(albumDao.findById(album.getId()).getTitle(), album.getTitle());
        assertEquals(albumDao.findById(album2.getId()).getTitle(), album2.getTitle());
    }*/

    /*@Test
    public void updateInvalidTest() {
        Album album = EntityUtils.getValidAlbum(EntityUtils.getPersistedValidMusician(emf));
        Album album2 = EntityUtils.getValidAlbum(EntityUtils.getPersistedValidMusician(emf));
        albumDao.create(album2);

        album.setTitle("random");
        albumDao.update(album);
        assertEquals(albumDao.findById(album2.getId()).getTitle(), album2.getTitle());
    }*/

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullTest() {
        albumDao.update(null);
    }

    @AfterMethod
    public void deleteData() {
        TestUtils.deleteData(emf, "Album","Musician"); 
    }
}
