/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.SongFacade;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.service.AlbumService;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.GenreService;
import cz.muni.fi.pa165.service.MusicianService;
import cz.muni.fi.pa165.service.SongService;
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

    @Inject
    private MusicianService musicianService;

    @Inject
    private GenreService genreService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long createSong(SongDTO s) {
        Song mappedSong = beanMappingService.mapTo(s, Song.class);
        mappedSong.setId(null);
        mappedSong = songService.create(mappedSong);
        return mappedSong.getId();

        /*Song mappedSong = beanMappingService.mapTo(s, Song.class);
        mappedSong.setAlbum(albumService.findAlbumById(s.getAlbumId()));
        mappedSong.setBitrate(s.getBitrate());
        mappedSong.setCommentary(s.getCommentary());
        mappedSong.setGenre(genreService.findById(s.getGenreId()));
        mappedSong.setMusician(musicianService.findById(s.getMusicianId()));
        mappedSong.setPosition(s.getPosition());
        mappedSong.setTitle(s.getTitle());
        Song newSong = songService.create(mappedSong);
        return newSong.getId();*/
    }

    @Override
    public void deleteSong(Long id) {
        songService.delete(songService.findById(id));
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



}
