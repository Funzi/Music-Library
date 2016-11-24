/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.GenreDao;
import cz.muni.fi.pa165.entity.Genre;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for GenreService
 * 
 * @author David Pribula
 */
public class GenreServiceTest {
    
    @InjectMocks
    private GenreService service = new GenreServiceImpl();
    
    @Mock
    private GenreDao genreDao;
    
    private Genre genreRock;
    private Genre genreJazz;
    
    private Long nonExistingId;
    
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        genreRock = new Genre() {
            {
                setId(1L);
                setName("Rock");
            }
        };
        
        genreJazz = new Genre() {
            {
                setId(2L);
                setName("Jazz");
            }
        };
        
        List<Genre> genres = new ArrayList<Genre>() {
            {
                add(genreRock);
                add(genreJazz);
            }
        };
        
        nonExistingId = 100L;
        
        when(genreDao.findById(genreRock.getId())).thenReturn(genreRock);
        when(genreDao.findById(genreJazz.getId())).thenReturn(genreJazz);
        when(genreDao.findAll()).thenReturn(genres);
        
        doThrow(PersistenceException.class).when(genreDao).create(genreRock);
        doThrow(IllegalArgumentException.class).when(genreDao).create(null);
        doThrow(IllegalArgumentException.class).when(genreDao).delete(null);
    }
    
    @Test
    public void testBaseObjectsProperties() {
        assertNotEquals(genreRock,genreJazz);
        assertNotEquals(genreRock.getId(),genreJazz.getId());
    }
    
    @Test
    public void testFindById() {
        assertSame(genreRock, service.findById(genreRock.getId()));
        assertSame(genreJazz, service.findById(genreJazz.getId()));
    }
    
    @Test
    public void testFindByNonExistingId() {
        assertNull(service.findById(nonExistingId));
    }
    
    @Test
    public void testFindAll() {
        List<Genre> genres = service.findAll();
        assertNotNull(genres);
        assertEquals(genres.size(),2);
        assertTrue(genres.contains(genreRock));
        assertTrue(genres.contains(genreJazz));
    }
    
    @Test
    public void testCreate() {
        assertEquals(genreJazz, service.create(genreJazz));
        verify(genreDao, times(1)).create(genreJazz);
    }
    
    @Test(expectedExceptions = PersistenceException.class)
    public void testCreateAlreadyExisting() {
        assertEquals(genreRock, service.create(genreRock));
        verify(genreDao, times(1)).create(genreRock);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNull() {
        service.create(null);
    }
    
    @Test 
    public void testDelete() {
        service.delete(genreRock);
        verify(genreDao, times(1)).delete(genreRock);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNull() {
        service.delete(null);
    }

    
    
    
}
