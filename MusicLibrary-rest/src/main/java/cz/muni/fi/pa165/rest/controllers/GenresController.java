package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.ApiUris;
import cz.muni.fi.pa165.ResourceNotFoundException;
import cz.muni.fi.pa165.api.GenreFacade;
import cz.muni.fi.pa165.api.dto.GenreDTO;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jan Stourac
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_GENRES)
public class GenresController {

	final static Logger logger = LoggerFactory.getLogger(GenresController.class);

	@Inject
	private GenreFacade genreFacade;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final List<GenreDTO> getGenres() {
		logger.info("rest getGenres()");
		return genreFacade.getAllGenres();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final GenreDTO getGenre(@PathVariable("id") Long id) {
		logger.info("rest getGenre(" + id + ")");

		GenreDTO genre = genreFacade.getGenreById(id);

		if(genre == null) {
			throw new ResourceNotFoundException();
		}

		return genre;
	}

}
