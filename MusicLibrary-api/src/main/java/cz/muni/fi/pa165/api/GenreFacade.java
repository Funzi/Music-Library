package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.api.dto.GenreDTO;
import java.util.List;

/**
 * Interface for genre implementing basic operations
 * 
 * @author David Pribula
 */
public interface GenreFacade {
    
    
    public List<GenreDTO> getAllGenres();
    
    //public List<SongDTO> getAllSongsByGenre();
    
    public GenreDTO getGenreById(Long id);
}
