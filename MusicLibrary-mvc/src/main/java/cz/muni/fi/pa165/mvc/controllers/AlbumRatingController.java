package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.api.AlbumRatingFacade;
import cz.muni.fi.pa165.api.SecurityFacade;
import cz.muni.fi.pa165.api.dto.AlbumRatingDTO;
import cz.muni.fi.pa165.mvc.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Jan Stourac
 */
@Controller
@RequestMapping("/album-rating")
public class AlbumRatingController {

	@Autowired
	private AlbumRatingFacade albumRatingFacade;

	@Autowired
	private SecurityFacade securityFacade;

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String delete(@PathVariable Long id, RedirectAttributes redir) {
		AlbumRatingDTO rating = albumRatingFacade.findById(id);

		if (rating == null) {
			redir.addFlashAttribute(Alert.ERROR, "Rating with id '" + id + "' does not exist!");
			return "redirect:/albums/";
		}

		Long albumId = rating.getAlbum().getId();
		if (!securityFacade.hasRole("admin") && !rating.getUser().getUsername().equals(securityFacade.getLoggedInUsername())) {
			redir.addFlashAttribute(Alert.ERROR, "This rating does not belongs to you and you are not an admin!");
		} else {
			try {
				albumRatingFacade.delete(rating);
				redir.addFlashAttribute(Alert.SUCCESS, "Successfuly removed!");
			} catch (Exception ex) {
				ex.printStackTrace();
				redir.addFlashAttribute(Alert.ERROR, "Unable to remove rating (reason: " + ex.getMessage() + ")");
			}
		}

		return "redirect:/albums/" + albumId;
	}
}
