package cz.muni.fi.pa165.util;

import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jan Stourac
 */
public class TestUtils {

    /**
     * Generates string containing only small 'a' letters with specified length.
     *
     * @param length length of string
     * @return string with defined length
     */
    public static String generateString(int length) {
        char[] arr = new char[length];
        Arrays.fill(arr, 'a');
        return String.valueOf(arr);
    }

    public static void persistObjects(EntityManagerFactory emf, Object... objs) {
        persistObjects(emf.createEntityManager(), objs);
    }

    public static void persistObjects(EntityManager em, Object... objs) {
        em.getTransaction().begin();
        for (Object o : objs) {
            em.persist(o);
        }
        em.getTransaction().commit();
        em.close();
    }

    public static void deleteAllData(EntityManagerFactory emf) {
        deleteAllData(emf.createEntityManager());
    }

    public static void deleteAllData(EntityManager em) {
        deleteData(em, "Song", "Art", "AlbumRating", "Album", "Musician", "Genre", "User", "Role");
    }

    public static void deleteData(EntityManagerFactory emf, String... schemas) {
        deleteData(emf.createEntityManager(), schemas);
    }

    public static void deleteData(EntityManager em, String... schemas) {
        em.getTransaction().begin();
        for (String s : schemas) {
            em.createQuery("DELETE FROM " + s).executeUpdate();
        }
        em.getTransaction().commit();
        em.close();
    }
}
