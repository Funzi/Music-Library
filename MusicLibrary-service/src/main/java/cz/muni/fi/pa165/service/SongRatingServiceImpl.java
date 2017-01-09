/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.SongRatingDao;
import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.entity.SongRating;
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
        songRatingDao.create(rating);
        return rating;
    }

    @Override
    public void delete(SongRating rating) {
        songRatingDao.delete(rating);
    }

    @Override
    public void updateRating(SongRating rating, double newValue) {
        rating.setRvalue(newValue);
        songRatingDao.update(rating);
    }

    @Override
    public void updateComment(SongRating rating, String comment) {
        rating.setComment(comment);
        songRatingDao.update(rating);
    }

    @Override
    public SongRating findById(Long id) {
        return songRatingDao.findById(id);
    }

    @Override
    public Double avgForSong(Song song, Date upTo) {
        return songRatingDao.avg(song, upTo);
    }

    @Override
    public List<SongRating> findAll() {
        return songRatingDao.findAll();
    }

}
