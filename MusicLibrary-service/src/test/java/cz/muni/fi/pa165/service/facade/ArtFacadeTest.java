package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.ArtFacade;
import cz.muni.fi.pa165.api.dto.ArtDTO;
import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.entity.Art;
import cz.muni.fi.pa165.service.BeanMappingService;
import java.util.Base64;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

/**
 *
 * @author Jan Stourac
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class ArtFacadeTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private ArtFacade artFacade;

	@Autowired
	private BeanMappingService beanMappingService;

	@Test
	public void testDozerMapping() {
		byte[] data = "somerandomdata...notnecesarrilyimage".getBytes();
		String base64 = Base64.getEncoder().encodeToString(data);

		Art art = new Art();
		art.setImage(data);

		ArtDTO dto = beanMappingService.mapTo(art, ArtDTO.class);
		assertEquals(base64, dto.getImage());

		Art art2 = beanMappingService.mapTo(dto, Art.class);
		assertEquals(art2.getImage(), data);
	}

	@Test
	public void testCreateAndGetArtById() {
		ArtDTO artDTO = new ArtDTO();
		artDTO.setImageName("ArtName");
		artDTO.setImageType("ArtType");
		byte[] img = "qweqweqweqweqweqew".getBytes();
		String str = Base64.getEncoder().encodeToString(img);
		artDTO.setImage(str);


		Long id = artFacade.createArt(artDTO);
		ArtDTO artDTO1 = artFacade.getArtById(id);

		assertTrue(artDTO1.getId() != null);
		assertEquals(artDTO1.getImage(), artDTO.getImage());
		assertEquals(artDTO1.getImageName(), artDTO.getImageName());
		assertEquals(artDTO1.getImageType(), artDTO.getImageType());
	}

	@Test
	public void testDelete() {
		ArtDTO artDTO = new ArtDTO();
		artDTO.setImageName("ArtName");
		artDTO.setImageType("ArtType");
		byte[] img = "qweqweqweqweqweqew".getBytes();
		String str = Base64.getEncoder().encodeToString(img);
		artDTO.setImage(str);

		assertNotNull(artFacade);

		Long id = artFacade.createArt(artDTO);
		artDTO.setId(id);
		artFacade.deleteArt(artDTO);
		ArtDTO artDTO1 = artFacade.getArtById(id);

		assertNull(artDTO1);
	}

}
