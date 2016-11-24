package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.AlbumRating;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jan Stourac
 */
public interface AlbumRatingDao {

	/**
	 * Stores given rating in database.
	 *
	 * @param rating rating to store
	 */
	void create(AlbumRating rating);

	/**
	 * Updates given rating in database.
	 *
	 * @param rating rating to update
	 * @return rating updated instance
	 */
	AlbumRating update(AlbumRating rating);

	/**
	 * Deletes given rating from database.
	 *
	 * @param rating rating to delete
	 */
	void delete(AlbumRating rating);

	/**
	 * Returns rating with given id or null.
	 *
	 * @param id rating id
	 * @return rating with given id
	 */
	AlbumRating findById(Long id);

	/**
	 * Returns average rating for given album until this date. If you are
	 * interested in global average for album, you can use attribute
	 * {@link Album#avgRating}.
	 *
	 * @param album album
	 * @param upTo max date
	 * @return average rating
	 */
	Double avg(Album album, Date upTo);

	/**
	 * Returns all ratings from database.
	 *
	 * @return list containing all ratings
	 */
	List<AlbumRating> findAll();
}
