/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.entity.Song;
import java.util.List;

/**
 * Service layer of Song entity
 *
 * @author Martin Kulisek
 */
public interface SongService {

    public Song findById(Long id);

    public List<Song> findAll();

    void assignSongToAlbum(Long song_id, Album album);

    public Song create(Song p);

    public void delete(Song p);

    List<Song> getSongsForMusician(Musician musician);

    void updateSongPosition(Song song, int newPosition);

    public List<Song> getSongsForGenre(Genre genre);
}
