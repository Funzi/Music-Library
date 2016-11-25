/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.api.GenreFacade;
import cz.muni.fi.pa165.api.dto.GenreDTO;
import cz.muni.fi.pa165.config.ServiceConfiguration;
import cz.muni.fi.pa165.dao.GenreDao;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.BeanMappingServiceImpl;
import cz.muni.fi.pa165.service.GenreService;
import cz.muni.fi.pa165.service.GenreServiceImpl;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit test for facade layer of Genre
 * 
 * @author David Pribula
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class GenreFacadeImplTest extends AbstractTestNGSpringContextTests{
        
    @Autowired
    private GenreFacade genreFacade;
      
    GenreDTO genreDTO1;
    GenreDTO genreDTO2;
    
    @BeforeMethod
    public void setUp() {
        
        genreDTO1 = new GenreDTO() {
            {
                setId(1L);
                setName("Rock");
            }
        };
        genreDTO2 = new GenreDTO() {
            {
                setId(2L);
                setName("Jazz");
            }
        };
    }
    
    @Test
    public void createTest() {
        Long id = genreFacade.createGenre(genreDTO1);
        
        assertNotEquals(id,0L);
    }
    
    @Test
    public void getAllGenresTest() {
        genreFacade.createGenre(genreDTO1);
        genreFacade.createGenre(genreDTO2);
        List<GenreDTO> genres = genreFacade.getAllGenres();

        assertEquals(genres.size(),2);
        assertEquals(genres.get(0).getName(), genreDTO1.getName());
        assertEquals(genres.get(1).getName(), genreDTO2.getName());
    }
    
    @Test
    public void getGenreById() {
        Long id = genreFacade.createGenre(genreDTO1);
        
        assertEquals(genreDTO1.getName(),genreFacade.getGenreById(id).getName());
    }
    
    @Test
    public void deleteTest() {
        Long id = genreFacade.createGenre(genreDTO1);
        
        genreFacade.deleteGenre(genreFacade.getGenreById(id));
        assertNull(genreFacade.getGenreById(id));
        assertEquals(genreFacade.getAllGenres().size(),0);
    }
}
