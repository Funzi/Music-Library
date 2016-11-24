package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.AlbumRatingDTO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jan Stourac
 */
public interface AlbumRatingFacade {
	Long create(AlbumRatingDTO rating);
	AlbumRatingDTO findById(Long id);
    void delete(AlbumRatingDTO rating);
	void updateRating(AlbumRatingDTO rating, double newValue);
	void updateComment(AlbumRatingDTO rating, String comment);
	Double avgForAlbum(AlbumDTO album, Date upTo);
	List<AlbumRatingDTO> findAll();
}
