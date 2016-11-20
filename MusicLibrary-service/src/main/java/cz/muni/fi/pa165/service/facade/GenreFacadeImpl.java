/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.GenreFacade;
import cz.muni.fi.pa165.api.dto.GenreDTO;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.GenreService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
@Transactional
public class GenreFacadeImpl implements GenreFacade {

    @Autowired
    private GenreService genreService;

    @Autowired
    private BeanMappingService beanMappingService;
    
    @Override
    public List<GenreDTO> getAllGenres() {
        return beanMappingService.mapTo(genreService.findAll(), GenreDTO.class);
    }

    @Override
    public GenreDTO getGenreById(Long id) {
        Genre genre = genreService.findById(id);
        return (genre == null) ? null : beanMappingService.mapTo(genre, GenreDTO.class);
    }
}
