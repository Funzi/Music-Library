package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.api.dto.GenreDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import java.util.List;

/**
 * Interface for genre implementing basic operations
 * 
 * @author David Pribula
 */
public interface GenreFacade {
    
    public Long createGenre(GenreDTO genre);
    
    public void deleteGenre(GenreDTO genre);
    
    public List<GenreDTO> getAllGenres();
    
    public List<SongDTO> getAllSongsByGenre();
    
    public GenreDTO getGenreById(Long id);
}
