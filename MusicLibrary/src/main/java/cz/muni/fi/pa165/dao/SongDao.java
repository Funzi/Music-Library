package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Song;
import java.util.List;

/**
 *
 * @author David Pribula
 */
public interface SongDao {

    void create(Song song);
    
    void update(Song song);
    
    void delete(Song song);
    
    void findById(Long id);
    
    List<Song> findAll();
}
