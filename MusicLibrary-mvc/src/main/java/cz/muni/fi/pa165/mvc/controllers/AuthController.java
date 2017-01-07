/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.mvc.Alert;
import cz.muni.fi.pa165.service.SecurityService;
import cz.muni.fi.pa165.service.UserService;
import cz.muni.fi.pa165.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.create(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

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