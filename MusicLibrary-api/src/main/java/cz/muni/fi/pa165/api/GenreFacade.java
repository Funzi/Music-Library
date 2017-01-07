package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.api.dto.GenreDTO;
import java.util.List;

/**
 * Interface for genre implementing basic operations
 *
 * @author David Pribula
 */
public interface GenreFacade {

    Long createGenre(GenreDTO genre);

    void deleteGenre(GenreDTO genre);

    List<GenreDTO> getAllGenres();

    GenreDTO getGenreById(Long id);

    void updateGenre(GenreDTO genre);
}
