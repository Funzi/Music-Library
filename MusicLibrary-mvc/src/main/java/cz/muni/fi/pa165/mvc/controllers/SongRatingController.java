/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.api.SecurityFacade;
import cz.muni.fi.pa165.api.SongRatingFacade;
import cz.muni.fi.pa165.api.dto.SongRatingDTO;
import cz.muni.fi.pa165.mvc.Alert;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/song-rating")
public class SongRatingController {

    @Autowired
    private SongRatingFacade songRatingFacade;

    @Autowired
    private SecurityFacade securityFacade;

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable Long id, RedirectAttributes redir) {
        SongRatingDTO rating = songRatingFacade.findById(id);

        if (rating == null) {
            redir.addFlashAttribute(Alert.ERROR, "Rating with id '" + id + "' does not exist!");
            return "redirect:/songs/";
        }

        Long songId = rating.getSong().getId();
        if (!securityFacade.hasRole("admin") && !rating.getUser().getUsername().equals(securityFacade.getLoggedInUsername())) {
            redir.addFlashAttribute(Alert.ERROR, "This rating isn't yours and you are not an admin!");
        } else {
            try {
                songRatingFacade.delete(rating);
                redir.addFlashAttribute(Alert.SUCCESS, "Successfuly removed!");
            } catch (Exception ex) {
                ex.printStackTrace();
                redir.addFlashAttribute(Alert.ERROR, "Unable to remove rating (reason: " + ex.getMessage() + ")");
            }
        }

        return "redirect:/songs/" + songId;
    }

    public static Map<Double, String> allowedValues() {
        return new LinkedHashMap<Double, String>() {
            {
                put(0.0, "0 stars");
                put(0.5, "0.5 star");
                put(1.0, "1 star");
                put(1.5, "1.5 stars");
                put(2.0, "2 stars");
                put(2.5, "2.5 stars");
                put(3.0, "3 stars");
                put(3.5, "3.5 stars");
                put(4.0, "4 stars");
                put(4.5, "4.5 stars");
                put(5.0, "5 stars");
            }
        };
    }
}
