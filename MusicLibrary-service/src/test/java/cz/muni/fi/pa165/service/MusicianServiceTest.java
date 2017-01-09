package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MusicianDao;
import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.exceptions.DataAccessException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for Musician service.
 *
 * @author Jan Stourac
 */
public class MusicianServiceTest {

	@InjectMocks
	private MusicianService service = new MusicianServiceImpl();

	@Mock
	private MusicianDao musicianDao;

	private Musician musician1;
	private Musician musician2;
	private Musician musician3;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		musician1 = new Musician() {
			{
				setId(1L);
				setName("Musician 1");
			}
		};

		musician2 = new Musician() {
			{
				setId(2L);
				setName("Musician 2");
			}
		};

		musician3 = new Musician() {
			{
				setId(3L);
				setName("Musician 3");
			}
		};

		when(musicianDao.findById(musician1.getId())).thenReturn(musician1);
		when(musicianDao.findById(musician2.getId())).thenReturn(musician2);
		when(musicianDao.findByName(musician1.getName())).thenReturn(musician1);
		when(musicianDao.findByName(musician2.getName())).thenReturn(musician2);
		doThrow(DataAccessException.class).when(musicianDao).create(musician1);
		doThrow(DataAccessException.class).when(musicianDao).create(null);
		doThrow(DataAccessException.class).when(musicianDao).delete(null);

		List<Musician> musicians = new ArrayList<Musician>() {
			{
				add(musician1);
				add(musician2);
			}
		};
		when(musicianDao.findAll()).thenReturn(musicians);
	}

	@Test
	public void testBaseObjectsProperties() {
		assertNotEquals(musician1, musician2);
		assertNotEquals(musician1.getId(), musician2.getId());
		assertNotEquals(musician1, musician3);
		assertNotEquals(musician1.getId(), musician3.getId());
		assertNotEquals(musician2, musician3);
		assertNotEquals(musician2.getId(), musician3.getId());
	}

	@Test
	public void testFindById() {
		assertSame(musician1, service.findById(musician1.getId()));
		assertSame(musician2, service.findById(musician2.getId()));
	}

	@Test
	public void testFindByIdNull() {
		assertNull(service.findById(musician3.getId()));
	}

	@Test
	public void testFindAll() {
		List<Musician> musicians = service.findAll();
		assertNotNull(musicians);
		assertEquals(musicians.size(), 2);
		assertTrue(musicians.contains(musician1));
		assertTrue(musicians.contains(musician2));
		assertFalse(musicians.contains(musician3));
	}

	@Test
	public void testFindByName() {
		assertEquals(musician1, service.findByName(musician1.getName()));
		assertEquals(musician2, service.findByName(musician2.getName()));
	}

	@Test
	public void testFindByNameNull() {
		assertNull(service.findByName(musician3.getName()));
	}

	@Test
	public void testCreate() {
		assertEquals(musician3, service.create(musician3));
		verify(musicianDao, times(1)).create(musician3);
	}

	@Test(expectedExceptions = DataAccessException.class)
	public void testCreateAlreadyExisting() {
		service.create(musician1);
		verify(musicianDao, times(1)).create(musician1);
	}

	@Test(expectedExceptions = DataAccessException.class)
	public void testCreateNull() {
		service.create(null);
	}

	@Test
	public void testDelete() {
		service.delete(musician1);
		verify(musicianDao, times(1)).delete(musician1);
	}

	@Test(expectedExceptions = DataAccessException.class)
	public void testDeleteNull() {
		service.delete(null);
	}
}
