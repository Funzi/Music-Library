package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.AppContext;
import static cz.muni.fi.pa165.util.EntityUtils.getPersistedValidRole;
import static cz.muni.fi.pa165.util.EntityUtils.getValidRole;
import cz.muni.fi.pa165.util.TestUtils;
import cz.muni.fi.pa165.utils.Constants;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for Role entity.
 *
 * @author Jan Stourac
 * @see Role
 */
@ContextConfiguration(classes = AppContext.class)
public class RoleTest extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;

	@Test
	public void testRolesWithSameDataAreEqual() {
		Role role1 = getValidRole();
		Role role2 = getValidRole();

		assertEquals(role1, role2);
	}

	@Test
	public void testRolesWithDifferentDataAreNotEqual() {
		Role role1 = getValidRole();
		Role role2 = getValidRole();
		role2.setName("random");

		assertNotEquals(role1.getName(), role2.getName());
		assertNotEquals(role1, role2);
	}

	@Test
	public void persitingRoleDoesntChangeEqual() {
		Role role1 = getPersistedValidRole(emf);

		EntityManager em = emf.createEntityManager();
		Role role2 = em.find(Role.class, role1.getId());
		assertEquals(role1, role2);
	}

	@Test
	public void testRolesWithDifferentDataHaveDifferentHash() {
		Role role1 = getValidRole();
		Role role2 = getValidRole();
		role2.setName("random");

		assertNotEquals(role1.getName(), role2.getName());
		assertNotEquals(role1.hashCode(), role2.hashCode());
	}

	@Test
	public void testRolesWithSameDataHaveSameHash() {
		Role role1 = getValidRole();
		Role role2 = getValidRole();

		assertEquals(role1.hashCode(), role2.hashCode());
	}

	@Test
	public void testPersitingRoleDoesntChangeHash() {
		Role role1 = getValidRole();
		TestUtils.persistObjects(emf, role1);

		EntityManager em = emf.createEntityManager();
		Role role2 = em.find(Role.class, role1.getId());
		assertEquals(role1.hashCode(), role2.hashCode());
	}

	@Test(expectedExceptions = PersistenceException.class)
	public void throwIfNameIsTooLong() {
		Role role = getValidRole();
		role.setName(TestUtils.generateString(Constants.INT_LENGTH_SMALL + 1));
		TestUtils.persistObjects(emf, role);
	}

	@Test(expectedExceptions = ConstraintViolationException.class)
	public void throwIfRolenameIsNull() {
		Role role = getValidRole();
		role.setName(null);
		TestUtils.persistObjects(emf, role);
	}

	@AfterMethod
	public void deleteData() {
		TestUtils.deleteAllData(emf);
	}
}
