package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.AppContext;
import static cz.muni.fi.pa165.util.EntityUtils.getPersistedValidUser;
import static cz.muni.fi.pa165.util.EntityUtils.getValidUser;
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
 * Unit tests for User entity.
 *
 * @author Jan Stourac
 * @see User
 */
@ContextConfiguration(classes = AppContext.class)
public class UserTest extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;

	@Test
	public void testUsersWithSameDataAreEqual() {
		User user1 = getValidUser();
		User user2 = getValidUser();

		assertEquals(user1, user2);
	}

	@Test
	public void testUsersWithDifferentDataAreNotEqual() {
		User user1 = getValidUser();
		User user2 = getValidUser();
		user2.setUsername("random");

		assertNotEquals(user1.getUsername(), user2.getUsername());
		assertNotEquals(user1, user2);
	}

	@Test
	public void persitingUserDoesntChangeEqual() {
		User user1 = getPersistedValidUser(emf);

		EntityManager em = emf.createEntityManager();
		User user2 = em.find(User.class, user1.getId());
		assertEquals(user1, user2);
	}

	@Test
	public void testUsersWithDifferentDataHaveDifferentHash() {
		User user1 = getValidUser();
		User user2 = getValidUser();
		user2.setUsername("random");

		assertNotEquals(user1.getUsername(), user2.getUsername());
		assertNotEquals(user1.hashCode(), user2.hashCode());
	}

	@Test
	public void testUsersWithSameDataHaveSameHash() {
		User user1 = getValidUser();
		User user2 = getValidUser();

		assertEquals(user1.hashCode(), user2.hashCode());
	}

	@Test
	public void testPersitingUserDoesntChangeHash() {
		User user1 = getValidUser();
		TestUtils.persistObjects(emf, user1);

		EntityManager em = emf.createEntityManager();
		User user2 = em.find(User.class, user1.getId());
		assertEquals(user1.hashCode(), user2.hashCode());
	}

	@Test(expectedExceptions = PersistenceException.class)
	public void throwIfUsernameIsTooLong() {
		User user = getValidUser();
		user.setUsername(TestUtils.generateString(Constants.INT_LENGTH_SMALL + 1));
		TestUtils.persistObjects(emf, user);
	}

	@Test(expectedExceptions = PersistenceException.class)
	public void throwIfPasswordIsTooLong() {
		User user = getValidUser();
		user.setPassword(TestUtils.generateString(Constants.INT_LENGTH_LONG + 1));
		TestUtils.persistObjects(emf, user);
	}

	@Test(expectedExceptions = ConstraintViolationException.class)
	public void throwIfUsernameIsNull() {
		User user = getValidUser();
		user.setUsername(null);
		TestUtils.persistObjects(emf, user);
	}

	@Test(expectedExceptions = ConstraintViolationException.class)
	public void throwIfPasswordIsNull() {
		User user = getValidUser();
		user.setPassword(null);
		TestUtils.persistObjects(emf, user);
	}

	@AfterMethod
	public void deleteData() {
		TestUtils.deleteAllData(emf);
	}
}
