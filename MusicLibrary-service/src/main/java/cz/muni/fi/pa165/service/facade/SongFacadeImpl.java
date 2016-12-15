/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.MusicianFacade;
import cz.muni.fi.pa165.api.SongFacade;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.api.dto.SongCreateDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.service.*;

import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Martin Kulisek
 */
@Service
@Transactional
public class SongFacadeImpl implements SongFacade {

    final static Logger LOG = LoggerFactory.getLogger(SongFacadeImpl.class);

    @Autowired
    private SongService songService;

    @Inject
    private AlbumService albumService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private MusicianService musicianService;

    @Autowired
    private GenreService genreService;

    @Override
    public Long createSong(SongCreateDTO s) {
        Song mappedSong = new Song();
        mappedSong.setTitle(s.getTitle());
        mappedSong.setBitrate(s.getBitrate());
        mappedSong.setPosition(s.getPosition());
        mappedSong.setCommentary(s.getCommentary());

        mappedSong.setAlbum(s.getAlbumId() == null ? null : albumService.findAlbumById(s.getAlbumId()));
        mappedSong.setMusician(s.getMusicianId() == null ? null : musicianService.findById(s.getMusicianId()));
        mappedSong.setGenre(s.getGenreId() == null ? null : genreService.findById(s.getGenreId()));
        mappedSong.setId(null);
        mappedSong = songService.create(mappedSong);

        return mappedSong.getId();
    }

    @Override
    public void deleteSong(Long id) {
        Song song = songService.findById(id);
        song.setAlbum(null);
        song.setMusician(null);
        song.setGenre(null);
        songService.delete(song);
    }

    @Override
    public void assignSongToAlbum(Long song_id, Long album_id) {
        Album album = albumService.findAlbumById(album_id);
        if (album != null) {
            songService.assignSongToAlbum(song_id, album);
        }
    }

    @Override
    public List<SongDTO> getAllSongs() {
        return beanMappingService.mapTo(songService.findAll(), SongDTO.class);
    }

    @Override
    public SongDTO getSongById(Long id) {
        Song song = songService.findById(id);
        return (song == null) ? null : beanMappingService.mapTo(song, SongDTO.class);
    }

    @Override
    public List<SongDTO> getSongsForMusician(MusicianDTO musician) {
        return beanMappingService.mapTo(songService.getSongsForMusician(beanMappingService.mapTo(musician, Musician.class)), SongDTO.class);
    }

    @Override
    public void updateSongPosition(SongDTO song, int newPosition) {
        songService.updateSongPosition(beanMappingService.mapTo(song, Song.class), newPosition);
    }

}
