package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.entity.Musician;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Unit tests for Musician DAO.
 *
 * @author Oldrich Konecny
 * @see MusicianDao
 */
@ContextConfiguration(classes = AppContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MusicianDaoTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private MusicianDao musicianDao;

	@Test
	public void testCreateAndFindById() {
		Musician musician = createValidMusiscian();
		musicianDao.create(musician);

		Musician musician2 = musicianDao.findById(musician.getId());

		assertEquals(musician, musician2);
	}

	@Test
	public void testFindByName() {
		Musician musician = createValidMusiscian();
		musicianDao.create(musician);

		Musician musician2 = musicianDao.findByName(musician.getName());

		assertEquals(musician, musician2);
	}

	@Test
	public void testFindByNameNoExisting() {
		Musician musician = createValidMusiscian();
		musicianDao.create(musician);

		Musician musician2 = musicianDao.findByName(musician.getName() + "aa");

		assertNull(musician2);
	}

	@Test(expectedExceptions = ConstraintViolationException.class)
	public void testCreateWithoutName() {
		Musician musician = new Musician();
		musicianDao.create(musician);
	}

	@Test(expectedExceptions = PersistenceException.class)
	public void testCreateTwoEqualMusician() {
		Musician musician1 = createValidMusiscian();
		Musician musician2 = createValidMusiscian();

		musicianDao.create(musician1);
		musicianDao.create(musician2);
	}

	@Test
	public void testFindAllNoExistingObjects() {
		assertTrue(musicianDao.findAll().isEmpty());
	}

	@Test
	public void testFindAll() {
		Musician musician1 = createValidPersistedMusician();
		Musician musician2 = createValidMusiscian();
		musician2.setName("AnotherTestMusician");
		musicianDao.create(musician2);

		List<Musician> list = musicianDao.findAll();
		assertTrue(list.size() == 2);
		assertTrue(list.contains(musician1) && list.contains(musician2));
	}

	@Test
	public void testDeleteNoExistingMusician() {
		Musician musician = new Musician();
		musician.setId(1231245L);
		musician.setName("TestMusician");
		musicianDao.delete(musician);
	}

	@Test
	public void testDelete() {
		Musician musician = createValidPersistedMusician();

		assertEquals(musicianDao.findById(musician.getId()), musician);
		musicianDao.delete(musician);

		assertTrue(musicianDao.findAll().isEmpty());
	}

	@Test
	public void testMultipleDelete() {
		Musician musician1 = createValidMusiscian();
		musician1.setName("one");
		Musician musician2 = createValidMusiscian();
		musician2.setName("two");

		musicianDao.create(musician1);
		musicianDao.create(musician2);

		assertEquals(musicianDao.findAll().size(), 2);

		musicianDao.delete(musician1);
		assertEquals(musicianDao.findAll().size(), 1);
		assertTrue(musicianDao.findAll().contains(musician2));

		musicianDao.delete(musician2);
		assertEquals(musicianDao.findAll().size(), 0);
	}

	@Test
	public void testUpdate() {
		Musician musician = createValidMusiscian();
		String name = "Something";
		musician.setName(name);
		musicianDao.create(musician);
		assertEquals(musicianDao.findById(musician.getId()).getName(), name);

		name = "Something Else";
		musician.setName(name);
		Musician musician2 = musicianDao.update(musician);
		assertEquals(musician.getId(), musician2.getId());
		assertEquals(musicianDao.findById(musician.getId()).getName(), name);
	}

	@Test
	public void testFindNoExistingObject() {
		Musician musician = musicianDao.findById(123123L);
		assertNull(musician);
	}

	private Musician createValidMusiscian() {
		Musician musician = new Musician();
		musician.setName("TestMusician");
		return musician;
	}

	private Musician createValidPersistedMusician() {
		Musician musician = new Musician();
		musician.setName("TestMusician");
		musicianDao.create(musician);
		return musician;
	}
}
