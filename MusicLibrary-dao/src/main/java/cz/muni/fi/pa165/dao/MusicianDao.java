package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Musician;
import java.util.List;

/**
 * Data access object layer interface for Musician entity.
 *
 * @author Jan Stourac
 */
public interface MusicianDao {

    /**
     * Returns musician with given id or null.
     *
     * @param id musician id
     * @return musician with given id
     */
    public Musician findById(Long id);

	/**
     * Returns musician with given name or null.
     *
     * @param name musician name
     * @return musician with given name
     */
    public Musician findByName(String name);

    /**
     * Stores given musician in database.
     *
     * @param musician musician to store
     */
    public void create(Musician musician);

    /**
     * Updates given musician in database.
     *
     * @param musician musician to update
     * @return updated instance
     */
    public Musician update(Musician musician);

    /**
     * Deletes given musician from database.
     *
     * @param musician musician to delete
     */
    public void delete(Musician musician);

    /**
     * Returns all musicians from database.
     *
     * @return list containing all musicians
     */
    public List<Musician> findAll();
}
