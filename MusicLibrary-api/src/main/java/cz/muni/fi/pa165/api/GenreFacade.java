package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.api.dto.GenreDTO;
import java.util.List;

/**
 * Interface for genre implementing basic operations
 * 
 * @author David Pribula
 */
public interface GenreFacade {
    
    
    List<GenreDTO> getAllGenres();
    
    //List<SongDTO> getAllSongsByGenre();
    
    GenreDTO getGenreById(Long id);
}
