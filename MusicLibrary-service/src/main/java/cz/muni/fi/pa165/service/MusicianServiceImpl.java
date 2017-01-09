package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MusicianDao;
import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.exceptions.DataAccessException;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jan Stourac
 */
@Service
public class MusicianServiceImpl implements MusicianService{

    @Inject
    private MusicianDao musicianDao;

    @Override
    public List<Musician> findAll() {
        try {
            return musicianDao.findAll();
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Musician findById(Long id) {
        try {
            return musicianDao.findById(id);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Musician findByName(String name) {
        try {
            return musicianDao.findByName(name);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Musician create(Musician musician) {
        try {
            musicianDao.create(musician);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
	return musician;
    }

    @Override
    public void update(Musician musician) {
        try {
            musicianDao.update(musician);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }
    
    @Override
    public void delete(Musician musician) {
        try {
            musicianDao.delete(musician);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

}
