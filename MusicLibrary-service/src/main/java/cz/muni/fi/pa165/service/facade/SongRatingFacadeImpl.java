/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.SongRatingFacade;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.api.dto.SongRatingDTO;
import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.entity.SongRating;
import cz.muni.fi.pa165.service.SongRatingService;
import cz.muni.fi.pa165.service.BeanMappingService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SongRatingFacadeImpl implements SongRatingFacade {

    @Autowired
    private SongRatingService ratingService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long create(SongRatingDTO rating) {
        SongRating ar = fromDTO(rating);
        ar = ratingService.create(ar);
        return ar.getId();
    }

    @Override
    public SongRatingDTO findById(Long id) {
        return toDTO(ratingService.findById(id));
    }

    @Override
    public void delete(SongRatingDTO rating) {
        ratingService.delete(fromDTO(rating));
    }

    @Override
    public void updateRating(SongRatingDTO rating, double newValue) {
        ratingService.updateRating(fromDTO(rating), newValue);
    }

    @Override
    public void updateComment(SongRatingDTO rating, String comment) {
        ratingService.updateComment(fromDTO(rating), comment);
    }

    @Override
    public Double avgForSong(SongDTO song, Date upTo) {
        return ratingService.avgForSong(beanMappingService.mapTo(song, Song.class), upTo);
    }

    @Override
    public List<SongRatingDTO> findAll() {
        return beanMappingService.mapTo(ratingService.findAll(), SongRatingDTO.class);
    }

    private SongRating fromDTO(SongRatingDTO dto) {
        return beanMappingService.mapTo(dto, SongRating.class);
    }

    private SongRatingDTO toDTO(SongRating r) {
        return r != null ? beanMappingService.mapTo(r, SongRatingDTO.class) : null;
    }
}
