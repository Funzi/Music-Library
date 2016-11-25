package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.UserFacade;
import cz.muni.fi.pa165.api.dto.UserCreateDTO;
import cz.muni.fi.pa165.api.dto.UserDTO;
import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.util.TestUtils;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit test for facade layer of User.
 *
 * @author Jan Stourac
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class UserFacadeTest extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;

	@Autowired
	private UserFacade userFacade;

	private UserDTO user1;
	private UserDTO user2;

	private UserCreateDTO cuser1;
	private UserCreateDTO cuser2;

	@BeforeMethod
	public void setUp() {
		user1 = new UserDTO() {
			{
				setUsername("User 1");
			}
		};

		user2 = new UserDTO() {
			{
				setUsername("User 2");
			}
		};

		cuser1 = new UserCreateDTO() {
			{
				setUsername("User 1");
				setPassword("aaa");
				setPasswordConfirm("aaa");
			}
		};

		cuser2 = new UserCreateDTO() {
			{
				setUsername("User 2");
				setPassword("aaa");
				setPasswordConfirm("aaa");
			}
		};

		assertNotEquals(user1.getUsername(), user2.getUsername());
		assertEquals(cuser1.getUsername(), user1.getUsername());
		assertEquals(cuser2.getUsername(), user2.getUsername());
	}

	@Test
	public void createUserTest() {
		Long id = userFacade.createUser(cuser1);
		assertNotNull(id);
		assertNotEquals(id, 0L);
	}

	@Test
	public void getByIdTest() {
		Long id1 = userFacade.createUser(cuser1);
		Long id2 = userFacade.createUser(cuser2);

		assertEquals(user1.getUsername(), userFacade.getUserById(id1).getUsername());
		assertEquals(user2.getUsername(), userFacade.getUserById(id2).getUsername());
	}

	@Test
	public void getAllUsersTest() {
		userFacade.createUser(cuser1);
		userFacade.createUser(cuser2);

		List<UserDTO> users = userFacade.getAllUsers();
		assertEquals(users.size(), 2);
		assertEquals(users.get(0).getUsername(), user1.getUsername());
		assertEquals(users.get(1).getUsername(), user2.getUsername());
	}

	@Test
	public void getUserByNameTest() {
		userFacade.createUser(cuser1);
		userFacade.createUser(cuser2);

		assertEquals(user1.getUsername(), userFacade.getUserByUsername(cuser1.getUsername()).getUsername());
		assertEquals(user2.getUsername(), userFacade.getUserByUsername(cuser2.getUsername()).getUsername());
	}

	@Test
	public void changePasswordTest() {
		userFacade.changePassword(user1, "newpassword123.");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void changeWrongPasswordTest() {
		userFacade.changePassword(user1, "a");
	}

	@AfterMethod
	public void deleteData() {
		TestUtils.deleteAllData(emf);
	}

}
