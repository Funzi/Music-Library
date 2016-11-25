/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.AlbumRatingFacade;
import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.AlbumRatingDTO;
import cz.muni.fi.pa165.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 *
 * @author David Pribula
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class AlbumRatingFacadeImplTest extends AbstractTestNGSpringContextTests{
    
    @Autowired
    private AlbumRatingFacade albumRatingFacade;
    private AlbumDTO album;
    private AlbumRatingDTO rating;
    private AlbumRatingDTO rating2;
    
    @BeforeMethod
    public void setUp() {
        rating = new AlbumRatingDTO() {
            {
                setRvalue(5);
                setAlbum(album);
            }
        };
        
        rating2 = new AlbumRatingDTO() {
            {
                setRvalue(3);
                setAlbum(album);
            }
        };
                
        album = new AlbumDTO() {
            {
                setId(null);
                setTitle("Album title");
                setCommentary("albumCommentary");
            }
        };
    }
    
    @Test
    private void createTest() {
        albumRatingFacade.create(rating);
    }
    
}
