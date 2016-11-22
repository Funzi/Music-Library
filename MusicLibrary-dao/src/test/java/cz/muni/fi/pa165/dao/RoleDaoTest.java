package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.*;
import cz.muni.fi.pa165.entity.Role;
import cz.muni.fi.pa165.util.EntityUtils;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

/**
 * Unit tests for Role DAO.
 *
 * @author Jan Stourac
 * @see RoleDao
 */
@ContextConfiguration(classes = AppContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class RoleDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RoleDao roleDao;

    @Test
    public void createAndFindByIdTest() {
        Role role = EntityUtils.getValidRole();
        roleDao.create(role);

        Role role2 = roleDao.findById(role.getId());
        assertEquals(role, role2);
    }

    @Test
    public void multiCreateTest() {
        Role role = EntityUtils.getValidRole();
        roleDao.create(role);

        Role role2 = getAnotherValidRole();
        roleDao.create(role2);

        assertEquals(roleDao.findById(role.getId()), role);
        assertEquals(roleDao.findById(role2.getId()), role2);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void doubleCreateTest() {
        Role role1 = EntityUtils.getValidRole();
		Role role2 = EntityUtils.getValidRole();
        roleDao.create(role1);
        roleDao.create(role2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNullTest() {
        roleDao.create(null);
    }

    @Test
    public void findNoneTest() {
        assertNull(roleDao.findById(10L));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findNullTest() {
        roleDao.findById(null);
    }

    @Test
    public void findAllNoneTest() {
        assertTrue(roleDao.findAll().isEmpty());
    }

    @Test
    public void findAllTest() {
        Role role = EntityUtils.getValidRole();
        roleDao.create(role);

        Role role2 = getAnotherValidRole();
        roleDao.create(role2);

        List<Role> roles = roleDao.findAll();
        assertEquals(roles.size(), 2);
        assertTrue(roles.contains(role) && roles.contains(role2));

        assertEquals(roleDao.findById(role.getId()), role);
        assertEquals(roleDao.findById(role2.getId()), role2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findAllNullTest() {
        roleDao.findById(null);
    }

    @Test
    public void deleteTest() {
        Role role = EntityUtils.getValidRole();
        roleDao.create(role);
        assertEquals(roleDao.findById(role.getId()), role);

        roleDao.delete(role);
        assertNull(roleDao.findById(role.getId()));
    }

    @Test
    public void multiDeleteTest() {
        Role role = EntityUtils.getValidRole();
        roleDao.create(role);

        Role role2 = getAnotherValidRole();
        roleDao.create(role2);

        assertEquals(roleDao.findAll().size(), 2);

        roleDao.delete(role);
        assertEquals(roleDao.findAll().size(), 1);
        assertEquals(roleDao.findById(role2.getId()), role2);

        roleDao.delete(role2);
        assertEquals(roleDao.findAll().size(), 0);
    }

    @Test
    public void deleteInvalidTest() {
        Role role = EntityUtils.getValidRole();
        Role role2 = getAnotherValidRole();

        roleDao.create(role2);
        roleDao.delete(role);
        assertEquals(roleDao.findById(role2.getId()), role2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNullTest() {
        roleDao.delete(null);
    }

    @Test
    public void updateTest() {
        Role role = EntityUtils.getValidRole();
        roleDao.create(role);
        assertEquals(roleDao.findById(role.getId()).getName(), role.getName());

        role.setName("random");
        roleDao.update(role);
        assertEquals(roleDao.findById(role.getId()).getName(), role.getName());
    }

    @Test
    public void multiUpdateTest() {
        Role role = EntityUtils.getValidRole();
        roleDao.create(role);

        Role role2 = getAnotherValidRole();
        roleDao.create(role2);

        role.setName("random");
        roleDao.update(role);
        assertEquals(roleDao.findById(role.getId()).getName(), role.getName());
        assertEquals(roleDao.findById(role2.getId()).getName(), role2.getName());

        role2.setName("random #2");
        roleDao.update(role2);
        assertEquals(roleDao.findById(role.getId()).getName(), role.getName());
        assertEquals(roleDao.findById(role2.getId()).getName(), role2.getName());
    }

    @Test
    public void updateInvalidTest() {
        Role role = EntityUtils.getValidRole();
        Role role2 = getAnotherValidRole();
        roleDao.create(role2);

        role.setName("random");
        roleDao.update(role);
        assertEquals(roleDao.findById(role2.getId()).getName(), role2.getName());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullTest() {
        roleDao.update(null);
    }

	private Role getAnotherValidRole() {
		Role role = EntityUtils.getValidRole();
		role.setName("another");
        return role;
	}
}
