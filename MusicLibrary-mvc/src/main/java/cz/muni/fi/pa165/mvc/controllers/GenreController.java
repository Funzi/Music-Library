package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.api.GenreFacade;
import cz.muni.fi.pa165.api.dto.GenreDTO;
import cz.muni.fi.pa165.mvc.Alert;
import cz.muni.fi.pa165.mvc.forms.FilterForm;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Martin Kulisek
 */
@Controller
@RequestMapping("/genres")
public class GenreController {

    final static Logger log = LoggerFactory.getLogger(GenreController.class);
    public static final String REDIRECT_GENRES = "redirect:/genres/";

    //@Autowired
    //private MessageSource messageSource; //resource bundle provided by Spring

    @Autowired
    private GenreFacade genreFacade;

    @RequestMapping("/")
    public String bar(Model model) {
        model.addAttribute("genres", genreFacade.getAllGenres());
        return "genre/list";
    }

    @RequestMapping("/")
    public String list(@ModelAttribute("form") FilterForm form, Model model) {
        return bar(model);
    }

    @RequestMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        GenreDTO g = genreFacade.getGenreById(id);
        model.addAttribute("genre", g);
        model.addAttribute("musicians", g.getName());
        model.addAttribute("description", g.getDescription());
        return "genre/details";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String add(Model model) {
        model.addAttribute("genreForm", new GenreDTO());
        return "genre/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public String doAdd(@ModelAttribute("genreForm") GenreDTO genre, RedirectAttributes redir) {
        genreFacade.createGenre(genre);
        redir.addFlashAttribute(Alert.SUCCESS, "Successfuly created");
        return REDIRECT_GENRES;
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes redir) {
        GenreDTO genre = genreFacade.getGenreById(id);

        if (genre == null) {
            redir.addFlashAttribute(Alert.ERROR, "Genre with id '" + id + "' not found in the database!");
            return REDIRECT_GENRES;
        }

        model.addAttribute("form", genreFacade.getGenreById(id));
        return "genre/edit";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public String doEdit(@PathVariable Long id, @ModelAttribute("form") GenreDTO genreForm, RedirectAttributes redir, HttpServletRequest request) {
        GenreDTO genre = genreFacade.getGenreById(id);

        if (genre == null) {
            redir.addFlashAttribute(Alert.ERROR, "Genre with id '" + id + "' not found in the database!");
            return REDIRECT_GENRES;
        }

        genre.setName(genreForm.getName());
        genre.setDescription(genreForm.getDescription());
        
        try {
            genreFacade.updateGenre(genre);
            redir.addFlashAttribute(Alert.SUCCESS, "Successfuly updated");
        } catch (Exception ex) {
            ex.printStackTrace();
            redir.addFlashAttribute(Alert.ERROR, "Unable to update genre (reason: " + ex.getMessage() + ")");
        }

        return "redirect:/genres/" + genre.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String delete(@PathVariable Long id, RedirectAttributes redir) {
        GenreDTO genre = genreFacade.getGenreById(id);

        if (genre == null) {
            redir.addFlashAttribute(Alert.ERROR, "Genre with id '" + id + "' does not exist!");
        } else {
            try {
                genreFacade.deleteGenre(genre);
                redir.addFlashAttribute(Alert.SUCCESS, "Successfuly deleted");
            } catch (Exception ex) {
                ex.printStackTrace();
                redir.addFlashAttribute(Alert.SUCCESS, "Unable to delete genre (reason: " + ex.getMessage() + ")");
            }
        }

        return "redirect:/genres/";
    }
}
