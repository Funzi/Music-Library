/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.AlbumFacade;
import cz.muni.fi.pa165.api.MusicianFacade;
import cz.muni.fi.pa165.api.SongFacade;
import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.api.dto.SongCreateDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.config.ServiceConfiguration;
import java.util.List;
import javax.transaction.Transactional;

import cz.muni.fi.pa165.entity.Song;
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
 * Unit tests for facade layer of Song
 *
 * @author Martin Kulisek
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class SongFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SongFacade songFacade;

    @Autowired
    private AlbumFacade albumFacade;

    @Autowired
    private MusicianFacade musicianFacade;

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
        Long id = songFacade.createSong(convertToSongCreateDTO(song1));
        assertNotEquals(id, 0L);
    }

    @Test
    public void getByIdTest() {
        Long id1 = songFacade.createSong(convertToSongCreateDTO(song1));
        Long id2 = songFacade.createSong(convertToSongCreateDTO(song2));

        assertEquals(song1.getTitle(), songFacade.getSongById(id1).getTitle());
        assertEquals(song2.getTitle(), songFacade.getSongById(id2).getTitle());
    }

    @Test
    public void getAllSongsTest() {
        songFacade.createSong(convertToSongCreateDTO(song1));
        songFacade.createSong(convertToSongCreateDTO(song2));

        List<SongDTO> songs = songFacade.getAllSongs();
        assertEquals(songs.size(), 2);
        assertEquals(songs.get(0).getTitle(), song1.getTitle());
        assertEquals(songs.get(1).getTitle(), song2.getTitle());
    }

    @Test
    public void deleteSongTest() {
        Long id1 = songFacade.createSong(convertToSongCreateDTO(song1));
        Long id2 = songFacade.createSong(convertToSongCreateDTO(song2));

        songFacade.deleteSong(id1);
        assertNull(songFacade.getSongById(id1));
        assertEquals(songFacade.getAllSongs().size(), 1);
        assertEquals(songFacade.getSongById(id2).getTitle(), song2.getTitle());
    }

    @Test
    public void updateSongPositionWithoutAlbumTest() {
        song1.setPosition(2);
        Long id = songFacade.createSong(convertToSongCreateDTO(song1));

        songFacade.updateSongPosition(songFacade.getSongById(id), 3);
        assertEquals(songFacade.getAllSongs().size(), 1);
        assertEquals(songFacade.getSongById(id).getPosition(), 3);
    }

    @Test
    public void assignSongToAlbumTest() {
        AlbumDTO album = new AlbumDTO();
        album.setTitle("Album");

        Long id = songFacade.createSong(convertToSongCreateDTO(song1));
        Long albumId = albumFacade.createAlbum(album);
        songFacade.assignSongToAlbum(id, albumId);

        assertEquals(songFacade.getAllSongs().size(), 1);
        assertEquals(songFacade.getSongById(id).getAlbum().getTitle(), "Album");
    }

    @Test
    public void getSongsForMusicianTest() {
        MusicianDTO musician = new MusicianDTO();
        musician.setName("Musician");
        MusicianDTO persistedMusician = musicianFacade.getMusicianById(musicianFacade.createMusician(musician));

        song1.setMusician(persistedMusician);
        songFacade.createSong(convertToSongCreateDTO(song1));

        assertEquals(songFacade.getSongsForMusician(persistedMusician).size(), 1);
        assertEquals(songFacade.getSongsForMusician(persistedMusician).get(0).getMusician().getName(), "Musician");
    }

    private SongCreateDTO convertToSongCreateDTO(SongDTO s) {
        SongCreateDTO sd = new SongCreateDTO();
        sd.setTitle(s.getTitle());
        sd.setBitrate(s.getBitrate());
        sd.setCommentary(s.getCommentary());
        sd.setPosition(s.getPosition());
        sd.setAlbumId(s.getAlbum() != null ? s.getAlbum().getId() : null);
        sd.setMusicianId(s.getMusician() != null ? s.getMusician().getId() : null);
        sd.setGenreId(s.getGenre() != null ? s.getGenre().getId() : null);
        return sd;
    }
}
