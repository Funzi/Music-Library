package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.AlbumRating;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.util.EntityUtils;
import static cz.muni.fi.pa165.util.EntityUtils.getValidAlbumRating;
import static cz.muni.fi.pa165.util.EntityUtils.getValidUser;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for AlbumRating DAO.
 *
 * @author Jan Stourac
 * @see AlbumRatingDao
 */
@ContextConfiguration(classes = AppContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class AlbumRatingDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private AlbumRatingDao albumRatingDao;

	@Autowired
    private AlbumDao albumDao;

	@Autowired
    private UserDao userDao;

	private User user1;
	private User user2;

	private Album album1;
	private Album album2;

	@BeforeMethod
	public void setup() {
		user1 = getValidUser();
		user2 = getValidUser();
		user2.setUsername("user 2");

		album1 = EntityUtils.getValidAlbum();
		album2 = EntityUtils.getValidAlbum();
		album2.setTitle("album 2");

		userDao.create(user1);
		userDao.create(user2);

		albumDao.create(album1);
		albumDao.create(album2);
	}

    @Test
    public void createTest() {
        AlbumRating albumRating = getValidAlbumRating(album1, user1);
        albumRatingDao.create(albumRating);

        AlbumRating albumRating2 = albumRatingDao.findById(albumRating.getId());
        assertEquals(albumRating, albumRating2);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createSameAlbumRatingsShouldRaiseExceptions() {
        AlbumRating albumRating = getValidAlbumRating(album1, user1);
        albumRatingDao.create(albumRating);
		AlbumRating albumRating1 = getValidAlbumRating(album1, user1);
        albumRatingDao.create(albumRating1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createWithNullParametersShouldRaiseException() {
        albumRatingDao.create(null);
    }

    @Test
    public void findByIdOnNonExistingIdShouldReturnNull() {
        assertNull(albumRatingDao.findById(100L));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByIdWithNullParameterShouldRaiseException() {
        albumRatingDao.findById(null);
    }

    @Test
    public void findAllOnEmptyDbShouldReturnEmptyList() {
        assertTrue(albumRatingDao.findAll().isEmpty());
    }

    @Test
    public void findAllTest() {
        AlbumRating albumRating = getValidAlbumRating(album1, user1);
        albumRatingDao.create(albumRating);

        AlbumRating albumRating2 = getValidAlbumRating(album1, user2);
        albumRatingDao.create(albumRating2);

		List<AlbumRating> albumRatings = albumRatingDao.findAll();
        assertEquals(2, albumRatings.size());
		assertTrue(albumRatings.contains(albumRating) && albumRatings.contains(albumRating2));
    }

    @Test
    public void afterDeletingAlbumRatingFindByIdShouldReturnNull() {
        AlbumRating albumRating = getValidAlbumRating(album1, user1);
        albumRatingDao.create(albumRating);

        albumRatingDao.delete(albumRating);
        assertNull(albumRatingDao.findById(albumRating.getId()));
    }

    @Test
    public void deletingNonExistingAlbumRatingWillNotRaiseException() {
        AlbumRating albumRating = getValidAlbumRating(album1, user1);
        albumRatingDao.delete(albumRating);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteWithNullParameterShouldRaiseException() {
        albumRatingDao.delete(null);
    }

    @Test
    public void updateTest() {
        AlbumRating albumRating = getValidAlbumRating(album1, user2);
        albumRatingDao.create(albumRating);
        albumRating.setRvalue(5.5f);
        albumRatingDao.update(albumRating);

        assertEquals(albumRatingDao.findById(albumRating.getId()).getRvalue(), 5.5);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateWithNullParameterShouldRaiseException() {
        albumRatingDao.update(null);
    }

	@Test
	public void testAvg() {
		double val1 = 1.0;
		double val2 = 2.3;
		double val3 = 0.8;
		AlbumRating rating1 = getValidAlbumRating(album1, user1);
		rating1.setRvalue(val1);
		AlbumRating rating2 = getValidAlbumRating(album1, user2);
		rating2.setRvalue(val2);
		AlbumRating rating3 = getValidAlbumRating(album2, user1);
		rating3.setRvalue(val3);

		albumRatingDao.create(rating1);
		try {
			Thread.sleep(2000);
		} catch(Exception ex) {
			fail(ex.getMessage(), ex);
		}
		albumRatingDao.create(rating2);
		albumRatingDao.create(rating3);

		rating1 = albumRatingDao.findById(rating1.getId());

		assertEquals(albumRatingDao.avg(album1, new Date()), (val1 + val2) / 2.0);
		assertEquals(albumRatingDao.avg(album1, rating1.getAdded()), val1);
		assertEquals(albumRatingDao.avg(album2, new Date()), val3);
	}

}
