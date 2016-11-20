/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.GenreDao;
import cz.muni.fi.pa165.entity.Genre;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author David Pribula
 */
@Service
public class GenreServiceImpl implements GenreService{

    @Inject
    private GenreDao genreDao;
    
    @Override
    public List<Genre> findAll() {
        return genreDao.findAll();
    }

    @Override
    public Genre findById(Long id) {
        return genreDao.findById(id);
    }

    @Override
    public void create(Genre genre) {
        genreDao.create(genre);
    }

    @Override
    public void delete(Genre genre) {
        genreDao.delete(genre);
    }
}
