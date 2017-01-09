package cz.muni.fi.pa165.sampledata;

import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.AlbumRating;
import cz.muni.fi.pa165.entity.Art;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.entity.Role;
import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.entity.SongRating;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.AlbumRatingService;
import cz.muni.fi.pa165.service.AlbumService;
import cz.muni.fi.pa165.service.ArtService;
import cz.muni.fi.pa165.service.GenreService;
import cz.muni.fi.pa165.service.MusicianService;
import cz.muni.fi.pa165.service.RoleService;
import cz.muni.fi.pa165.service.SongRatingService;
import cz.muni.fi.pa165.service.SongService;
import cz.muni.fi.pa165.service.UserService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Loads some sample data to populate the app database.
 *
 * @author Jan Stourac
 */
@Service
@Transactional //transactions are handled on facade layer
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    public static final String JPEG = "image/jpeg";

	@Autowired
    private AlbumService albumService;
	@Autowired
    private AlbumRatingService albumRatingService;
	@Autowired
    private ArtService artService;
    @Autowired
    private GenreService genreService;
	@Autowired
    private MusicianService musicianService;
	@Autowired
    private RoleService roleService;
	@Autowired
    private SongService songService;
	@Autowired
    private SongRatingService songRatingService;
	@Autowired
    private UserService userService;

    @Override
    @SuppressWarnings("unused")
    public void loadData() throws Exception {
		log.info("creating genres");
		Genre metal = genre("Metal", "metal music");
		Genre rock = genre("Rock", "rock music");

		Role admin = role("admin");

		User honza = user("honza", "Honza Admin", "heslo123", admin);
		User pepa = user("pepa", "Pepa User", "heslo1234.");

		Musician metallica = musician("Metallica");
		Musician inFlames = musician("In Flames");

		Art loadArt = art(IOUtils.toByteArray(SampleDataLoadingFacade.class.getResourceAsStream("/art/load.jpg")));
		Art sirenCharmsArt = art(IOUtils.toByteArray(SampleDataLoadingFacade.class.getResourceAsStream("/art/siren_charms.jpg")));

		Album load = album("Load", null, LocalDate.parse("1996-06-04"), loadArt);
		Album sirenCharms = album("Siren Charms", null, LocalDate.parse("2014-09-05"), sirenCharmsArt);
		Album myMix = album("My mix", "best mix 4ever", LocalDate.now(), null);

		AlbumRating loadHonza = albumRating(load, honza, 1.5, null);
		AlbumRating loadPepa = albumRating(load, pepa, 2.5, "One of the top albums from Metallica. Really love it!");
		AlbumRating sirenCharmsHonza = albumRating(sirenCharms, honza, 3.5, "The best live performance I have ever seen");
		AlbumRating sirenCharmsPepa = albumRating(sirenCharms, pepa, 4.0, null);

		Song aintMyBitch = song("Ain't My Bitch", load, metallica, 1, rock, null);
		Song twoXFour = song("2 X 4", load, metallica, 2, rock, null);
		Song theHouseJackBuilt = song("The House Jack Build", load, metallica, 3, rock, null);
		Song inPlainView = song("In Plaing View", sirenCharms, inFlames, 1, metal, null);
		Song everythingsGone = song("Everything's Gone", sirenCharms, inFlames, 2, metal, null);
		Song paralyzed = song("Paralyzed", sirenCharms, inFlames, 3, metal, null);
		Song aintMyBitch2 = song("Ain't My Bitch", myMix, metallica, 1, rock, null);
		Song theHouseJackBuilt2 = song("The House Jack Build", myMix, metallica, 3, rock, null);
		Song paralyzed2 = song("Paralyzed", myMix, inFlames, 2, metal, null);

		SongRating twoXFourHonza = songRating(twoXFour, honza, 4.5, "Nice song");
		SongRating paralyzedHonza = songRating(paralyzed, honza, 1.5, "Not my favourite");
		SongRating twoXFourPepa = songRating(twoXFour, pepa, 2.0, null);
    }

	private Album album(String title, String commentary, LocalDate releaseDate, Art art) {
		Album album = new Album();
		album.setTitle(title);
		album.setCommentary(commentary);
		album.setReleaseDate(releaseDate);
		album.setArt(art);

		albumService.createAlbum(album);

		return album;
	}

	private AlbumRating albumRating(Album album, User user, double rvalue, String comment) {
		AlbumRating rating = new AlbumRating();
		rating.setAlbum(album);
		rating.setRvalue(rvalue);
		rating.setUser(user);
		rating.setComment(comment);

		albumRatingService.create(rating);

		return rating;
	}

	private Art art(byte[] image) {
		Art art = new Art();
		art.setImage(image);
		art.setImageName("aaaaa");
		art.setImageType(JPEG);

		artService.createArt(art);

		return art;
	}

	private Genre genre(String name, String description) {
		Genre genre = new Genre();
		genre.setName(name);
		genre.setDescription(description);

		genreService.create(genre);

		return genre;
	}

	private Musician musician(String name) {
		Musician musician = new Musician();
		musician.setName(name);

		musicianService.create(musician);

		return musician;
	}

	private Role role(String name) {
		Role role = new Role();
		role.setName(name);

		roleService.create(role);

		return role;
	}

	private Song song(String title, Album album, Musician musician, int position, Genre genre, String commentary) {
		Song song = new Song();
		song.setTitle(title);
		song.setAlbum(album);
		song.setMusician(musician);
		song.setPosition(position);
		song.setGenre(genre);
		song.setCommentary(commentary);

		songService.create(song);

		return song;
	}

	private SongRating songRating(Song song, User user, double rvalue, String comment) {
		SongRating rating = new SongRating();
		rating.setSong(song);
		rating.setRvalue(rvalue);
		rating.setUser(user);
		rating.setComment(comment);

		songRatingService.create(rating);

		return rating;
	}

	private User user(String username, String name, String password, Role... roles) {
		User user = new User();
		user.setUsername(username);
		user.setName(name);
		user.setPassword(password);
		user.setPasswordConfirm(password);
		for(Role r : roles) {
			user.addRole(r);
		}

		userService.create(user);

		return user;
	}

    private static Date daysBeforeNow(int days) {
        return Date.from(ZonedDateTime.now().minusDays(days).toInstant());
    }

    private static Date toDate(int year, int month, int day) {
        return Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private byte[] readImage(String file) throws IOException {
        try (InputStream is = this.getClass().getResourceAsStream("/" + file)) {
            int nRead;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        }
    }
}
