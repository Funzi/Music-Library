package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.entity.Musician;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import cz.muni.fi.pa165.util.EntityUtils;
import cz.muni.fi.pa165.util.TestUtils;
import cz.muni.fi.pa165.utils.Constants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

/**
 * Unit tests for Musician entity.
 * 
 * @author Oldrich Konecny
 * @see Musician
 */
@ContextConfiguration(classes = AppContext.class)
public class MusicianTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void testEquals() {
        Musician musician1 = createMinimalValidMusiscian();
        TestUtils.persistObjects(emf, musician1);
        EntityManager em = emf.createEntityManager();
        Musician musician2 = em.find(Musician.class, musician1.getId());
        assertEquals(musician1, musician2);
    }

    @Test
    public void testHashCode() {
        Musician musician1 = createMinimalValidMusiscian();
        TestUtils.persistObjects(emf, musician1);
        EntityManager em = emf.createEntityManager();
        Musician musician2 = em.find(Musician.class, musician1.getId());
        assertEquals(musician1.hashCode(), musician2.hashCode());
    }

    @Test
    public void testGeneratedId() {
        Musician musician1 = createMinimalValidMusiscian();
        TestUtils.persistObjects(emf, musician1);
        EntityManager em = emf.createEntityManager();
        Musician musician2 = em.find(Musician.class, musician1.getId());
        assertEquals(musician1.getId(), musician2.getId());
    }

    @Test
    public void testGenerateMoreId() {
        Musician musician1 = createMinimalValidMusiscian();
        musician1.setName("one");
        Musician musician2 = createMinimalValidMusiscian();
        musician2.setName("two");
        TestUtils.persistObjects(emf, musician1, musician2);

        assertNotEquals(musician1.getId(), musician2.getId());
    }

    @Test
    public void testName() {
        Musician musician1 = createMinimalValidMusiscian();
        String name = "BOOM qwe";
        musician1.setName(name);
        TestUtils.persistObjects(emf, musician1);

        EntityManager em = emf.createEntityManager();
        Musician musician2 = em.find(Musician.class, musician1.getId());

        assertEquals(musician2.getName(), name);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNullName() {
        Musician musician = createMinimalValidMusiscian();
        musician.setName(null);
        TestUtils.persistObjects(emf, musician);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void testTooLongName() {
        Musician musician = createMinimalValidMusiscian();
        musician.setName(TestUtils.generateString(Constants.INT_LENGTH_SMALL + 1));
        TestUtils.persistObjects(emf, musician);
    }

    @Test
    public void testMaxNameLength() {
        Musician musician = createMinimalValidMusiscian();
        musician.setName(TestUtils.generateString(Constants.INT_LENGTH_SMALL));
        TestUtils.persistObjects(emf, musician);
        EntityManager em = emf.createEntityManager();
        Musician musician2 = em.find(Musician.class, musician.getId());
        assertEquals(musician.getName(), musician2.getName());

    }

    @Test
    public void testEmptyName() {
        Musician musician = createMinimalValidMusiscian();
        musician.setName("");
        TestUtils.persistObjects(emf, musician);
        EntityManager em = emf.createEntityManager();
        Musician musician2 = em.find(Musician.class, musician.getId());
        assertTrue(musician2.getName().isEmpty());
    }


    private Album createMinimalPersistedAlbum(EntityManagerFactory emf) {
        Album album = new Album();
        album.setTitle("TestAlbum");
        TestUtils.persistObjects(emf, album);
        return album;
    }

    @AfterMethod
    public void deleteData() {
        TestUtils.deleteData(emf, "Musician", "Album", "Genre", "Song");
    }

    private Musician createMinimalValidMusiscian() {
        Musician musician = new Musician();
        musician.setName("TestMusician");

        return musician;
    }

}
