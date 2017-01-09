package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.AlbumRatingDao;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.AlbumRating;
import cz.muni.fi.pa165.exceptions.DataAccessException;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jan Stourac
 */
@Service
public class AlbumRatingServiceImpl implements AlbumRatingService {

	@Inject
    private AlbumRatingDao albumRatingDao;

	@Override
	public AlbumRating create(AlbumRating rating) {
            try {
                albumRatingDao.create(rating);
            }catch(Exception e) {
                throw new DataAccessException(e);
            }	
            return rating;
	}

	@Override
	public void delete(AlbumRating rating) {
            try {
                albumRatingDao.delete(rating);
            }catch(Exception e) {
                throw new DataAccessException(e);
            }	
	}

	@Override
	public void updateRating(AlbumRating rating, double newValue) {
            rating.setRvalue(newValue);
            try {
                albumRatingDao.update(rating);
            }catch(Exception e) {
                throw new DataAccessException(e);
            }	
	}

	@Override
	public void updateComment(AlbumRating rating, String comment) {
            rating.setComment(comment);
            try {
                albumRatingDao.update(rating);
            }catch(Exception e) {
                throw new DataAccessException(e);
            }	
	}

	@Override
	public AlbumRating findById(Long id) {
            try {
                return albumRatingDao.findById(id);
            }catch(Exception e) {
                throw new DataAccessException(e);
            }	
	}

	@Override
	public Double avgForAlbum(Album album, Date upTo) {
            try {
                return albumRatingDao.avg(album, upTo);
            }catch(Exception e) {
                throw new DataAccessException(e);
            }		
	}

	@Override
	public List<AlbumRating> findAll() {
            try {
                return albumRatingDao.findAll();
            }catch(Exception e) {
                throw new DataAccessException(e);
            }		
	}

}
