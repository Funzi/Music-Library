package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.AlbumRating;
import java.util.Date;
import java.util.List;

/**
 * @author jan Stourac
 */
public interface AlbumRatingService {
    AlbumRating create(AlbumRating rating);
	AlbumRating findById(Long id);
    void delete(AlbumRating rating);
	void updateRating(AlbumRating rating, double newValue);
	void updateComment(AlbumRating rating, String comment);
	Double avgForAlbum(Album album, Date upTo);
	List<AlbumRating> findAll();
}
