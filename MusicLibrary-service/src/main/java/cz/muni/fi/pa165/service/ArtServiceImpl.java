package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.ArtDao;
import cz.muni.fi.pa165.entity.Art;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by olda on 23.11.2016.
 */
@Service
public class ArtServiceImpl implements ArtService {

    @Inject
    private ArtDao artDao;

    @Override
    public Art findArtById(Long id) {
        return artDao.findById(id);
    }

    @Override
    public Art createArt(Art art) {
        artDao.create(art);
        return art;
    }

    @Override
    public void deleteArt(Art art) {
        artDao.delete(art);
    }
}
