package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.api.dto.ArtDTO;

/**
 * Created by olda on 23.11.2016.
 */
public interface ArtFacade {

    Long createArt(ArtDTO artDTO);
    ArtDTO getArtById(Long id);
    void deleteArt(ArtDTO artDTO);
}
