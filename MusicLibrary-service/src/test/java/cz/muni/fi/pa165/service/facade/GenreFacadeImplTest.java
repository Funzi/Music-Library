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
      
    Genre genre1;
    Genre genre2;
    GenreDTO genreDTO1;
    
    @BeforeMethod
    public void setUp() {
               
        genre1 = new Genre() {
            {
                setId(1L);
                setName("Rock");
            }
        };
        genreDTO1 = new GenreDTO() {
            {
                setId(1L);
                setName("Rock");
            }
        };
        genre2 = new Genre() {
            {
                setId(2L);
                setName("Jazz");
            }
        };
        
        List<Genre> genres = new ArrayList<Genre>() {
            {
                add(genre1);
                add(genre2);
            }
        };
    }
    
    @Test
    public void getAllGenresTest() {
        
    }
    
    @Test
    public void getGenreById() {
        Long id = genreFacade.createGenre(genreDTO1);
        
        assertEquals(genreDTO1.getName(),genreFacade.getGenreById(id).getName());
    }
}
