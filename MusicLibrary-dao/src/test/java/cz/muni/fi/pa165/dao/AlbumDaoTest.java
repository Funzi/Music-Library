package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.util.EntityUtils;
import static cz.muni.fi.pa165.util.EntityUtils.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import org.testng.annotations.Test;

/**
 * Unit tests for Album DAO.
 *
 * @author Martin Kulisek
 * @see SongDao
 */
@ContextConfiguration(classes = AppContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class AlbumDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AlbumDao albumDao;

	@Autowired
    private ArtDao artDao;

	@Autowired
    private GenreDao genreDao;

	@Autowired
    private UserDao userDao;

	@Autowired
    private MusicianDao musicianDao;

	@Autowired
    private SongDao songDao;

	@Autowired
    private AlbumRatingDao albumRatingDao;

    @Test
    public void createAndFindByIdTest() {
        Album album = EntityUtils.getValidAlbum();
        albumDao.create(album);

        Album album2 = albumDao.findById(album.getId());
        assertEquals(album, album2);
    }

    @Test
    public void multiCreateTest() {
        Album album = EntityUtils.getValidAlbum();
        albumDao.create(album);

        Album album2 = EntityUtils.getValidAlbum();
        albumDao.create(album2);

        assertEquals(albumDao.findById(album.getId()), album);
        assertEquals(albumDao.findById(album2.getId()), album2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNullTest() {
        albumDao.create(null);
    }

    @Test
    public void findNoneTest() {
        assertNull(albumDao.findById(10L));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findNullTest() {
        albumDao.findById(null);
    }

    @Test
    public void findAllNoneTest() {
        assertTrue(albumDao.findAll().isEmpty());
    }

    @Test
    public void findAllTest() {
        Album album = EntityUtils.getValidAlbum();
        albumDao.create(album);

        Album album2 = EntityUtils.getValidAlbum();
        albumDao.create(album2);

        List<Album> albums = albumDao.findAll();
        assertEquals(albums.size(), 2);
        assertTrue(albums.contains(album) && albums.contains(album2));

        assertEquals(albumDao.findById(album.getId()), album);
        assertEquals(albumDao.findById(album2.getId()), album2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findAllNullTest() {
        albumDao.findById(null);
    }

    @Test
    public void deleteTest() {
        Album album = EntityUtils.getValidAlbum();
        albumDao.create(album);
        assertEquals(albumDao.findById(album.getId()), album);

        albumDao.delete(album);
        assertNull(albumDao.findById(album.getId()));
    }

    @Test
    public void multiDeleteTest() {
        Album album = EntityUtils.getValidAlbum();
        albumDao.create(album);

        Album album2 = EntityUtils.getValidAlbum();
        albumDao.create(album2);

        assertEquals(albumDao.findAll().size(), 2);

        albumDao.delete(album);
        assertEquals(albumDao.findAll().size(), 1);
        assertEquals(albumDao.findById(album2.getId()), album2);

        albumDao.delete(album2);
        assertEquals(albumDao.findAll().size(), 0);
    }

    @Test()
    public void deleteInvalidTest() {
        Album album = EntityUtils.getValidAlbum();
        Album album2 = EntityUtils.getValidAlbum();

        albumDao.create(album2);
        albumDao.delete(album);
        assertEquals(albumDao.findById(album2.getId()), album2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNullTest() {
        albumDao.delete(null);
    }

    @Test
    public void updateTest() {
        Album album = EntityUtils.getValidAlbum();
        albumDao.create(album);
        assertEquals(albumDao.findById(album.getId()).getTitle(), album.getTitle());

		String newTitle = "random";
		assertNotEquals(album.getTitle(), newTitle);
        album.setTitle(newTitle);
        albumDao.update(album);
        assertEquals(albumDao.findById(album.getId()).getTitle(), album.getTitle());
    }

    @Test
    public void multiUpdateTest() {
        Album album = EntityUtils.getValidAlbum();
        albumDao.create(album);

        Album album2 = EntityUtils.getValidAlbum();
        albumDao.create(album2);

        album.setTitle("random");
        albumDao.update(album);
        assertEquals(albumDao.findById(album.getId()).getTitle(), album.getTitle());
        assertEquals(albumDao.findById(album2.getId()).getTitle(), album2.getTitle());

        album2.setTitle("random #2");
        albumDao.update(album2);
        assertEquals(albumDao.findById(album.getId()).getTitle(), album.getTitle());
        assertEquals(albumDao.findById(album2.getId()).getTitle(), album2.getTitle());
    }

    @Test
    public void updateInvalidTest() {
        Album album = EntityUtils.getValidAlbum();
        Album album2 = EntityUtils.getValidAlbum();
        albumDao.create(album2);

        album.setTitle("random");
        albumDao.update(album);
        assertEquals(albumDao.findById(album2.getId()).getTitle(), album2.getTitle());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullTest() {
        albumDao.update(null);
    }

	@Test
	public void testBestRating() {
        Album album = EntityUtils.getValidAlbum();
        Album album2 = EntityUtils.getValidAlbum();
		album2.setTitle("aaaaa");

		User user1 = EntityUtils.getValidUser();
		User user2 = EntityUtils.getValidUser();
		user2.setUsername("user 2");

		AlbumRating rating = getValidAlbumRating(album, user1);
		rating.setRvalue(1.0);

		AlbumRating rating2 = getValidAlbumRating(album, user2);
		rating2.setRvalue(0.3);

		AlbumRating rating3 = getValidAlbumRating(album2, user1);
		rating3.setRvalue(0.3);

		userDao.create(user1);
		userDao.create(user2);

		albumDao.create(album);
        albumDao.create(album2);
		albumRatingDao.create(rating3);

		Date before = new Date();
		try {
			Thread.sleep(2000);
		} catch(Exception ex) {
			fail();
		}
		albumRatingDao.create(rating);
		albumRatingDao.create(rating2);

		album = albumDao.findById(album.getId());
		album2 = albumDao.findById(album2.getId());

		System.out.println("===> " + albumRatingDao.findById(rating.getId()).getAdded());
		System.out.println("===> " + albumRatingDao.findById(rating3.getId()).getAdded());

		List<Album> best;
		best = albumDao.findBestRated(Integer.MAX_VALUE);
		assertEquals(best.size(), 2);
		assertTrue(best.contains(album) && best.contains(album2));

		best = albumDao.findBestRated(1);
		assertEquals(best.size(), 1);
		assertTrue(best.contains(album));
	}

	@Test
	public void testBestRatingForMusician() {
		Album album1 = getValidAlbum();
		Album album2 = getValidAlbum();

		Musician musician = getValidMusician();
		musicianDao.create(musician);
		Song song1 = EntityUtils.getValidSong();
		song1.setMusician(musician);
		song1.setAlbum(album1);
		Song song2 = EntityUtils.getValidSong();
		song2.setMusician(musician);
		song2.setAlbum(album2);

		User user = getValidUser();
		userDao.create(user);
		AlbumRating rating1 = getValidAlbumRating(album1, user);
		rating1.setRvalue(1.0);
		AlbumRating rating2 = getValidAlbumRating(album2, user);
		rating2.setRvalue(0.60);

		songDao.create(song1);
		songDao.create(song2);
		albumDao.create(album1);
		albumDao.create(album2);
		albumRatingDao.create(rating1);
		albumRatingDao.create(rating2);

		List<Album> bestRated = albumDao.findBestRatedForMusician(1, musician);
		assertEquals(bestRated.size(), 1);
		assertTrue(bestRated.contains(album1));
	}

    @Test
    public void testFindAlbumByMusician() {
        Musician musician = getValidMusician();
		musicianDao.create(musician);
        Song song1 = new Song();
        Song song2 = new Song();
        Album album1 = new Album();
        Album album2 = new Album();

        song1.setAlbum(album1);
        song1.setTitle("songWithMusician");
        song1.setBitrate(1);
        song1.setCommentary("qwe");
        song1.setMusician(musician);

        album1.setTitle("albumWithMusician");
        album1.setCommentary("qwe");
        album1.addSong(song1);

		albumDao.create(album1);
		songDao.create(song1);

        song2.setAlbum(album2);
        song2.setTitle("songWithOUTmusician");
        song2.setCommentary("asd");
        song2.setBitrate(1);

        album2.setTitle("albumWITHOUTMusician");
        album2.addSong(song2);

		albumDao.create(album2);
		songDao.create(song2);

        assertTrue(albumDao.findAlbumByMusicianId(musician.getId()).get(0).equals(album1));

    }

    @Test
    public void testFindAlbumsByReleaseDate() {
        Album album = getValidAlbum();
        LocalDate ld = LocalDate.of(2016, 1, 1);
        album.setReleaseDate(ld);
        albumDao.create(album);

        Album album1 = getValidAlbum();
        album1.setReleaseDate(LocalDate.of(2000, 1, 1));
        albumDao.create(album1);

        assertTrue(albumDao.findAll().size() == 2);

        List<Album> list = albumDao.findAlbumsByReleaseDates(
                LocalDate.of(2010, 1, 1), LocalDate.of(2017, 1 , 1));

        assertEquals(list.get(0), album);
    }

    @Test
    public void testFindAlbumsByReleaseDateNonExisting() {
        Album album = getValidAlbum();
        LocalDate ld = LocalDate.of(2016, 1, 2);
        album.setReleaseDate(ld);
        albumDao.create(album);

        List<Album> list = albumDao.findAlbumsByReleaseDates(
                LocalDate.of(2010, 1, 1), LocalDate.of(2016, 1 , 1));

        assertTrue(list.isEmpty());
    }

    @Test
    public void testFindAlbumsByReleaseDateBorderDate() {
        Album album = getValidAlbum();
        LocalDate ld = LocalDate.of(2016, 1, 1);
        album.setReleaseDate(ld);
        albumDao.create(album);

        List<Album> list = albumDao.findAlbumsByReleaseDates(
                LocalDate.of(2016, 1, 1), LocalDate.of(2016, 1 , 1));

        assertEquals(list.get(0), album);
    }

    @Test
    public void testFindAlbumsByReleaseDateBorderDates2() {
        Album album = getValidAlbum();
        LocalDate ld = LocalDate.of(2016, 1, 1);
        album.setReleaseDate(ld);
        albumDao.create(album);

        Album album1 = getValidAlbum();
        album1.setReleaseDate(LocalDate.of(2010, 1, 1));
        albumDao.create(album1);

        List<Album> list = albumDao.findAlbumsByReleaseDates(
                LocalDate.of(2010, 1, 1), LocalDate.of(2016, 1 , 1));
        assertTrue(list.contains(album));
        assertTrue(list.contains(album1));
        assertTrue(list.size() == 2);
    }

    @Test(expectedExceptions = DateTimeException.class)
    public void testFindAlbumsByReleaseDateWrongDate() {
        albumDao.findAlbumsByReleaseDates(LocalDate.of(1,13,789), LocalDate.of(-5,1,1));
    }

    @Test
    public void testFindAlbumsByReleaseDateChangeDates() {
        Album album = getValidAlbum();
        LocalDate ld = LocalDate.of(2016, 1, 1);
        album.setReleaseDate(ld);
        albumDao.create(album);

        List<Album> list = albumDao.findAlbumsByReleaseDates(
                LocalDate.of(2017, 1, 1), LocalDate.of(2010, 1 , 1));

        assertTrue(list.isEmpty());
    }

    @Test
    public void testFindAlbumsByPartialTitle() {
        String title = "tvaroh za rafkem..";
        Album album = getValidAlbum();
        album.setTitle(title);
        albumDao.create(album);

        String partial = "tvaroh";

        List<Album> list = albumDao.findAlbumsByPartialTitle(partial);
        assertEquals(list.get(0), album);
    }

    @Test
    public void testFindAlbumsByPartialTitleMoreAlbums() {
        String title = "tvaroh za rafkem..";
        Album album = getValidAlbum();
        album.setTitle(title);
        albumDao.create(album);

        Album album1 = getValidAlbum();
        album1.setTitle("vsem nam tvaroh velmi chutna");
        albumDao.create(album1);

        Album album2 = getValidAlbum();
        album2.setTitle("Tvaroh qweasfq");
        albumDao.create(album2);

        String partial = "tvaroh";

        List<Album> list = albumDao.findAlbumsByPartialTitle(partial);
        assertTrue(list.contains(album));
        assertTrue(list.contains(album1));
        assertTrue(list.size() == 2);
    }

    @Test
    public void testFindAlbumsByPartialTitleNonExistingTitle() {
        String title = "tvaroh za rafkem..";
        Album album = getValidAlbum();
        album.setTitle(title);
        albumDao.create(album);

        String partial = "qweasd";

        List<Album> list = albumDao.findAlbumsByPartialTitle(partial);
        assertTrue(list.isEmpty());
    }

    @Test
    public void testAddSongToAlbum() {
        Musician musician = getValidMusician();
        Album album = getValidAlbum();
        Art art = getArt();
        Genre genre = getValidGenre();
        Song song = EntityUtils.getValidSong();
        song.setGenre(genre);
        song.setMusician(musician);
        song.setAlbum(album);
        album.setArt(art);
        //album.addSong(song);
		musicianDao.create(musician);
		artDao.create(art);
		genreDao.create(genre);
		albumDao.create(album);
		songDao.create(song);

        Album album1 = albumDao.findById(album.getId());
        assertEquals(album1.getId(), album.getId());
    }

}
