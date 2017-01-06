package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.config.PasswordStrengthValidator;
import cz.muni.fi.pa165.config.ValidationReport;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.persistence.PersistenceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for User service.
 *
 * @author Jan Stourac
 */
public class UserServiceTest {

	@InjectMocks
	private UserService service = new UserServiceImpl();

	@Mock
	private UserDao userDao;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	private PasswordStrengthValidator validator;

	private User user1;
	private User user2;
	private User user3;

	private String password = "aaaaaa";
	private String wrongPassword = "a";
	private String encodedPassword = "::encoded::";
	private ValidationReport report1 = new ValidationReport(true, Collections.EMPTY_LIST);
	private ValidationReport report2 = new ValidationReport(false, Arrays.asList(new String[] {"error #1", "error #2"}));

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		user1 = new User() {
			{
				setId(1L);
				setUsername("user 1");
				setName("User #1");
				setPassword("a");
			}
		};

		user2 = new User() {
			{
				setId(2L);
				setUsername("user 2");
				setName("User #2");
				setPassword("a");
			}
		};

		user3 = new User() {
			{
				setId(3L);
				setUsername("user 3");
				setName("User #3");
				setPassword("a");
			}
		};

		when(userDao.findById(user1.getId())).thenReturn(user1);
		when(userDao.findById(user2.getId())).thenReturn(user2);
		when(userDao.findByUsername(user1.getUsername())).thenReturn(user1);
		when(userDao.findByUsername(user2.getUsername())).thenReturn(user2);

		when(bCryptPasswordEncoder.encode(password)).thenReturn(encodedPassword);
		when(validator.validate(password)).thenReturn(report1);
		when(validator.validate(wrongPassword)).thenReturn(report2);

		doThrow(PersistenceException.class).when(userDao).create(user1);
		doThrow(IllegalArgumentException.class).when(userDao).create(null);
		doThrow(IllegalArgumentException.class).when(userDao).delete(null);

		List<User> users = new ArrayList<User>() {
			{
				add(user1);
				add(user2);
			}
		};
		when(userDao.findAll()).thenReturn(users);
	}

	@Test
	public void testBaseObjectsProperties() {
		assertNotEquals(user1, user2);
		assertNotEquals(user1.getId(), user2.getId());
		assertNotEquals(user1, user3);
		assertNotEquals(user1.getId(), user3.getId());
		assertNotEquals(user2, user3);
		assertNotEquals(user2.getId(), user3.getId());
		assertNotEquals(password, encodedPassword);
	}

	@Test
	public void testFindById() {
		assertSame(user1, service.findById(user1.getId()));
		assertSame(user2, service.findById(user2.getId()));
	}

	@Test
	public void testFindByIdNull() {
		assertNull(service.findById(user3.getId()));
	}

	@Test
	public void testFindAll() {
		List<User> users = service.findAll();
		assertNotNull(users);
		assertEquals(users.size(), 2);
		assertTrue(users.contains(user1));
		assertTrue(users.contains(user2));
		assertFalse(users.contains(user3));
	}

	@Test
	public void testCreate() {
		service.create(user3);
		verify(userDao, times(1)).create(user3);
	}

	@Test(expectedExceptions = PersistenceException.class)
	public void testCreateAlreadyExisting() {
		service.create(user1);
		verify(userDao, times(1)).create(user1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testCreateNull() {
		service.create(null);
	}

	@Test
	public void testDelete() {
		service.delete(user1);
		verify(userDao, times(1)).delete(user1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testDeleteNull() {
		service.delete(null);
	}

	@Test
	public void testFindByUsername() {
		assertEquals(user1, service.findByUsername(user1.getUsername()));
		assertEquals(user2, service.findByUsername(user2.getUsername()));
	}

	@Test
	public void testChangePassword() {
		service.changePassword(user1, password);
		assertEquals(user1.getPassword(), encodedPassword);
		verify(validator, times(1)).validate(password);
		verify(bCryptPasswordEncoder, times(1)).encode(password);
		verify(userDao, times(1)).update(user1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testWrongPassword() {
		service.changePassword(user1, wrongPassword);
		verify(validator, times(1)).validate(password);
	}

}
