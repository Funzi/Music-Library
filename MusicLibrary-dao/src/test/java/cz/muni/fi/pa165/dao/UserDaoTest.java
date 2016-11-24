package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.*;
import cz.muni.fi.pa165.entity.User;
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
 * Unit tests for User DAO.
 *
 * @author Jan Stourac
 * @see UserDao
 */
@ContextConfiguration(classes = AppContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void createAndFindByIdTest() {
        User user = EntityUtils.getValidUser();
        userDao.create(user);

        User user2 = userDao.findById(user.getId());
        assertEquals(user, user2);
    }

    @Test
    public void multiCreateTest() {
        User user = EntityUtils.getValidUser();
        userDao.create(user);

        User user2 = getAnotherValidUser();
        userDao.create(user2);

        assertEquals(userDao.findById(user.getId()), user);
        assertEquals(userDao.findById(user2.getId()), user2);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void doubleCreateTest() {
        User user1 = EntityUtils.getValidUser();
		User user2 = EntityUtils.getValidUser();
        userDao.create(user1);
        userDao.create(user2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNullTest() {
        userDao.create(null);
    }

    @Test
    public void findNoneTest() {
        assertNull(userDao.findById(10L));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findNullTest() {
        userDao.findById(null);
    }

    @Test
    public void findAllNoneTest() {
        assertTrue(userDao.findAll().isEmpty());
    }

    @Test
    public void findAllTest() {
        User user = EntityUtils.getValidUser();
        userDao.create(user);

        User user2 = getAnotherValidUser();
        userDao.create(user2);

        List<User> users = userDao.findAll();
        assertEquals(users.size(), 2);
        assertTrue(users.contains(user) && users.contains(user2));

        assertEquals(userDao.findById(user.getId()), user);
        assertEquals(userDao.findById(user2.getId()), user2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findAllNullTest() {
        userDao.findById(null);
    }

    @Test
    public void deleteTest() {
        User user = EntityUtils.getValidUser();
        userDao.create(user);
        assertEquals(userDao.findById(user.getId()), user);

        userDao.delete(user);
        assertNull(userDao.findById(user.getId()));
    }

    @Test
    public void multiDeleteTest() {
        User user = EntityUtils.getValidUser();
        userDao.create(user);

        User user2 = getAnotherValidUser();
        userDao.create(user2);

        assertEquals(userDao.findAll().size(), 2);

        userDao.delete(user);
        assertEquals(userDao.findAll().size(), 1);
        assertEquals(userDao.findById(user2.getId()), user2);

        userDao.delete(user2);
        assertEquals(userDao.findAll().size(), 0);
    }

    @Test
    public void deleteInvalidTest() {
        User user = EntityUtils.getValidUser();
        User user2 = getAnotherValidUser();

        userDao.create(user2);
        userDao.delete(user);
        assertEquals(userDao.findById(user2.getId()), user2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNullTest() {
        userDao.delete(null);
    }

    @Test
    public void updateTest() {
        User user = EntityUtils.getValidUser();
        userDao.create(user);
        assertEquals(userDao.findById(user.getId()).getUsername(), user.getUsername());

        user.setUsername("random");
        userDao.update(user);
        assertEquals(userDao.findById(user.getId()).getUsername(), user.getUsername());
    }

    @Test
    public void multiUpdateTest() {
        User user = EntityUtils.getValidUser();
        userDao.create(user);

        User user2 = getAnotherValidUser();
        userDao.create(user2);

        user.setUsername("random");
        userDao.update(user);
        assertEquals(userDao.findById(user.getId()).getUsername(), user.getUsername());
        assertEquals(userDao.findById(user2.getId()).getUsername(), user2.getUsername());

        user2.setUsername("random #2");
        userDao.update(user2);
        assertEquals(userDao.findById(user.getId()).getUsername(), user.getUsername());
        assertEquals(userDao.findById(user2.getId()).getUsername(), user2.getUsername());
    }

    @Test
    public void updateInvalidTest() {
        User user = EntityUtils.getValidUser();
        User user2 = getAnotherValidUser();
        userDao.create(user2);

        user.setUsername("random");
        userDao.update(user);
        assertEquals(userDao.findById(user2.getId()).getUsername(), user2.getUsername());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullTest() {
        userDao.update(null);
    }

	private User getAnotherValidUser() {
		User user = EntityUtils.getValidUser();
		user.setUsername("another");
        return user;
	}
}
