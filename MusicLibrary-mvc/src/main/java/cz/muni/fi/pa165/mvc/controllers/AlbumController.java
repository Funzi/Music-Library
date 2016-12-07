package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.api.AlbumFacade;
import cz.muni.fi.pa165.api.AlbumRatingFacade;
import cz.muni.fi.pa165.api.SecurityFacade;
import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.AlbumRatingDTO;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.mvc.Alert;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Example controller showing as much features as possible.
 *
 * @author Jan Stourac
 */
@Controller
@RequestMapping("/albums")
public class AlbumController {

	final static Logger log = LoggerFactory.getLogger(AlbumController.class);

	@Autowired
	private MessageSource messageSource; //resource bundle provided by Spring

	@Autowired
	private AlbumFacade albumFacade;

	@Autowired
	private AlbumRatingFacade albumRatingFacade;

	@Autowired
	private SecurityFacade securityFacade;

	@RequestMapping("/")
	public String list(Model model) {
		Map<AlbumDTO, Set<MusicianDTO>> data = new HashMap<>();

		albumFacade.getAllAlbums().forEach(a
				-> data.put(a, a.getSongs().stream().map(s -> s.getMusician()).collect(Collectors.toSet()))
		);

		model.addAttribute("albums", data);

		return "album/list";
	}

	@RequestMapping("/{id}")
	public String detail(@PathVariable Long id, Model model) {
		AlbumDTO a = albumFacade.getAlbumById(id);
		model.addAttribute("album", a);
		model.addAttribute("musicians", a.getSongs().stream().map(s -> s.getMusician()).collect(Collectors.toSet()));
		model.addAttribute("genres", a.getSongs().stream().map(s -> s.getGenre()).collect(Collectors.toSet()));
		model.addAttribute("ratings", a.getRatings());
		model.addAttribute("ratingForm", new AlbumRatingDTO());

		return "album/details";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('admin')")
	public String edit(@PathVariable Long id, Model model) {
		model.addAttribute("form", albumFacade.getAlbumById(id));

		return "album/edit";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('admin')")
	public String doEdit(@PathVariable Long id, @ModelAttribute("form") AlbumDTO album, RedirectAttributes redir) {
		albumFacade.updateAlbum(album);
		redir.addFlashAttribute(Alert.SUCCESS, "Successfuly updated");
		return "redirect:/albums/";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('admin')")
	public String delete(@PathVariable Long id, RedirectAttributes redir) {
		AlbumDTO album = albumFacade.getAlbumById(id);

		if (album == null) {
			redir.addFlashAttribute(Alert.ERROR, "Album with id '" + id + "' does not exist!");
		} else {
			try {
				albumFacade.deleteAlbum(album);
				redir.addFlashAttribute(Alert.SUCCESS, "Successfuly deleted");
			} catch (Exception ex) {
				//TODO: Logging
				ex.printStackTrace();
				redir.addFlashAttribute(Alert.SUCCESS, "Unable to delete album (reason: " + ex.getMessage() + ")");
			}
		}

		return "redirect:/albums/";
	}

	//FIXME: - Fix constraint violation exception
	@RequestMapping(value = "/{id}/rate", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String rate(@PathVariable Long id, @ModelAttribute("ratingForm") AlbumRatingDTO rating, RedirectAttributes redir) {
		AlbumDTO album = albumFacade.getAlbumById(id);

		if (album == null) {
			redir.addFlashAttribute(Alert.ERROR, "Album with id '" + id + "' does not exist!");
		} else {
			try {
				rating.setAlbum(album);
				rating.setUser(securityFacade.getLoggedInUser());
				albumRatingFacade.create(rating);
				redir.addFlashAttribute(Alert.SUCCESS, "Successfuly rated!");
			} catch (Exception ex) {
				//TODO: Logging
				ex.printStackTrace();
				redir.addFlashAttribute(Alert.SUCCESS, "Unable to save rating (reason: " + ex.getMessage() + ")");
			}
		}

		return "redirect:/albums/" + id;
	}
}
