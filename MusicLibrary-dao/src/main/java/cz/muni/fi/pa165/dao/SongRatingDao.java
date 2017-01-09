/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.entity.SongRating;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Martin Kulisek
 */
public interface SongRatingDao {

    /**
     * Stores given rating in database.
     *
     * @param rating rating to store
     */
    void create(SongRating rating);

    /**
     * Updates given rating in database.
     *
     * @param rating rating to update
     * @return rating updated instance
     */
    SongRating update(SongRating rating);

    /**
     * Deletes given rating from database.
     *
     * @param rating rating to delete
     */
    void delete(SongRating rating);

    /**
     * Returns rating with given id or null.
     *
     * @param id rating id
     * @return rating with given id
     */
    SongRating findById(Long id);

    /**
     * Returns average rating for given song until this date. If you are
     * interested in global average for song, you can use attribute
     * {@link Song#avgRating}.
     *
     * @param song song
     * @param upTo max date
     * @return average rating
     */
    Double avg(Song song, Date upTo);

    /**
     * Returns all ratings from database.
     *
     * @return list containing all ratings
     */
    List<SongRating> findAll();
}
