/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.SongRatingDao;
import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.entity.SongRating;
import cz.muni.fi.pa165.exceptions.DataAccessException;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Martin Kulisek
 */
@Service
public class SongRatingServiceImpl implements SongRatingService {

    @Inject
    private SongRatingDao songRatingDao;

    @Override
    public SongRating create(SongRating rating) {
        try {
            songRatingDao.create(rating);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
        return rating;
    }

    @Override
    public void delete(SongRating rating) {
        try {
            songRatingDao.delete(rating);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void updateRating(SongRating rating, double newValue) {
        rating.setRvalue(newValue);
        try {
            songRatingDao.update(rating);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void updateComment(SongRating rating, String comment) {
        rating.setComment(comment);
        try {
            songRatingDao.update(rating);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public SongRating findById(Long id) {
        try {
            return songRatingDao.findById(id);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Double avgForSong(Song song, Date upTo) {
        try {
            return songRatingDao.avg(song, upTo);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<SongRating> findAll() {
        try {
            return songRatingDao.findAll();
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

}
