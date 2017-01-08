package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.ApiUris;
import cz.muni.fi.pa165.InvalidParameterException;
import cz.muni.fi.pa165.ResourceNotFoundException;
import cz.muni.fi.pa165.api.GenreFacade;
import cz.muni.fi.pa165.api.dto.GenreDTO;
import java.util.List;
import javax.inject.Inject;

import cz.muni.fi.pa165.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

	/**
	 * curl -i -X GET http://localhost:8080/pa165/rest/genres
	 *
     */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final List<GenreDTO> getGenres() {
		logger.info("rest getGenres()");
		return genreFacade.getAllGenres();
	}

	/**
	 * curl -i -X GET http://localhost:8080/pa165/rest/genres/${id}
	 * @param id
	 * @return
     */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final GenreDTO getGenre(@PathVariable("id") Long id) {
		logger.info("rest getGenre(" + id + ")");

		GenreDTO genre = genreFacade.getGenreById(id);

		if(genre == null) {
			throw new ResourceNotFoundException();
		}

		return genre;
	}

	/**
	 * curl -i -X DELETE http://localhost:8080/pa165/rest/genres/${id}
	 * @param id
     */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public final void deleteGenre(@PathVariable("id") Long id) {
		logger.info("REST, trying to delete genre id=#{}", id);
		try {
			genreFacade.deleteGenre(genreFacade.getGenreById(id));
			logger.info("REST, genre with id=#{} has been deleted", id);
		} catch (Exception ex) {
			logger.error("REST, error when deleting genre with id=#{}", id);
			throw new ResourceNotFoundException();
		}
	}

	/**
	 * curl -i -X POST -H "Content-Type: application/json" --data '{"name":"${name}","description":"${description}"}'
	 * http://localhost:8080/pa165/rest/genres/create
	 * @param genreDTO
	 * @return
     */
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public final GenreDTO createGenre(@RequestBody GenreDTO genreDTO) {
		logger.info("REST, trying to create genre");

		try {
			Long id = genreFacade.createGenre(genreDTO);
			logger.info("REST, genre created with id=#{}", id);
			return genreFacade.getGenreById(id);
		} catch (Exception ex) {
			logger.error("REST, fail to create genre={}", genreDTO);
			throw new InvalidParameterException();
		}
	}

	/**
	 * curl -i -X PUT -H "Content-Type: application/json" --data '{"name":"${name}","description":"${description}"}'
	 * http://localhost:8080/pa165/rest/genres/${id}
	 * @param id
	 * @param genreDTO
     * @return
     */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public final GenreDTO editGenre(@PathVariable("id") Long id, @RequestBody GenreDTO genreDTO) {
		logger.info("REST, trying to edit genre with id=#{}, with values genreDTO={}", id, genreDTO);
		try {
			genreDTO.setId(id);
			genreFacade.updateGenre(genreDTO);
			logger.info("REST, successfully edited genre with id=#{}", id);
			return genreFacade.getGenreById(id);
		} catch (Exception ex) {
			logger.error("REST, error when editing genre id=#{}, values={}", id, genreDTO);
			throw new InvalidParameterException();
		}
	}




}
