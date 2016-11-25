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
}
