/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.api.dto.SongRatingDTO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Martin Kulisek
 */
public interface SongRatingFacade {

    Long create(SongRatingDTO rating);

    SongRatingDTO findById(Long id);

    void delete(SongRatingDTO rating);

    void updateRating(SongRatingDTO rating, double newValue);

    void updateComment(SongRatingDTO rating, String comment);

    Double avgForSong(SongDTO song, Date upTo);

    List<SongRatingDTO> findAll();
}
