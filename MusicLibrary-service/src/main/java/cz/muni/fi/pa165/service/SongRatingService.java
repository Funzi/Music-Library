/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.entity.SongRating;
import java.util.Date;
import java.util.List;

/**
 * @author Martin Kulisek
 */
public interface SongRatingService {

    SongRating create(SongRating rating);

    SongRating findById(Long id);

    void delete(SongRating rating);

    void updateRating(SongRating rating, double newValue);

    void updateComment(SongRating rating, String comment);

    Double avgForSong(Song song, Date upTo);

    List<SongRating> findAll();
}
