package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.entity.Art;
import cz.muni.fi.pa165.util.EntityUtils;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;

/**
 * Created by olda on 24.11.2016.
 */
@ContextConfiguration(classes = AppContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ArtDaoTest extends AbstractTestNGSpringContextTests{

    @Inject
    private ArtDao artDao;

    @Test
    public void testCreateAndFindById() {
        Art art = EntityUtils.getArt();
        artDao.create(art);

        Art art1 = artDao.findById(art.getId());
        assertEquals(art, art1);
    }

    @Test
    public void testMultipleCreate() {
        Art art = EntityUtils.getArt();
        artDao.create(art);
        Art art1 = EntityUtils.getArt();
        art1.setImageName("SomethingElse");
        artDao.create(art1);

        Art art2 = artDao.findById(art.getId());
        Art art3 = artDao.findById(art1.getId());
        assertEquals(art, art2);
        assertEquals(art1, art3);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void testCreateWithNotNullId() {
        Art art = EntityUtils.getArt();
        art.setId(5L);
        artDao.create(art);
    }

    @Test
    public void testCreateSameArtTwoTimes() {
        Art art = EntityUtils.getArt();
        artDao.create(art);
        Art art1 = EntityUtils.getArt();
        artDao.create(art1);

        List<Art> list = artDao.findAll();
        assertTrue(list.size() == 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByIdNullId() {
        Art art = artDao.findById(null);
    }

    @Test
    public void testFindByIdNonExistingId() {
        Art art = artDao.findById(654L);
        assertEquals(art, null);
    }

    @Test
    public void testFindAll() {
        Art art = EntityUtils.getArt();
        Art art1 = EntityUtils.getArt();
        art1.setImageName("Art1");
        Art art2 = EntityUtils.getArt();
        art2.setImageName("Art2");
        artDao.create(art);
        artDao.create(art1);
        artDao.create(art2);
        List<Art> list = artDao.findAll();
        assertTrue(list.size() == 3);
        assertTrue(list.contains(art));
        assertTrue(list.contains(art1));
        assertTrue(list.contains(art2));
    }

    @Test
    public void testFindAllNothingToFind() {
        List<Art> list = artDao.findAll();
        assertTrue(list.size() == 0);
    }

    @Test
    public void testDelete() {
        Art art = EntityUtils.getArt();
        artDao.create(art);
        assertEquals(artDao.findById(art.getId()), art);
        artDao.delete(art);
        assertTrue(artDao.findAll().size() == 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNull() {
        artDao.delete(null);
    }

    @Test
    public void testDeleteNonExistingArt() {
        Art art = EntityUtils.getArt();
        artDao.create(art);
        Art art1 = EntityUtils.getArt();
        art1.setId(5797L);
        artDao.delete(art1);

        assertEquals(artDao.findById(art.getId()), art);
        assertTrue(artDao.findAll().size() == 1);
    }

    @Test
    public void testUpdateImageName() {
        Art art = EntityUtils.getArt();
        artDao.create(art);
        String name = "JESUS_CONDOM";
        art.setImageName(name);
        artDao.update(art);

        assertEquals(artDao.findById(art.getId()).getImageName(), name);
    }

    @Test
    public void testUpdateImageTyp() {
        Art art = EntityUtils.getArt();
        artDao.create(art);
        String type = "JESUS_CONDOM";
        art.setImageType(type);
        artDao.update(art);

        assertEquals(artDao.findById(art.getId()).getImageType(), type);
    }

    @Test
    public void testUpdateImage() {
        Art art = EntityUtils.getArt();
        artDao.create(art);
        byte[] image = "JESUS_CONDOM".getBytes();
        art.setImage(image);
        artDao.update(art);

        assertEquals(artDao.findById(art.getId()).getImage(), image);
    }

    @Test
    public void testUpdateNonExistingArt() {
        Art art = EntityUtils.getArt();
        art.setId(9789L);
        artDao.update(art);
    }

}
