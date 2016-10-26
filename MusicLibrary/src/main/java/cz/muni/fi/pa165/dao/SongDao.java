package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Song;
import java.util.List;

/**
 *
 * @author David Pribula
 */
public interface SongDao {

    void createSong(Song song);
    
    void updateSong(Song song);
    
    void deleteSong(Song song);
    
    void findSongById(Long id);
    
    List<Song> getAllSongs();
}
