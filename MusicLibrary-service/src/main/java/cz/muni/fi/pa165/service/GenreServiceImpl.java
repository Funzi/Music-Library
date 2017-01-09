/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.GenreDao;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.exceptions.DataAccessException;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author David Pribula
 */
@Service
public class GenreServiceImpl implements GenreService {

    @Inject
    private GenreDao genreDao;

    @Override
    public List<Genre> findAll() {
        try {
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
        return genreDao.findAll();
    }

    @Override
    public Genre findById(Long id) {
        try {
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
        return genreDao.findById(id);
    }

    @Override
    public Genre create(Genre genre) {
        try {
            genreDao.create(genre);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
        return genre;
    }

    @Override
    public void delete(Genre genre) {
        try {
            genreDao.delete(genre);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void updateGenre(Genre genre) {
        try {
            genreDao.update(genre);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }
}
