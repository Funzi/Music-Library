package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.util.EntityUtils;
import cz.muni.fi.pa165.util.TestUtils;
import cz.muni.fi.pa165.utils.Constants;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertNull;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for Song entity.
 *
 * @author Jan Stourac
 * @see Song
 */
@ContextConfiguration(classes = AppContext.class)
public class SongTest extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;

	@Test
	public void testSimpleEquals() {
		Song song1 = createMinimalValidSong();
		Song song2 = createMinimalValidSong();

		assertNotSame(song1, song2, "Instances for this test cannot be the same!");
		assertEquals(song1, song2);
	}

	@Test
	public void testSimpleNotEquals() {
		Song song1 = createMinimalValidSong();
		Song song2 = createMinimalValidSong();
		song2.setTitle("not equals");

		assertNotSame(song1, song2, "Instances for this test cannot be the same!");
		assertNotEquals(song1, song2);
	}

	@Test
	public void testComplexEquals() {
		Song song1 = createComplexValidSong();
		Song song2 = createComplexValidSong();

		assertNotSame(song1, song2, "Instances for this test cannot be the same!");
		assertEquals(song1, song2);
	}

	@Test
	public void testSimplePersistenceEquals() {
		Song song = createMinimalValidSong();
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertEquals(song, song2);
	}

	@Test
	public void testSimpleHashCode() {
		Song song1 = createMinimalValidSong();
		Song song2 = createMinimalValidSong();

		assertNotSame(song1, song2, "Instances for this test cannot be the same!");
		assertEquals(song1.hashCode(), song2.hashCode());
	}

	@Test
	public void testSimpleDifferentHashCode() {
		Song song1 = createMinimalValidSong();
		Song song2 = createMinimalValidSong();
		song2.setTitle("not same");

		assertNotSame(song1, song2, "Instances for this test cannot be the same!");
		assertNotEquals(song1.hashCode(), song2.hashCode());
	}

	@Test
	public void testComplexHashCode() {
		Song song1 = createComplexValidSong();
		Song song2 = createComplexValidSong();

		assertNotSame(song1, song2, "Instances for this test cannot be the same!");
		assertEquals(song1.hashCode(), song2.hashCode());
	}

	@Test
	public void testSimplePersistenceHashCode() {
		Song song = createMinimalValidSong();
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertEquals(song.hashCode(), song2.hashCode());
	}

	@Test()
	public void testGeneratedId() {
		Song song = createMinimalValidSong();
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertEquals(song.getId(), song2.getId());
	}

	@Test()
	public void testTitle() {
		String title = "Test";
		Song song = createMinimalValidSong();
		song.setTitle(title);
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertEquals(song2.getTitle(), title);
	}

	@Test(expectedExceptions = PersistenceException.class)
	public void throwIfTitleIsNull() {
		Song song = createMinimalValidSong();
		song.setTitle(null);
		TestUtils.persistObjects(emf, song);
	}

	@Test()
	public void testMaxTitleLength() {
		Song song = createMinimalValidSong();
		song.setTitle(TestUtils.generateString(Constants.INT_LENGTH_SMALL));
		TestUtils.persistObjects(emf, song);
	}

	@Test(expectedExceptions = PersistenceException.class)
	public void throwIfTitleIsTooLong() {
		Song song = createMinimalValidSong();
		song.setTitle(TestUtils.generateString(Constants.INT_LENGTH_SMALL + 1));
		TestUtils.persistObjects(emf, song);
	}

	@Test()
	public void testAlbum() {
		Album album = EntityUtils.getPersistedValidAlbum(emf);
		Song song = createMinimalValidSong();
		song.setAlbum(album);
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertEquals(song2.getAlbum(), album);
	}

	@Test
	public void testPosition() {
		int pos = 10;
		Song song = createMinimalValidSong();
		song.setPosition(pos);
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertEquals(song2.getPosition(), pos);
	}

	@Test
	public void testDefaultPosition() {
		Song song = createMinimalValidSong();
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertEquals(song2.getPosition(), 0);
	}

	@Test()
	public void testGenre() {
		Genre genre = EntityUtils.getPersistedValidGenre(emf);
		Song song = createMinimalValidSong();
		song.setGenre(genre);
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertEquals(song2.getGenre(), genre);
	}

	@Test()
	public void testDefaultGenre() {
		Song song = createMinimalValidSong();
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertNull(song2.getGenre());
	}

	@Test()
	public void testNullGenre() {
		Song song = createMinimalValidSong();
		song.setGenre(null);
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertNull(song2.getGenre());
	}

	@Test
	public void testBitrate() {
		int bitrate = 10;
		Song song = createMinimalValidSong();
		song.setBitrate(bitrate);
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertEquals(song2.getBitrate(), bitrate);
	}

	@Test
	public void testDefaultBitrate() {
		Song song = createMinimalValidSong();
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertEquals(song2.getBitrate(), 0);
	}

	@Test
	public void testCommentary() {
		String com = "commentary";
		Song song = createMinimalValidSong();
		song.setCommentary(com);
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertEquals(song2.getCommentary(), com);
	}

	@Test
	public void testDefaultCommentary() {
		Song song = createMinimalValidSong();
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertNull(song2.getCommentary());
	}

	@Test
	public void testNullCommentary() {
		Song song = createMinimalValidSong();
		song.setCommentary(null);
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertNull(song2.getCommentary());
	}

	@Test
	public void testCommentaryMaxLength() {
		String com = TestUtils.generateString(Constants.INT_LENGTH_HUGE);
		Song song = createMinimalValidSong();
		song.setCommentary(com);
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertEquals(song2.getCommentary(), com);
	}

	@Test(expectedExceptions = PersistenceException.class)
	public void throwIfCommentaryIsTooLong() {
		Song song = createMinimalValidSong();
		song.setCommentary(TestUtils.generateString(Constants.INT_LENGTH_HUGE + 1));
		TestUtils.persistObjects(emf, song);
	}

	@AfterMethod
	public void deleteData() {
		TestUtils.deleteAllData(emf);
	}

	private Song createMinimalValidSong() {
		Song song = new Song();
		song.setTitle("song");
		return song;
	}

	private Song createComplexValidSong() {
		Musician musician = EntityUtils.getValidMusician();
		Album album = EntityUtils.getValidAlbum(musician);
		int position = 10;
		Genre genre = EntityUtils.getValidGenre();
		int bitrate = 192;
		String commentary = "some comment";

		Song song = createMinimalValidSong();
		song.setAlbum(album);
		song.setPosition(position);
		song.setGenre(genre);
		song.setBitrate(bitrate);
		song.setCommentary(commentary);
		return song;
	}

}
