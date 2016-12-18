package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.api.GenreFacade;
import cz.muni.fi.pa165.api.SongFacade;
import cz.muni.fi.pa165.api.dto.GenreDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.mvc.Alert;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for genre
 *
 * @author Martin Kulisek
 */
@Controller
@RequestMapping("/genres")
public class GenreController {

    final static Logger log = LoggerFactory.getLogger(GenreController.class);
    public static final String REDIRECT_GENRES = "redirect:/genres/";

    @Autowired
    private GenreFacade genreFacade;

    @Autowired
    private SongFacade songFacade;

    @RequestMapping("/")
    public String bar(Model model) {
        log.info("Getting all genres for list");
        model.addAttribute("genres", genreFacade.getAllGenres());
        return "genre/list";
    }

    @RequestMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        log.info("Getting details for genre with id={}", id);
        GenreDTO g = genreFacade.getGenreById(id);
        model.addAttribute("genre", g);
        model.addAttribute("name", g.getName());
        model.addAttribute("description", g.getDescription());
        List<SongDTO> songDTOList = songFacade.getAllSongs().stream().filter(s -> s.getGenre().equals(g)).collect(Collectors.toList());
        model.addAttribute("songs", songDTOList);
        return "genre/details";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String add(Model model) {
        log.info("Preparing for creating a new genre");
        model.addAttribute("genreForm", new GenreDTO());
        return "genre/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public String doAdd(@ModelAttribute("genreForm") GenreDTO genre, RedirectAttributes redir) {
        log.info("Creating new genre");
        if (!(genre.getName().trim().length() > 0)) {
            log.error("Name was not set in the form");
            redir.addFlashAttribute(Alert.ERROR, "You have to fill the name");
            return REDIRECT_GENRES + "add";
        } else {
            genreFacade.createGenre(genre);
            log.info("Successfully created genre");
            redir.addFlashAttribute(Alert.SUCCESS, "Successfuly created");
        }
        return REDIRECT_GENRES;
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes redir) {
        log.info("Trying to get genre with id={} for edit", id);
        GenreDTO genre = genreFacade.getGenreById(id);

        if (genre == null) {
            log.error("Cannot find genre with id={} in database", id);
            redir.addFlashAttribute(Alert.ERROR, "Genre with id '" + id + "' not found in the database!");
            return REDIRECT_GENRES;
        }

        model.addAttribute("form", genreFacade.getGenreById(id));
        log.info("Successfully got genre with id={} for edit", id);
        return "genre/edit";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public String doEdit(@PathVariable Long id, @ModelAttribute("form") GenreDTO genreForm, RedirectAttributes redir, HttpServletRequest request) {
        log.info("Trying to update genre={}", genreForm);
        GenreDTO genre = genreFacade.getGenreById(id);

        if (genre == null) {
            log.error("Cannot find genre with id={} in database", id);
            redir.addFlashAttribute(Alert.ERROR, "Genre with id '" + id + "' not found in the database!");
            return REDIRECT_GENRES;
        }

        if (!(genreForm.getName().trim().length() > 0)) {
            log.error("Name was not set in the form");
            redir.addFlashAttribute(Alert.ERROR, "You have to fill the name");
            return REDIRECT_GENRES + "{id}/edit";
        } else {
            genre.setName(genreForm.getName());
            genre.setDescription(genreForm.getDescription());

            try {
                genreFacade.updateGenre(genre);
                log.info("Successfully updated genre={}", genre);
                redir.addFlashAttribute(Alert.SUCCESS, "Successfuly updated");
            } catch (Exception ex) {
                log.error("Unable to update genre={} because: {}", genre, ex);
                redir.addFlashAttribute(Alert.ERROR, "Unable to update genre (reason: " + ex.getMessage() + ")");
            }
        }

        return REDIRECT_GENRES + genre.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String delete(@PathVariable Long id, RedirectAttributes redir) {
        log.info("Trying to delete genre with id={}", id);
        GenreDTO genre = genreFacade.getGenreById(id);

        if (genre == null) {
            log.error("Cannot find genre with id={} in database", id);
            redir.addFlashAttribute(Alert.ERROR, "Genre with id '" + id + "' does not exist!");
        } else {

            try {
                if (songFacade.getSongsForGenre(genre).size() > 0) {
                    log.error("Unable to delete genre={} because of association with existing song", genre);
                    redir.addFlashAttribute(Alert.ERROR, "You need to delete all songs connected to this genre first.");
                } else {
                    genreFacade.deleteGenre(genre);
                    log.info("Successfully deleted genre={}", genre);
                    redir.addFlashAttribute(Alert.SUCCESS, "Successfuly deleted");
                }
            } catch (Exception ex) {
                log.error("Unable to delete genre={} because: {}", genre, ex);
                redir.addFlashAttribute(Alert.ERROR, "Unable to delete genre (reason: " + ex.getMessage() + ")");
            }
        }
        return REDIRECT_GENRES;
    }
}
