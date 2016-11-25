package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.MusicianFacade;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.config.ServiceConfiguration;
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit test for facade layer of Musician.
 *
 * @author Jan Stourac
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class MusicianFacadeTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private MusicianFacade musicianFacade;

	private MusicianDTO musician1;
	private MusicianDTO musician2;

	@BeforeMethod
	public void setUp() {
		musician1 = new MusicianDTO() {
			{
				setName("Musician 1");
			}
		};

		musician2 = new MusicianDTO() {
			{
				setName("Musician 2");
			}
		};

		assertNotEquals(musician1.getName(), musician2.getName());
	}

	@Test
	public void createMusicianTest() {
		Long id = musicianFacade.createMusician(musician1);
		assertNotEquals(id, 0L);
	}

	@Test
	public void getByIdTest() {
		Long id1 = musicianFacade.createMusician(musician1);
		Long id2 = musicianFacade.createMusician(musician2);

		assertEquals(musician1.getName(), musicianFacade.getMusicianById(id1).getName());
		assertEquals(musician2.getName(), musicianFacade.getMusicianById(id2).getName());
	}

	@Test
	public void getAllMusiciansTest() {
		musicianFacade.createMusician(musician1);
		musicianFacade.createMusician(musician2);

		List<MusicianDTO> musicians = musicianFacade.getAllMusicians();
		assertEquals(musicians.size(), 2);
		assertEquals(musicians.get(0).getName(), musician1.getName());
		assertEquals(musicians.get(1).getName(), musician2.getName());
	}

	@Test
	public void getMusicianByNameTest() {
		musicianFacade.createMusician(musician1);
		musicianFacade.createMusician(musician2);

		assertEquals(musician1.getName(), musicianFacade.getMusicianByName(musician1.getName()).getName());
		assertEquals(musician2.getName(), musicianFacade.getMusicianByName(musician2.getName()).getName());
	}

	@Test
	public void deleteMusicianTest() {
		Long id1 = musicianFacade.createMusician(musician1);
		Long id2 = musicianFacade.createMusician(musician2);

		musicianFacade.deleteMusician(musicianFacade.getMusicianById(id1));
		assertNull(musicianFacade.getMusicianById(id1));
		assertEquals(musicianFacade.getAllMusicians().size(), 1);
		assertEquals(musicianFacade.getMusicianById(id2).getName(), musician2.getName());
	}

}
