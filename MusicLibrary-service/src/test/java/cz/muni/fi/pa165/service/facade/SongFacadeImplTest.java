/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.SongFacade;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.config.ServiceConfiguration;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Martin Kulisek
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class SongFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SongFacade songFacade;

    private SongDTO song1;
    private SongDTO song2;

    @BeforeMethod
    public void setUp() {
        song1 = new SongDTO() {
            {
                setTitle("Song 1");
            }
        };

        song2 = new SongDTO() {
            {
                setTitle("Song 2");
            }
        };

        assertNotEquals(song1.getTitle(), song2.getTitle());
    }

    @Test
    public void createSongTest() {
        Long id = songFacade.createSong(song1);
        assertNotEquals(id, 0L);
    }

    @Test
    public void getByIdTest() {
        Long id1 = songFacade.createSong(song1);
        Long id2 = songFacade.createSong(song2);

        assertEquals(song1.getTitle(), songFacade.getSongById(id1).getTitle());
        assertEquals(song2.getTitle(), songFacade.getSongById(id2).getTitle());
    }

    @Test
    public void getAllSongsTest() {
        songFacade.createSong(song1);
        songFacade.createSong(song2);

        List<SongDTO> songs = songFacade.getAllSongs();
        assertEquals(songs.size(), 2);
        assertEquals(songs.get(0).getTitle(), song1.getTitle());
        assertEquals(songs.get(1).getTitle(), song2.getTitle());
    }

    @Test
    public void deleteSongTest() {
        Long id1 = songFacade.createSong(song1);
        Long id2 = songFacade.createSong(song2);

        songFacade.deleteSong(id1);
        assertNull(songFacade.getSongById(id1));
        assertEquals(songFacade.getAllSongs().size(), 1);
        assertEquals(songFacade.getSongById(id2).getTitle(), song2.getTitle());
    }
}
