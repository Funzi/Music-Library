package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.api.SecurityFacade;
import cz.muni.fi.pa165.api.dto.UserDTO;
import cz.muni.fi.pa165.mvc.Alert;
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
 * Profile controller.
 *
 * @author Jan Stourac
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

	final static Logger log = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	private MessageSource messageSource; //resource bundle provided by Spring

	@Autowired
	private SecurityFacade securityFacade;

	@RequestMapping("/wishlist")
	@PreAuthorize("isAuthenticated()")
	public String wishlist(Model model, RedirectAttributes redir) {
		log.info("Getting wishlist");

		UserDTO user = securityFacade.getLoggedInUser();

		if (user == null) {
			redir.addFlashAttribute(Alert.ERROR, "You are not logged in!");
			return "redirect:/";
		}

		model.addAttribute("wishlist", user.getWishlist());

		return "profile/wishlist";
	}
}
