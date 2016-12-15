package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.api.AlbumFacade;
import cz.muni.fi.pa165.api.GenreFacade;
import cz.muni.fi.pa165.api.MusicianFacade;
import cz.muni.fi.pa165.api.SongFacade;
import cz.muni.fi.pa165.api.dto.*;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.mvc.Alert;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 *
 * @author Jan Stourac
 */
@Controller
@RequestMapping("/songs")
public class SongController {

	final static Logger log = LoggerFactory.getLogger(SongController.class);

	public static final String REDIRECT_SONGS = "redirect:/songs/";

	@Autowired
	private SongFacade songFacade;

	@Autowired
	private MusicianFacade musicianFacade;

	@Autowired
	private AlbumFacade albumFacade;

	@Autowired
	private GenreFacade genreFacade;

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

	@RequestMapping(value = "/")
	public String list(@RequestParam(value = "genre", required = false) Long genreId, Model model) {
		List<SongDTO> songDTOList;
		if (genreId != null) {
			GenreDTO genreDTO = genreFacade.getGenreById(genreId);
			songDTOList = songFacade.getAllSongs().stream().filter(s -> s.getGenre().equals(genreDTO)).collect(Collectors.toList());
		}else {
			songDTOList = songFacade.getAllSongs();
		}
		model.addAttribute("songDTOList",songDTOList);

		return "songs/list";
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable Long id, Model model, RedirectAttributes redir) {
		SongDTO songDTO = songFacade.getSongById(id);

		if (songDTO == null) {
			redir.addFlashAttribute(Alert.ERROR, "Song with #id=" + id +" not found in database");
			return REDIRECT_SONGS;
		} else {
			model.addAttribute("songDTO", songDTO);
		}

		return "songs/detail";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('admin')")
	public String add(Model model) {

		model.addAttribute("songForm", new SongCreateDTO());
		model.addAttribute("allMusicians", musicianFacade.getAllMusicians());
		model.addAttribute("allAlbums", albumFacade.getAllAlbums());
		model.addAttribute("allGenres", genreFacade.getAllGenres());
		return "songs/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('admin')")
	public String doAdd(@ModelAttribute("songForm") SongCreateDTO songCreateDTO, RedirectAttributes redirect) {
		Long id = songFacade.createSong(songCreateDTO);
		redirect.addFlashAttribute(Alert.SUCCESS, "Song Successfully created #id=" + id);

		return REDIRECT_SONGS;
	}

	@RequestMapping(value = "/list/{id}/musician", method = RequestMethod.GET)
	public String listForMusician(@PathVariable Long id, Model model, RedirectAttributes redir) {
		MusicianDTO musicianDTO = musicianFacade.getMusicianById(id);
		if (musicianDTO == null) {
			redir.addFlashAttribute(Alert.ERROR, "Musician with #id=" + id + " not found in database");
			return REDIRECT_SONGS;
		}

		List<SongDTO> songDTOList = songFacade.getSongsForMusician(musicianDTO);
		model.addAttribute("songDTOList", songDTOList);
		return "songs/listForMusician";
	}

	@RequestMapping(value = "/addSongToAlbum/{songId}/{albumId}", method = RequestMethod.POST)
	public String addSongToAlbum(@PathVariable Long songId, @PathVariable Long albumId, RedirectAttributes redir) {
		if (songFacade.getSongById(songId) == null) {
			redir.addFlashAttribute(Alert.ERROR, "Song with #id=" + songId + " not found in database");
			return REDIRECT_SONGS;
		}
		if (albumFacade.getAlbumById(albumId) == null) {
			redir.addFlashAttribute(Alert.ERROR, "Album with #id=" + albumId + " not found in database");
			return REDIRECT_SONGS;
		}

		try {
			songFacade.assignSongToAlbum(songId, albumId);
		}catch (Exception ex) {
			redir.addFlashAttribute(Alert.ERROR, "Cannot assign song #id=" + songId+ " to album #id=" + albumId);
			return REDIRECT_SONGS;
		}
		redir.addFlashAttribute(Alert.SUCCESS, "Song with #id=" + songId + " has been assigned to album #id=" + albumId);
		return REDIRECT_SONGS;
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable Long id, RedirectAttributes redir) {
		if (songFacade.getSongById(id) == null) {
			redir.addFlashAttribute(Alert.ERROR, "Song with #id=" + id + " not found in database");
			return REDIRECT_SONGS;
		}

		try {
			songFacade.deleteSong(id);
			songFacade.deleteSong(id);
			redir.addFlashAttribute(Alert.SUCCESS, "Song with #id=" + id + " has been deleted");
			return REDIRECT_SONGS;
		}catch (Exception ex) {
			redir.addFlashAttribute(Alert.ERROR, "Cannot delete Song with #id=" + id);
			return REDIRECT_SONGS;
		}

	}









}
