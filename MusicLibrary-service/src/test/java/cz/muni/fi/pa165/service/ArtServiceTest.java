package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.ArtDao;
import cz.muni.fi.pa165.entity.Art;
import cz.muni.fi.pa165.exceptions.DataAccessException;
import org.hibernate.PersistentObjectException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.PersistenceException;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;

/**
 * Created by olda on 24.11.2016.
 */
public class ArtServiceTest {

    @InjectMocks
    private ArtService service = new ArtServiceImpl();

    @Mock
    private ArtDao artDao;

    private Art art1;
    private Art art2;
    private Art art3;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        art1 = new Art() {
            {
                setId(1L);
                setImageName("NameArt1");
                setImageType("Type1");
            }
        };

        art2 = new Art() {
            {
                setId(2L);
                setImageName("NameArt2");
                setImageType("Type2");
            }
        };

        art3 = new Art() {
            {
                setId(3L);
                setImageName("NameArt3");
                setImageType("Type3");
            }
        };

        when(artDao.findById(art1.getId())).thenReturn(art1);
        when(artDao.findById(art2.getId())).thenReturn(art2);
        doThrow(DataAccessException.class).when(artDao).create(art1);
        doThrow(DataAccessException.class).when(artDao).create(null);
        doThrow(DataAccessException.class).when(artDao).delete(null);

    }

    @Test
    public void testBaseObjectsProperties() {
        assertNotEquals(art1, art2);
        assertNotEquals(art1.getId(), art2.getId());
        assertNotEquals(art1, art3);
        assertNotEquals(art1.getId(), art3.getId());
        assertNotEquals(art2, art3);
        assertNotEquals(art2.getId(), art3.getId());
    }

    @Test
    public void testFindById() {
        assertEquals(art1, service.findArtById(art1.getId()));
        assertEquals(art2, service.findArtById(art2.getId()));
    }

    @Test
    public void testFindByIdNull() {
        assertNull(service.findArtById(art3.getId()));
    }

    @Test
    public void testCreate() {
        assertEquals(art3, service.createArt(art3));
        verify(artDao, times(1)).create(art3);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateAlreadyExisting() {
        service.createArt(art1);
        verify(artDao, times(1)).create(art1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateNull() {
        service.createArt(null);
    }

    @Test
    public void testDelete() {
        service.deleteArt(art1);
        verify(artDao, times(1)).delete(art1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteNull() {
        service.deleteArt(null);
    }
}
