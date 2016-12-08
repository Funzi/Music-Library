package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.api.SongFacade;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.mvc.Alert;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Jan Stourac
 */
@Controller
@RequestMapping("/songs")
public class SongController {

	@Autowired
	private SongFacade songFacade;

	@RequestMapping(value = "/update-position", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('admin')")
	public String doEdit(@RequestParam Long song, @RequestParam Integer position, RedirectAttributes redir, HttpServletRequest request) {
		SongDTO songDTO = songFacade.getSongById(song);

		if (songDTO == null) {
			redir.addFlashAttribute(Alert.ERROR, "Song with id '" + song + "' not found in the database!");
			return AlbumController.REDIRECT_ALBUMS;
		}

		try {
			songFacade.updateSongPosition(songDTO, position);
			redir.addFlashAttribute(Alert.SUCCESS, "Song position successfuly updated");
		} catch(Exception ex) {
			//TODO: logging
			ex.printStackTrace();
			redir.addFlashAttribute(Alert.ERROR, "Unable to update song position (reason: " + ex.getMessage() + ")!");
		}

		return "redirect:/albums/" + songDTO.getAlbum().getId();
	}
}
