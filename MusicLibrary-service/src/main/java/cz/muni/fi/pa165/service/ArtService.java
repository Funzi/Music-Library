package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Art;

/**
 * Created by olda on 22.11.2016.
 */
public interface ArtService {
    Art findArtById(Long id);
    Art createArt(Art art);
    void deleteArt(Art art);
}
