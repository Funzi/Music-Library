/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.api.AlbumFacade;
import cz.muni.fi.pa165.api.MusicianFacade;
import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.mvc.Alert;
import static cz.muni.fi.pa165.mvc.controllers.SongController.REDIRECT_SONGS;
import cz.muni.fi.pa165.mvc.forms.FilterForm;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author David Pribula
 */
@Controller
@RequestMapping("/musicians")
public class MusicianController {
    final static Logger log = LoggerFactory.getLogger(MusicianController.class);
    
    public static final String REDIRECT_MUSICIANS = "redirect:/musicians/";
   
    @Autowired
    private MusicianFacade musicianFacade;
    
    @Autowired
    private AlbumFacade albumFacade;
    
    @RequestMapping("/")
    public String list(Model model) {
        model.addAttribute("musicians", musicianFacade.getAllMusicians());
        
        return "musicians/list";
    }
    
    @RequestMapping("/{id}")
	public String detail(@PathVariable Long id, Model model) {
            MusicianDTO musician = musicianFacade.getMusicianById(id);
            List<AlbumDTO> albums = albumFacade.getAlbumByMusician(musician);

		model.addAttribute("albums", albums);
                model.addAttribute("musician",musician);
		return "musicians/details";
	}
        
        @RequestMapping(value = "/add", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('admin')")
	public String add(Model model) {
		model.addAttribute("musicianForm", new MusicianDTO());
		return "musicians/add";
	}
        
        @RequestMapping(value = "/add", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('admin')")
	public String doAdd(@ModelAttribute("musicianForm") MusicianDTO musician, RedirectAttributes redir) {
		musicianFacade.createMusician(musician);
		redir.addFlashAttribute(Alert.SUCCESS, "Successfuly created");
		return REDIRECT_MUSICIANS;
	}
}
