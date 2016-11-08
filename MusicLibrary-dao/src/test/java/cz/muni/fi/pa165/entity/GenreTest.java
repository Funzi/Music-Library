package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.AppContext;
import static cz.muni.fi.pa165.util.EntityUtils.getValidGenre;
import cz.muni.fi.pa165.util.TestUtils;
import cz.muni.fi.pa165.utils.Constants;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for Genre entity.
 * 
 * @author David Pribula
 * @see Genre
 */
@ContextConfiguration(classes = AppContext.class)
public class GenreTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void testGenresWithSameDataAreEqual() {
        Genre genre1 = getValidGenre();
        Genre genre2 = getValidGenre();

        assertEquals(genre1, genre2);
    }

    @Test
    public void testGenresWithDifferentDataAreNotEqual() {
        Genre genre1 = getValidGenre();
        Genre genre2 = getValidGenre();
        genre2.setName("Jazz");

        assertNotEquals(genre1, genre2);
    }

    @Test
    public void persitingGenreDoesntChangeEqual() {
        Genre genre1 = getValidGenre();
        TestUtils.persistObjects(emf, genre1);

        EntityManager em = emf.createEntityManager();
        Genre genre2 = em.find(Genre.class, genre1.getId());
        assertEquals(genre1, genre2);
    }

    @Test
    public void genresWithDifferentDataHaveDifferentHash() {
        Genre genre1 = getValidGenre();
        Genre genre2 = getValidGenre();
        genre2.setName("Jazz");

        assertNotEquals(genre1.hashCode(), genre2.hashCode());
    }

    @Test
    public void genresWithSameDataHaveSameHash() {
        Genre genre1 = getValidGenre();
        Genre genre2 = getValidGenre();

        assertEquals(genre1.hashCode(), genre2.hashCode());
    }

    @Test
    public void persitingGenreDoesntChangeHash() {
        Genre genre1 = getValidGenre();
        TestUtils.persistObjects(emf, genre1);

        EntityManager em = emf.createEntityManager();
        Genre genre2 = em.find(Genre.class, genre1.getId());
        assertEquals(genre1.hashCode(), genre2.hashCode());
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void persitingDescriptionLongerThanMaxShouldRaiseException() {
        Genre genre = getValidGenre();
        genre.setDescription(TestUtils.generateString(Constants.INT_LENGTH_HUGE + 1));
        TestUtils.persistObjects(emf, genre);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void persistingWithNullNameShouldRaiseException() {
        Genre genre = getValidGenre();
        genre.setName(null);
        TestUtils.persistObjects(emf, genre);
    }

    @AfterMethod
    public void deleteData() {
        TestUtils.deleteAllData(emf);
    }
}
