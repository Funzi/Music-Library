package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.api.AlbumFacade;
import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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

	@RequestMapping("/")
	public String listAlbums(Model model) {
		Map<AlbumDTO, Set<MusicianDTO>> data = new HashMap<>();

		albumFacade.getAllAlbums().forEach(a
				-> data.put(a, a.getSongs().stream().map(s -> s.getMusician()).collect(Collectors.toSet()))
		);

		model.addAttribute("albums", data);

		return "album/list";
	}

	@RequestMapping("/{id}")
	public String albumDetail(@PathVariable Long id, Model model) {
		AlbumDTO a = albumFacade.getAlbumById(id);
		model.addAttribute("album", a);
		model.addAttribute("musicians", a.getSongs().stream().map(s -> s.getMusician()).collect(Collectors.toSet()));
		model.addAttribute("genres", a.getSongs().stream().map(s -> s.getGenre()).collect(Collectors.toSet()));

		return "album/details";
	}
}
