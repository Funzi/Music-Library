package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.dao.*;
import cz.muni.fi.pa165.entity.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
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

    @Mock
    private SongDao songDao;

    @Mock
    private ArtDao artDao;

    @Mock
    private AlbumRatingDao albumRatingDao;

    @Mock
    private GenreDao genreDao;

    @Mock
    private MusicianDao musicianDao;

    private Album album1;
    private Album album2;
    private Album album3;
    private Album album4;
    private Song song1;
    private Art art1;
    private AlbumRating albumRating1;
    private Genre genre1;
    private Musician musician1;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        art1 = new Art() {
            {
                setId(1L);
                setImageName("ArtName");
                setImageType("ArtType");
            }
        };

        genre1 = new Genre() {
            {
                setId(1L);
                setDescription("GenreDescription");
                setName("GenreName");
            }
        };

        musician1 = new Musician() {
            {
                setId(1L);
                setName("MusicianName");
            }
        };

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

        album4 = new Album() {
            {
                setId(4L);
                setTitle("Album4");
                setArt(art1);
            }
        };

        song1 = new Song() {
            {
                setId(1L);
                setTitle("SongTitle");
                setAlbum(album4);
                setMusician(musician1);
                setGenre(genre1);
            }
        };

        albumRating1 = new AlbumRating() {
            {
                setComment("AlbumRatingComment");
                setAlbum(album4);
            }
        };

        when(artDao.findById(art1.getId())).thenReturn(art1);
        when(genreDao.findById(genre1.getId())).thenReturn(genre1);
        when(musicianDao.findById(musician1.getId())).thenReturn(musician1);
        when(songDao.findById(song1.getId())).thenReturn(song1);
        when(albumRatingDao.findById(albumRating1.getId())).thenReturn(albumRating1);
        when(albumDao.findById(album4.getId())).thenReturn(album4);

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


    @Test
    public void testCreateOrUpdateEverything() {
        album4.addSong(song1);
        album4.addRating(albumRating1);
        Album alb = service.createOrUpdateEverything(album4);
        Album album = service.findAlbumById(alb.getId());

        assertEquals(album, alb);

    }









}
