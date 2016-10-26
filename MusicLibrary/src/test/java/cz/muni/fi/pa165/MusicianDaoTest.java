package cz.muni.fi.pa165;

/**
 * Created by olda on 26.10.2016.
 */
import cz.muni.fi.pa165.dao.MusicianDao;
import cz.muni.fi.pa165.entity.Musician;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

@ContextConfiguration(classes = AppContext.class)
public class MusicianDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MusicianDao musicianDao;

    @Test
    public void createFindDeleteTest() {
        Musician m = new Musician();
        m.setName("Kabat");

        musicianDao.create(m);

        Musician m2 = new Musician();
        m2.setName("Harlej");
        musicianDao.create(m2);

        List<Musician> musicians = musicianDao.findAll();
        assertEquals(musicians.size(), 2);
        assertTrue(musicians.contains(m) && musicians.contains(m2));

        assertEquals(musicianDao.findById(m.getId()).getName(), m.getName());
        assertEquals(musicianDao.findById(m2.getId()).getName(), m2.getName());

        m.setName("Horkyze Slize");
        m = musicianDao.update(m);
        assertTrue(musicianDao.findAll().contains(m));

        musicianDao.delete(m);
        musicians = musicianDao.findAll();
        assertEquals(musicians.size(), 1);
        assertFalse(musicians.contains(m));
        assertTrue(musicians.contains(m2));

        musicianDao.delete(m2);
        musicians = musicianDao.findAll();
        assertTrue(musicians.isEmpty());
    }
}

