package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.RoleDao;
import cz.muni.fi.pa165.entity.Role;
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
 * Unit tests for Role service.
 *
 * @author Jan Stourac
 */
public class RoleServiceTest {

	@InjectMocks
	private RoleService service = new RoleServiceImpl();

	@Mock
	private RoleDao roleDao;

	private Role role1;
	private Role role2;
	private Role role3;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		role1 = new Role() {
			{
				setId(1L);
				setName("role 1");
			}
		};

		role2 = new Role() {
			{
				setId(2L);
				setName("role 2");
			}
		};

		role3 = new Role() {
			{
				setId(3L);
				setName("role 3");
			}
		};

		when(roleDao.findById(role1.getId())).thenReturn(role1);
		when(roleDao.findById(role2.getId())).thenReturn(role2);
		when(roleDao.findByName(role1.getName())).thenReturn(role1);
		when(roleDao.findByName(role2.getName())).thenReturn(role2);

		doThrow(PersistenceException.class).when(roleDao).create(role1);
		doThrow(IllegalArgumentException.class).when(roleDao).create(null);
		doThrow(IllegalArgumentException.class).when(roleDao).delete(null);

		List<Role> roles = new ArrayList<Role>() {
			{
				add(role1);
				add(role2);
			}
		};
		when(roleDao.findAll()).thenReturn(roles);
	}

	@Test
	public void testBaseObjectsProperties() {
		assertNotEquals(role1, role2);
		assertNotEquals(role1.getId(), role2.getId());
		assertNotEquals(role1, role3);
		assertNotEquals(role1.getId(), role3.getId());
		assertNotEquals(role2, role3);
		assertNotEquals(role2.getId(), role3.getId());
	}

	@Test
	public void testFindById() {
		assertSame(role1, service.findById(role1.getId()));
		assertSame(role2, service.findById(role2.getId()));
	}

	@Test
	public void testFindByIdNull() {
		assertNull(service.findById(role3.getId()));
	}

	@Test
	public void testFindAll() {
		List<Role> roles = service.findAll();
		assertNotNull(roles);
		assertEquals(roles.size(), 2);
		assertTrue(roles.contains(role1));
		assertTrue(roles.contains(role2));
		assertFalse(roles.contains(role3));
	}

	@Test
	public void testCreate() {
		service.create(role3);
		verify(roleDao, times(1)).create(role3);
	}

	@Test(expectedExceptions = PersistenceException.class)
	public void testCreateAlreadyExisting() {
		service.create(role1);
		verify(roleDao, times(1)).create(role1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testCreateNull() {
		service.create(null);
	}

	@Test
	public void testDelete() {
		service.delete(role1);
		verify(roleDao, times(1)).delete(role1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testDeleteNull() {
		service.delete(null);
	}

}
