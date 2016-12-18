package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.api.AlbumFacade;
import cz.muni.fi.pa165.api.AlbumRatingFacade;
import cz.muni.fi.pa165.api.GenreFacade;
import cz.muni.fi.pa165.api.MusicianFacade;
import cz.muni.fi.pa165.api.SecurityFacade;
import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.AlbumRatingDTO;
import cz.muni.fi.pa165.api.dto.ArtDTO;
import cz.muni.fi.pa165.api.dto.GenreDTO;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.mvc.Alert;
import cz.muni.fi.pa165.mvc.forms.FilterForm;
import java.io.IOException;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Example controller showing as much features as possible.
 *
 * @author Jan Stourac
 */
@Controller
@RequestMapping("/albums")
public class AlbumController {

    final static Logger log = LoggerFactory.getLogger(AlbumController.class);

    public static final String REDIRECT_ALBUMS = "redirect:/albums/";

    @Autowired
    private MessageSource messageSource; //resource bundle provided by Spring

    @Autowired
    private AlbumFacade albumFacade;

    @Autowired
    private AlbumRatingFacade albumRatingFacade;

    @Autowired
    private GenreFacade genreFacade;

    @Autowired
    private MusicianFacade musicianFacade;

    @Autowired
    private SecurityFacade securityFacade;

    @RequestMapping("/")
    public String list(@ModelAttribute("form") FilterForm form, Model model) {
        log.info("Getting all albums for list");
        form = form != null ? form : new FilterForm();
        Map<AlbumDTO, Set<MusicianDTO>> data = new HashMap<>();

        albumFacade.getAlbums(form.getMusicians(), form.getGenres()).forEach(a
                -> data.put(a, a.getSongs().stream().map(s -> s.getMusician()).collect(Collectors.toSet()))
        );

        model.addAttribute("albums", data);
        model.addAttribute("form", form);
        model.addAttribute("allMusicians", musicianFacade.getAllMusicians().stream().sorted(new Comparator<MusicianDTO>() {

            @Override
            public int compare(MusicianDTO o1, MusicianDTO o2) {
                return o1.getName().compareTo(o2.getName());
            }

        }).collect(Collectors.toMap(m -> m.getId(), m -> m.getName())));
        model.addAttribute("allGenres", genreFacade.getAllGenres().stream().sorted(new Comparator<GenreDTO>() {

            @Override
            public int compare(GenreDTO o1, GenreDTO o2) {
                return o1.getName().compareTo(o2.getName());
            }

        }).collect(Collectors.toMap(m -> m.getId(), m -> m.getName())));

        return "album/list";
    }

    @RequestMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        log.info("Getting detail for album with id={}", id);
        AlbumDTO a = albumFacade.getAlbumById(id);
        model.addAttribute("album", a);
        model.addAttribute("musicians", a.getSongs().stream().map(s -> s.getMusician()).collect(Collectors.toSet()));
        model.addAttribute("genres", a.getSongs().stream().map(s -> s.getGenre()).collect(Collectors.toSet()));
        model.addAttribute("ratings", a.getRatings());
        model.addAttribute("ratingForm", new AlbumRatingDTO());
        model.addAttribute("ratingValues", AlbumRatingController.allowedValues());
        model.addAttribute("hasRated", a.getRatings().stream()
                .map(r -> r.getUser().getUsername())
                .anyMatch(u -> u.equals(securityFacade.getLoggedInUsername())));

