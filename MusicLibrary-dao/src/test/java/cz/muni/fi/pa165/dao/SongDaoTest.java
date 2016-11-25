package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.*;
import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.util.EntityUtils;
import static cz.muni.fi.pa165.util.EntityUtils.getValidMusician;
import static cz.muni.fi.pa165.util.EntityUtils.getValidSong;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

/**
 * Unit tests for Song DAO.
 *
 * @author Jan Stourac
 * @see SongDao
 */
@ContextConfiguration(classes = AppContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class SongDaoTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private SongDao songDao;

	@Autowired
	private MusicianDao musicianDao;

	@Test
	public void createAndFindByIdTest() {
		Song song = EntityUtils.getValidSong();
		songDao.create(song);

		Song song2 = songDao.findById(song.getId());
		assertEquals(song, song2);
	}

	@Test
	public void multiCreateTest() {
		Song song = EntityUtils.getValidSong();
		songDao.create(song);

		Song song2 = EntityUtils.getValidSong();
		songDao.create(song2);

		assertEquals(songDao.findById(song.getId()), song);
		assertEquals(songDao.findById(song2.getId()), song2);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void createNullTest() {
		songDao.create(null);
	}

	@Test
	public void findNoneTest() {
		assertNull(songDao.findById(10L));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void findNullTest() {
		songDao.findById(null);
	}

	@Test
	public void findAllNoneTest() {
		assertTrue(songDao.findAll().isEmpty());
	}

	@Test
	public void findAllTest() {
		Song song = EntityUtils.getValidSong();
		songDao.create(song);

		Song song2 = EntityUtils.getValidSong();
		songDao.create(song2);

		List<Song> songs = songDao.findAll();
		assertEquals(songs.size(), 2);
		assertTrue(songs.contains(song) && songs.contains(song2));

		assertEquals(songDao.findById(song.getId()), song);
		assertEquals(songDao.findById(song2.getId()), song2);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void findAllNullTest() {
		songDao.findById(null);
	}

	@Test
	public void deleteTest() {
		Song song = EntityUtils.getValidSong();
		songDao.create(song);
		assertEquals(songDao.findById(song.getId()), song);

		songDao.delete(song);
		assertNull(songDao.findById(song.getId()));
	}

	@Test
	public void multiDeleteTest() {
		Song song = EntityUtils.getValidSong();
		songDao.create(song);

		Song song2 = EntityUtils.getValidSong();
		songDao.create(song2);

		assertEquals(songDao.findAll().size(), 2);

		songDao.delete(song);
		assertEquals(songDao.findAll().size(), 1);
		assertEquals(songDao.findById(song2.getId()), song2);

		songDao.delete(song2);
		assertEquals(songDao.findAll().size(), 0);
	}

	@Test
	public void deleteInvalidTest() {
		Song song = EntityUtils.getValidSong();
		Song song2 = EntityUtils.getValidSong();

		songDao.create(song2);
		songDao.delete(song);
		assertEquals(songDao.findById(song2.getId()), song2);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void deleteNullTest() {
		songDao.delete(null);
	}

	@Test
	public void updateTest() {
		Song song = EntityUtils.getValidSong();
		songDao.create(song);
		assertEquals(songDao.findById(song.getId()).getTitle(), song.getTitle());

		song.setTitle("random");
		songDao.update(song);
		assertEquals(songDao.findById(song.getId()).getTitle(), song.getTitle());
	}

	@Test
	public void multiUpdateTest() {
		Song song = EntityUtils.getValidSong();
		songDao.create(song);

		Song song2 = EntityUtils.getValidSong();
		songDao.create(song2);

		song.setTitle("random");
		songDao.update(song);
		assertEquals(songDao.findById(song.getId()).getTitle(), song.getTitle());
		assertEquals(songDao.findById(song2.getId()).getTitle(), song2.getTitle());

		song2.setTitle("random #2");
		songDao.update(song2);
		assertEquals(songDao.findById(song.getId()).getTitle(), song.getTitle());
		assertEquals(songDao.findById(song2.getId()).getTitle(), song2.getTitle());
	}

	@Test
	public void updateInvalidTest() {
		Song song = EntityUtils.getValidSong();
		Song song2 = EntityUtils.getValidSong();
		songDao.create(song2);

		song.setTitle("random");
		songDao.update(song);
		assertEquals(songDao.findById(song2.getId()).getTitle(), song2.getTitle());
	}

	@Test
	public void findByMusicianTest() {
		Musician musician1 = getValidMusician();
		musician1.setName("musician 1");
		Musician musician2 = getValidMusician();
		musician2.setName("musician 2");
		Song song1 = getValidSong();
		song1.setMusician(musician1);
		Song song2 = getValidSong();
		song2.setMusician(musician1);
		Song song3 = getValidSong();
		song3.setMusician(musician2);

		assertNotEquals(musician1.getName(), musician2.getName());

		musicianDao.create(musician1);
		musicianDao.create(musician2);

		songDao.create(song1);
		songDao.create(song2);
		songDao.create(song3);

		List<Song> songs = songDao.findByMusician(musician1);
		assertEquals(songs.size(), 2);
		assertTrue(songs.contains(song1));
		assertTrue(songs.contains(song2));
		assertFalse(songs.contains(song3));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void updateNullTest() {
		songDao.update(null);
	}
}
