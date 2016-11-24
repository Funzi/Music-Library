package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.AlbumRatingFacade;
import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.AlbumRatingDTO;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.AlbumRating;
import cz.muni.fi.pa165.service.AlbumRatingService;
import cz.muni.fi.pa165.service.BeanMappingService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jan Stourac
 */
public class AlbumRatingFacadeImpl implements AlbumRatingFacade {

	@Autowired
    private AlbumRatingService ratingService;

    @Autowired
    private BeanMappingService beanMappingService;

	@Override
	public Long create(AlbumRatingDTO rating) {
		AlbumRating ar = fromDTO(rating);
		ar = ratingService.create(ar);
		return ar.getId();
	}

	@Override
	public AlbumRatingDTO findById(Long id) {
		return toDTO(ratingService.findById(id));
	}

	@Override
	public void delete(AlbumRatingDTO rating) {
		ratingService.delete(fromDTO(rating));
	}

	@Override
	public void updateRating(AlbumRatingDTO rating, double newValue) {
		ratingService.updateRating(fromDTO(rating), newValue);
	}

	@Override
	public void updateComment(AlbumRatingDTO rating, String comment) {
		ratingService.updateComment(fromDTO(rating), comment);
	}

	@Override
	public Double avgForAlbum(AlbumDTO album, Date upTo) {
		return ratingService.avgForAlbum(beanMappingService.mapTo(album, Album.class), upTo);
	}

	@Override
	public List<AlbumRatingDTO> findAll() {
        return beanMappingService.mapTo(ratingService.findAll(), AlbumRatingDTO.class);
	}

	private AlbumRating fromDTO(AlbumRatingDTO dto) {
		return beanMappingService.mapTo(dto, AlbumRating.class);
	}

	private AlbumRatingDTO toDTO(AlbumRating ar) {
		return ar != null ? beanMappingService.mapTo(ar, AlbumRatingDTO.class) : null;
	}
}
