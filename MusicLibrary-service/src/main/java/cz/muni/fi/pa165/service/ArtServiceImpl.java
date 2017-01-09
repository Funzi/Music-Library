package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.ArtDao;
import cz.muni.fi.pa165.entity.Art;
import cz.muni.fi.pa165.exceptions.DataAccessException;
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
        try {
            return artDao.findById(id);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }	
    }

    @Override
    public Art createArt(Art art) {
        try {
            artDao.create(art);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
        return art;
    }

    @Override
    public void deleteArt(Art art) {
        try {
            artDao.delete(art);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }
}
