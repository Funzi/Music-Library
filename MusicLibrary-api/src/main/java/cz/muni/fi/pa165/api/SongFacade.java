/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.api.dto.GenreDTO;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.api.dto.SongCreateDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import java.util.List;

/**
 *
 * @author Martin Kulisek
 */
public interface SongFacade {

    public Long createSong(SongCreateDTO s);

    public void deleteSong(Long id);

    public void assignSongToAlbum(Long song_id, Long album_id);

    public List<SongDTO> getAllSongs();

    public SongDTO getSongById(Long id);

    List<SongDTO> getSongsForMusician(MusicianDTO musician);

    void updateSongPosition(SongDTO song, int newPosition);

    void updateSong(SongCreateDTO s);

    public List<SongDTO> getSongsForGenre(GenreDTO genre);

}
