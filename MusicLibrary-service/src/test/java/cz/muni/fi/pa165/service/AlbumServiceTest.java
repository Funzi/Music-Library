package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.AlbumDao;
import cz.muni.fi.pa165.entity.Album;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Created by olda on 24.11.2016.
 */
public class AlbumServiceTest {

    @InjectMocks
    private AlbumService service = new AlbumServiceImpl();

    @Mock
    private AlbumDao albumDao;

    private Album album1;
    private Album album2;
    private Album album3;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        album1 = new Album() {
            {
                setId(1L);
                setTitle("Album1");
                setReleaseDate(LocalDate.of(2011, 1, 1));
                setCommentary("Album1Commentary");
            }
        };

        album2 = new Album() {
            {
                setId(2L);
                setTitle("Album2");
                setReleaseDate(LocalDate.of(2012, 1, 1));
                setCommentary("Album2Commentary");
            }
        };

        album3 = new Album() {
            {
                setId(3L);
                setTitle("Album3");
                setReleaseDate(LocalDate.of(2013, 1, 1));
                setCommentary("Album3Commentary");
            }
        };

        when(albumDao.findById(album1.getId())).thenReturn(album1);
        when(albumDao.findById(album2.getId())).thenReturn(album2);

        List<Album> list = new ArrayList<Album>() {
            {
                add(album1);
                add(album2);
            }
        };
        when(albumDao.findAll()).thenReturn(list);

        doThrow(PersistenceException.class).when(albumDao).create(album1);
        doThrow(IllegalArgumentException.class).when(albumDao).create(null);
        doThrow(IllegalArgumentException.class).when(albumDao).delete(null);

    }

    @Test
    public void testBaseObjectProperties() {
        assertNotEquals(album1, album2);
        assertNotEquals(album1.getId(), album2.getId());
        assertNotEquals(album1, album3);
        assertNotEquals(album1.getId(), album3.getId());
        assertNotEquals(album3, album2);
        assertNotEquals(album3.getId(), album2.getId());
    }

    @Test
    public void testFindById() {
        assertEquals(service.findAlbumById(album1.getId()), album1);
        assertEquals(service.findAlbumById(album2.getId()), album2);
    }

    @Test
    public void testFindByIdNull() {
        assertNull(service.findAlbumById(album3.getId()));
    }

    @Test
    public void testFindAll() {
        List<Album> list = service.findAllAlbums();
        assertNotNull(list);
        assertTrue(list.size() == 2);
        assertTrue(list.contains(album1));
        assertTrue(list.contains(album2));
        assertFalse(list.contains(album3));
    }

    @Test
    public void testCreate() {
        assertEquals(album3, service.createAlbum(album3));
        verify(albumDao, times(1)).create(album3);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void testCreateAlreadyExisting() {
        service.createAlbum(album1);
        verify(albumDao, times(1)).create(album3);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateNull() {
        service.createAlbum(null);
    }

    @Test
    public void testDelete() {
        service.deleteAlbum(album1);
        verify(albumDao, times(1)).delete(album1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNull() {
        service.deleteAlbum(null);
    }












}
