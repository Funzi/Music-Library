package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.api.GenreFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Example controller showing as much features as possible.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@Controller
@RequestMapping("/genres")
public class GenreController {

    final static Logger log = LoggerFactory.getLogger(GenreController.class);

    @Autowired
    private MessageSource messageSource; //resource bundle provided by Spring

	@Autowired
	private GenreFacade genreFacade;

    @RequestMapping("/")
    public String bar(Model model) {
        model.addAttribute("genres", genreFacade.getAllGenres());
        return "genre/list";
    }
}
