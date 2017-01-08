package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.mvc.Alert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Jan Stourac
 */
@Controller
public class AuthController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username or password is invalid.");
        else
            model.addAttribute("error",null);

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

	@RequestMapping(value = "/login-ok", method = RequestMethod.GET)
    public String loginOK(@RequestParam String target, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(Alert.SUCCESS, "Successfuly logged in!");

        return "redirect:" + target;
    }

	@RequestMapping(value = "/logout-ok", method = RequestMethod.GET)
    public String logoutOK(@RequestParam String target, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(Alert.SUCCESS, "Successfuly logged out!");

        return "redirect:" + target;
    }
}