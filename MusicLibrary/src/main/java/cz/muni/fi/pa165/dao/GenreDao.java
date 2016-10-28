/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Genre;
import java.util.List;

/**
 * Data access object layer interface for Genre entity.
 * @author Martin Kulisek
 */
public interface GenreDao {

    /**
     * Finds genre by given id
     * @param id
     * @return genre object with given id
     */
    public Genre findById(Long id);

    /**
     * Stores given genre in database
     * @param genre genre object to store
     */
    public void create(Genre genre);

    /**
     * Updates given genre in database
     * @param genre genre object to update
     * @return updated genre object
     */
    public Genre update(Genre genre);

    /**
     * Deletes given genre from database
     * @param genre genre object to delete
     */
    public void delete(Genre genre);

    /**
     * Finds all genres in database
     * @return list of all genres in database
     */
    public List<Genre> findAll();

}
