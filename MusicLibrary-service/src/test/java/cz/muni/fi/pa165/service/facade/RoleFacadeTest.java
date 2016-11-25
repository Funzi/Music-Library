package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.RoleFacade;
import cz.muni.fi.pa165.api.dto.RoleDTO;
import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.util.TestUtils;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit test for facade layer of Role.
 *
 * @author Jan Stourac
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class RoleFacadeTest extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;

	@Autowired
	private RoleFacade roleFacade;

	private RoleDTO role1;
	private RoleDTO role2;

	@BeforeMethod
	public void setUp() {
		role1 = new RoleDTO() {
			{
				setName("Role 1");
			}
		};

		role2 = new RoleDTO() {
			{
				setName("Role 2");
			}
		};

		assertNotEquals(role1.getName(), role2.getName());
	}

	@Test
	public void createRoleTest() {
		Long id = roleFacade.createRole(role1);
		assertNotNull(id);
		assertNotEquals(id, 0L);
	}

	@Test
	public void getByIdTest() {
		Long id1 = roleFacade.createRole(role1);
		Long id2 = roleFacade.createRole(role2);

		assertEquals(role1.getName(), roleFacade.getRoleById(id1).getName());
		assertEquals(role2.getName(), roleFacade.getRoleById(id2).getName());
	}

	@Test
	public void getAllRolesTest() {
		roleFacade.createRole(role1);
		roleFacade.createRole(role2);

		List<RoleDTO> roles = roleFacade.getAllRoles();
		assertEquals(roles.size(), 2);
		assertEquals(roles.get(0).getName(), role1.getName());
		assertEquals(roles.get(1).getName(), role2.getName());
	}

	@AfterMethod
	public void deleteData() {
		TestUtils.deleteAllData(emf);
	}

}
