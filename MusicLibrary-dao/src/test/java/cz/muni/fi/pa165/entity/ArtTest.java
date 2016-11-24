package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.util.TestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import javax.persistence.*;
import javax.validation.ConstraintViolationException;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by olda on 17.11.2016.
 */
@ContextConfiguration(classes = AppContext.class)
public class ArtTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void testEquals() {
        Art art = createMinumalValidArt();
        TestUtils.persistObjects(emf, art);

        EntityManager em = emf.createEntityManager();
        Art art1 = em.find(Art.class, art.getId());
        assertEquals(art, art1);
    }

    @Test
    public void testHashCode() {
        Art art = createMinumalValidArt();
        TestUtils.persistObjects(emf, art);

        EntityManager em = emf.createEntityManager();
        Art art1 = em.find(Art.class, art.getId());
        assertEquals(art.hashCode(), art1.hashCode());
    }

    @Test
    public void testGenerateId() {
        Art art = createMinumalValidArt();
        TestUtils.persistObjects(emf, art);

        EntityManager em = emf.createEntityManager();
        Art art1 = em.find(Art.class, art.getId());
        assertEquals(art.getId(), art1.getId());
    }

    @Test
    public void testImageName() {
        String name = "StringTestName";
        Art art = createMinumalValidArt();
        art.setImageName(name);
        TestUtils.persistObjects(emf, art);

        EntityManager em = emf.createEntityManager();
        Art art1 = em.find(Art.class, art.getId());
        assertEquals(art1.getImageName(), name);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void testNullName() {
        Art art = createMinumalValidArt();
        art.setImageName(null);
        TestUtils.persistObjects(emf, art);
    }

    @Test
    public void testImageType() {
        String type = "FUCKING_HELL";
        Art art = createMinumalValidArt();
        art.setImageType(type);
        TestUtils.persistObjects(emf, art);

        EntityManager em = emf.createEntityManager();
        Art art1 = em.find(Art.class, art.getId());
        assertEquals(art1.getImageType(), type);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void testNullImageType() {
        Art art = createMinumalValidArt();
        art.setImageType(null);
        TestUtils.persistObjects(emf, art);
    }

    @Test
    public void testImage() {
        byte[] b = "HELL_YES".getBytes();
        Art art = createMinumalValidArt();
        art.setImage(b);
        TestUtils.persistObjects(emf, art);

        EntityManager em = emf.createEntityManager();
        Art art1 = em.find(Art.class, art.getId());
        assertEquals(art1.getImage(), b);
     }

    @AfterMethod
    public void deleteData() {
        TestUtils.deleteData(emf, "Art");
    }

    private Art createMinumalValidArt() {
        Art art = new Art();
        art.setImageType("JPG");
        art.setImageName("TestArt");
        return art;
    }

}
