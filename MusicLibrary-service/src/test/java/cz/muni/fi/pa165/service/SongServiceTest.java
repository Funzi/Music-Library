/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.SongDao;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.entity.Song;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for Song service.
 *
 * @author Martin Kulisek
 */
public class SongServiceTest {

    @InjectMocks
    private SongService service = new SongServiceImpl();

    @Mock
    private SongDao songDao;

    private Song song1;
    private Song song2;
    private Song song3;

	private Musician musician1;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);

		musician1 = new Musician() {
				{
					setName("musician 1");
				}
		};

        song1 = new Song() {
            {
                setId(1L);
                setTitle("Song 1");
				setMusician(musician1);
            }
        };

        song2 = new Song() {
            {
                setId(2L);
                setTitle("Song 2");
            }
        };

        song3 = new Song() {
            {
                setId(3L);
                setTitle("Song 3");
            }
        };

        when(songDao.findById(song1.getId())).thenReturn(song1);
        when(songDao.findById(song2.getId())).thenReturn(song2);
		when(songDao.findByMusician(musician1)).thenReturn(new ArrayList<Song>() {
				{
					add(song1);
					add(song2);
				}
		});
        doThrow(PersistenceException.class).when(songDao).create(song1);
        doThrow(IllegalArgumentException.class).when(songDao).create(null);
        doThrow(IllegalArgumentException.class).when(songDao).delete(null);

        List<Song> songs = new ArrayList<Song>() {
            {
                add(song1);
                add(song2);
            }
        };
        when(songDao.findAll()).thenReturn(songs);
    }

    @Test
    public void testBaseObjectsProperties() {
        assertNotEquals(song1, song2);
        assertNotEquals(song1.getId(), song2.getId());
        assertNotEquals(song1, song3);
        assertNotEquals(song1.getId(), song3.getId());
        assertNotEquals(song2, song3);
        assertNotEquals(song2.getId(), song3.getId());
    }

    @Test
    public void testFindById() {
        assertSame(song1, service.findById(song1.getId()));
        assertSame(song2, service.findById(song2.getId()));
    }

    @Test
    public void testFindByIdNull() {
        assertNull(service.findById(song3.getId()));
    }

    @Test
    public void testFindAll() {
        List<Song> songs = service.findAll();
        assertNotNull(songs);
        assertEquals(songs.size(), 2);
        assertTrue(songs.contains(song1));
        assertTrue(songs.contains(song2));
        assertFalse(songs.contains(song3));
    }

    @Test
    public void testCreate() {
        assertEquals(song3, service.create(song3));
        verify(songDao, times(1)).create(song3);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void testCreateAlreadyExisting() {
        service.create(song1);
        verify(songDao, times(1)).create(song1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateNull() {
        service.create(null);
    }

    @Test
    public void testDelete() {
        service.delete(song1);
        verify(songDao, times(1)).delete(song1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNull() {
        service.delete(null);
    }

	@Test
	public void testGetByMusician() {
		List<Song> songs = service.getSongsForMusician(musician1);
		assertEquals(songs.size(), 2);
		assertTrue(songs.contains(song1));
		assertTrue(songs.contains(song2));
		assertFalse(songs.contains(song3));
	}

	@Test
	public void updateSongPositionTest() {
		Song song1 = new Song();
		song1.setPosition(1);
		Song song2 = new Song();
		song2.setPosition(2);
		Song song3 = new Song();
		song3.setPosition(3);
		Song song4 = new Song();
		song4.setPosition(4);
		Song song5 = new Song();
		song5.setPosition(5);
		Song song6 = new Song();
		song6.setPosition(6);

		Album album = new Album();
		album.addSong(song6);
		album.addSong(song5);
		album.addSong(song4);
		album.addSong(song3);
		album.addSong(song2);
		album.addSong(song1);

		song2.setAlbum(album);

		service.updateSongPosition(song2, 5);

		verify(songDao, times(1)).update(song2);
		verify(songDao, times(1)).update(song3);
		verify(songDao, times(1)).update(song4);
		verify(songDao, times(1)).update(song5);

		assertEquals(song1.getPosition(), 1);
		assertEquals(song2.getPosition(), 5);
		assertEquals(song3.getPosition(), 2);
		assertEquals(song4.getPosition(), 3);
		assertEquals(song5.getPosition(), 4);
		assertEquals(song6.getPosition(), 6);
	}

}
