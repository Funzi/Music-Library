package cz.muni.fi.pa165.entity;

/**
 * Created by olda on 26.10.2016.
 */
import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.entity.Musician;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

@ContextConfiguration(classes = AppContext.class)
public class MusicianTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void categoryTest() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Musician musician = new Musician();
        musician.setName("Eminem");
        em.persist(musician);
        em.getTransaction().commit();
        em.close();
        //TODO under this line: create a second entity manager in categoryTest, use find method to find the category and assert its name.
        EntityManager em2 = emf.createEntityManager();
        Musician musician2 = em2.find(Musician.class, musician.getId());
        assertEquals(musician2.getName(), "Eminem");

        assertEquals(musician.getName(), "Eminem");
        EntityManager em3 = emf.createEntityManager();
        musician.setName("Pepa z Depa");

    }

}