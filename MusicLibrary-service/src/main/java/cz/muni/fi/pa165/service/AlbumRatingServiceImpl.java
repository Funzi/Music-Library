package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.AlbumRatingDao;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.AlbumRating;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Jan Stourac
 */
public class AlbumRatingServiceImpl implements AlbumRatingService {

	@Inject
    private AlbumRatingDao albumRatingDao;

	@Override
	public AlbumRating create(AlbumRating rating) {
		albumRatingDao.create(rating);
		return rating;
	}

	@Override
	public void delete(AlbumRating rating) {
		albumRatingDao.delete(rating);
	}

	@Override
	public void updateRating(AlbumRating rating, double newValue) {
		rating.setRvalue(newValue);
		albumRatingDao.update(rating);
	}

	@Override
	public void updateComment(AlbumRating rating, String comment) {
		rating.setComment(comment);
		albumRatingDao.update(rating);
	}

	@Override
	public AlbumRating findById(Long id) {
		return albumRatingDao.findById(id);
	}

	@Override
	public Double avgForAlbum(Album album, Date upTo) {
		return albumRatingDao.avg(album, upTo);
	}

	@Override
	public List<AlbumRating> findAll() {
		return albumRatingDao.findAll();
	}

}