        return "album/details";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String add(Model model) {
        log.info("Trying to create new album");
        model.addAttribute("albumForm", new AlbumDTO());
        return "album/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public String doAdd(@ModelAttribute("albumForm") AlbumDTO album, RedirectAttributes redir) {
        if (!(album.getTitle().trim().length() > 0)) {
            log.error("Title was not set in the form");
            redir.addFlashAttribute(Alert.ERROR, "You have to fill the title");
            return REDIRECT_ALBUMS + "add";
        } else {
            log.info("Creating new album");
            albumFacade.createAlbum(album);
            redir.addFlashAttribute(Alert.SUCCESS, "Successfuly created");
            return REDIRECT_ALBUMS;
        }
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes redir) {
        log.info("Trying to get album with id={} for edit", id);
        AlbumDTO album = albumFacade.getAlbumById(id);

        if (album == null) {
            log.error("Cannot find album with id={} in database", id);
            redir.addFlashAttribute(Alert.ERROR, "Album with id '" + id + "' not found in the database!");
            return REDIRECT_ALBUMS;
        }
        log.info("Successfully get album with id={} to edit", id);
        model.addAttribute("form", albumFacade.getAlbumById(id));
        return "album/edit";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public String doEdit(@PathVariable Long id, @ModelAttribute("form") AlbumDTO albumForm, RedirectAttributes redir, HttpServletRequest request) {
        log.info("Trying to update album={}", albumForm);
        AlbumDTO album = albumFacade.getAlbumById(id);

        if (album == null) {
            redir.addFlashAttribute(Alert.ERROR, "Album with id '" + id + "' not found in the database!");
            return REDIRECT_ALBUMS;
        } else if (!(albumForm.getTitle().trim().length() > 0)) {
            log.error("Title was not set in the form");
            redir.addFlashAttribute(Alert.ERROR, "You have to fill the title");
            return REDIRECT_ALBUMS + "{id}/edit";
        } else {

            // Update only relevant attributes
            album.setTitle(albumForm.getTitle());
            album.setReleaseDate(albumForm.getReleaseDate());
            album.setCommentary(albumForm.getCommentary());
            try {
                albumFacade.updateAlbum(album);
                redir.addFlashAttribute(Alert.SUCCESS, "Successfuly updated");
                log.info("Successfully update album={}", album);
            } catch (Exception ex) {
                log.error("Unable to update album={} because: {}", album, ex);
                ex.printStackTrace();
                redir.addFlashAttribute(Alert.ERROR, "Unable to update album (reason: " + ex.getMessage() + ")");
            }
        }

        return "redirect:/albums/" + album.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public String delete(@PathVariable Long id, RedirectAttributes redir) {
        log.info("Trying to delete album with id={}", id);
        AlbumDTO album = albumFacade.getAlbumById(id);

        if (album == null) {
            log.error("Cannot find album with id={} in database", id);
            redir.addFlashAttribute(Alert.ERROR, "Album with id '" + id + "' does not exist!");
        } else {
            try {
                albumFacade.deleteAlbum(album);
                redir.addFlashAttribute(Alert.SUCCESS, "Successfuly deleted");
                log.info("Successfully deleted album={}", album);
            } catch (Exception ex) {
                log.error("Unable to delete album={} because: {}", album, ex);
                ex.printStackTrace();
                redir.addFlashAttribute(Alert.ERROR, "Unable to delete album (reason: " + ex.getMessage() + ")");
            }
        }

        return "redirect:/albums/";
    }

    @RequestMapping(value = "/{id}/rate", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String rate(@PathVariable Long id, @ModelAttribute("ratingForm") AlbumRatingDTO rating, RedirectAttributes redir) {
        log.info("Trying to rate album with id={}", id);
        AlbumDTO album = albumFacade.getAlbumById(id);

        if (album == null) {
            redir.addFlashAttribute(Alert.ERROR, "Album with id '" + id + "' does not exist!");
        } else {
            try {
                rating.setId(null);
                rating.setAlbum(album);
                rating.setUser(securityFacade.getLoggedInUser());
                albumRatingFacade.create(rating);
                redir.addFlashAttribute(Alert.SUCCESS, "Successfully rated!");
                log.info("Successfully rated album={}", album);
            } catch (Exception ex) {
                log.error("Unable to rate album={} with rating={}", album, rating);
                ex.printStackTrace();
                redir.addFlashAttribute(Alert.ERROR, "Unable to save rating (reason: " + ex.getMessage() + ")");
            }
        }

        return "redirect:/albums/" + id;
    }

    @RequestMapping(value = "/{id}/unrate", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String rate(@PathVariable Long id, RedirectAttributes redir) {
        log.info("Trying to rate album with id={}", id);
        AlbumDTO album = albumFacade.getAlbumById(id);
        if (album == null) {
            redir.addFlashAttribute(Alert.ERROR, "Album with id '" + id + "' does not exist!");
        } else {
            String username = securityFacade.getLoggedInUsername();
            boolean success = false;
            for (AlbumRatingDTO rating : album.getRatings()) {
                if (rating.getUser().getUsername().equals(username)) {
                    try {
                        albumRatingFacade.delete(rating);
                        redir.addFlashAttribute(Alert.SUCCESS, "Successfuly removed!");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        redir.addFlashAttribute(Alert.ERROR, "Unable to remove rating (reason: " + ex.getMessage() + ")");
                    }
                    success = true;
                    break;
                }
            }

            if (!success) {
                redir.addFlashAttribute(Alert.ERROR, "Rating for this album was not found in database!");
            }
        }

        return "redirect:/albums/" + id;
    }

    @RequestMapping(value = "/{id}/upload-cover", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String handleFileUpload(@PathVariable Long id, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        log.info("Trying to uload picture for album with id={}", id);
        try {
            ArtDTO artDTO = new ArtDTO();
            artDTO.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            artDTO.setImageType(file.getContentType());
            artDTO.setImageName(file.getOriginalFilename());
        } catch (IOException ex) {
            //TODO: Exception handling
            ex.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/albums/${id}";
    }

}
