package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.util.EntityUtils;
import cz.muni.fi.pa165.util.TestUtils;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Jan Stourac
 */
@ContextConfiguration(classes = AppContext.class)
public class SongTest extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;

	@Test
	public void testEquals() {
		Song song = createMinimalValidSong();
		TestUtils.persistObjects(emf, song);

		EntityManager em2 = emf.createEntityManager();
		Song song2 = em2.find(Song.class, song.getId());
		assertEquals(song, song2);
	}

	@Test
	public void testHashCode() {
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
		song.setTitle(TestUtils.generateString(64));
		TestUtils.persistObjects(emf, song);
	}

	@Test(expectedExceptions = PersistenceException.class)
	public void throwIfTitleIsTooLong() {
		Song song = createMinimalValidSong();
		song.setTitle(TestUtils.generateString(65));
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

	//@Test()
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
		String com = TestUtils.generateString(2048);
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
		song.setCommentary(TestUtils.generateString(2049));
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

}
