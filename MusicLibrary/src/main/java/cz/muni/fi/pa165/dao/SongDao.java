package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Song;
import java.util.List;

/**
 * Data access object layer interface for Song entity.
 * @author David Pribula
 */
public interface SongDao {
    
    /**
     * Stores given song in database.
     *
     * @param song song to store
     */
    void create(Song song);

    /**
     * Updates given song in database.
     *
     * @param song song to update
     * @return song updated instance
     */
    Song update(Song song);

    /**
     * Deletes given song from database.
     *
     * @param song song to delete
     */
    void delete(Song song);

    /**
     * Returns song with given id or null.
     *
     * @param id song id
     * @return song with given id
     */
    Song findById(Long id);

    /**
     * Returns all songs from database.
     *
     * @return list containing all songs
     */
    List<Song> findAll();
}
