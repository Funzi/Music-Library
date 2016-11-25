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
	}

	@Test
	public void createMusicianTest() {
		Long id = musicianFacade.createMusician(musician1);
		assertNotEquals(id, 0L);
	}

	@Test
	public void getByIdTest() {
		Long id = musicianFacade.createMusician(musician1);
		MusicianDTO dto = musicianFacade.getMusicianById(id);

		assertEquals(musician1.getName(), dto.getName());
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

}
