package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.api.AlbumFacade;
import cz.muni.fi.pa165.api.GenreFacade;
import cz.muni.fi.pa165.api.MusicianFacade;
import cz.muni.fi.pa165.api.SongFacade;
import cz.muni.fi.pa165.api.dto.*;
import cz.muni.fi.pa165.mvc.Alert;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
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
        } catch (Exception ex) {
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
            log.info("Getting all songs for genreId={} for list", genreId);
            GenreDTO genreDTO = genreFacade.getGenreById(genreId);
            songDTOList = songFacade.getAllSongs().stream().filter(s -> s.getGenre().equals(genreDTO)).collect(Collectors.toList());
        } else {
            log.info("Getting all songs for list");
            songDTOList = songFacade.getAllSongs();
        }
        model.addAttribute("songDTOList", songDTOList);

        return "songs/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redir) {
        log.info("Getting detail for song with id={}", id);
        SongDTO songDTO = songFacade.getSongById(id);

        if (songDTO == null) {
            log.error("Cannot find song with id={}", id);
            redir.addFlashAttribute(Alert.ERROR, "Song with #id=" + id + " not found in database");
            return REDIRECT_SONGS;
        } else {
            log.info("Successfully get detail for song={}", songDTO);
            model.addAttribute("songDTO", songDTO);
        }

        return "songs/detail";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String add(Model model) {
        log.info("Trying to create new song");
        model.addAttribute("songForm", new SongCreateDTO());
        model.addAttribute("allMusicians", musicianFacade.getAllMusicians());
        model.addAttribute("allAlbums", albumFacade.getAllAlbums());
        model.addAttribute("allGenres", genreFacade.getAllGenres());
        return "songs/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public String doAdd(@Valid @ModelAttribute("songForm") SongCreateDTO songCreateDTO, BindingResult bindingResult, RedirectAttributes redirect, Model model) {

        if (bindingResult.hasErrors()) {
            log.error("Validation of SongCreateDTO={} didn't pass. Returning to add new song", songCreateDTO);
            model.addAttribute("allMusicians", musicianFacade.getAllMusicians());
            model.addAttribute("allAlbums", albumFacade.getAllAlbums());
            model.addAttribute("allGenres", genreFacade.getAllGenres());
            return "songs/add";
        }
        log.info("Successfully validated SongCreateDTO={}", songCreateDTO);

        Long id = songFacade.createSong(songCreateDTO);

        log.info("Successfully created song with id={}", id);
        redirect.addFlashAttribute(Alert.SUCCESS, "Song Successfully created");

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

    @RequestMapping(value = "/addSongToAlbum/", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String addSongToAlbum(@RequestParam(value = "songId", required = false) Long songId, @RequestParam(value = "albumId") Long albumId, RedirectAttributes redir) {
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
        } catch (Exception ex) {
            redir.addFlashAttribute(Alert.ERROR, "Cannot assign song #id=" + songId + " to album #id=" + albumId);
            return REDIRECT_SONGS;
        }
        redir.addFlashAttribute(Alert.SUCCESS, "Song with #id=" + songId + " has been assigned to album #id=" + albumId);
        return REDIRECT_SONGS;
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String delete(@PathVariable Long id, RedirectAttributes redir) {
        log.info("Trzing to delete song with id={}", id);
        if (songFacade.getSongById(id) == null) {
            log.error("Song with id={} not found in database", id);
            redir.addFlashAttribute(Alert.ERROR, "Song with #id=" + id + " not found in database");
            return REDIRECT_SONGS;
        }

        try {
            songFacade.deleteSong(id);
            songFacade.deleteSong(id);
            log.info("Song with id={} successfully deleted", id);
            redir.addFlashAttribute(Alert.SUCCESS, "Song successfully deleted");
            return REDIRECT_SONGS;
        } catch (Exception ex) {
            log.error("Song with id={} cannot be deleted", id);
            redir.addFlashAttribute(Alert.ERROR, "Cannot delete Song with #id=" + id);
            return REDIRECT_SONGS;
        }
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes redir) {
        log.info("Trying to get song with id={} for edit", id);
        SongDTO song = songFacade.getSongById(id);

        if (song == null) {
            log.error("Cannot find song with id={} in database", id);
            redir.addFlashAttribute(Alert.ERROR, "Song with id '" + id + "' not found in the database!");
            return REDIRECT_SONGS;
        }

		SongCreateDTO s = new SongCreateDTO();
		s.setId(song.getId());
		s.setPosition(song.getPosition());
		s.setBitrate(song.getBitrate());
		s.setCommentary(song.getCommentary());
		s.setTitle(song.getTitle());
		s.setMusicianId(song.getMusician() == null ? null : song.getMusician().getId());
		s.setAlbumId(song.getAlbum() == null ? null : song.getAlbum().getId());
		s.setGenreId(song.getGenre() == null ? null : song.getGenre().getId());

        model.addAttribute("form", s);
		model.addAttribute("allMusicians", musicianFacade.getAllMusicians());
		model.addAttribute("allAlbums", albumFacade.getAllAlbums());
		model.addAttribute("allGenres", genreFacade.getAllGenres());
        log.info("Successfully got song with id={} for edit", id);
        return "songs/edit";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public String doEdit(@PathVariable Long id,@Valid @ModelAttribute("form") SongCreateDTO songForm, BindingResult bindingResult, RedirectAttributes redir, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("Validation of SongCreateDTO={} didn't pass. Returning to edit song with id={}", songForm, id);
            model.addAttribute("allMusicians", musicianFacade.getAllMusicians());
            model.addAttribute("allAlbums", albumFacade.getAllAlbums());
            model.addAttribute("allGenres", genreFacade.getAllGenres());
            return "songs/edit";
        }
        log.info("Successfully validated SongCreateDTO={}", songForm);

        log.info("Trying to update song={}", songForm);
        SongDTO song = songFacade.getSongById(id);

        if (song == null) {
            log.error("Cannot find song with id={} in database", id);
            redir.addFlashAttribute(Alert.ERROR, "Song with id '" + id + "' not found in the database!");
            return REDIRECT_SONGS;
        }


		try {
			songFacade.updateSong(songForm);
			log.info("Successfully updated song={}", songForm);
			redir.addFlashAttribute(Alert.SUCCESS, "Successfully updated");
		} catch (Exception ex) {
			log.error("Unable to update song={} because: {}", song, ex);
			redir.addFlashAttribute(Alert.ERROR, "Unable to update song (reason: " + ex.getMessage() + ")");
		}


        return REDIRECT_SONGS + song.getId();
    }

}
