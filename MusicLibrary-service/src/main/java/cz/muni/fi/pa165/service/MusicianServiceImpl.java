package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MusicianDao;
import cz.muni.fi.pa165.entity.Musician;
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
        return musicianDao.findAll();
    }

    @Override
    public Musician findById(Long id) {
        return musicianDao.findById(id);
    }

	@Override
	public Musician findByName(String name) {
		return musicianDao.findByName(name);
	}

    @Override
    public Musician create(Musician musician) {
        musicianDao.create(musician);
		return musician;
    }

    @Override
    public void update(Musician musician) {
        musicianDao.update(musician);
    }
    
    @Override
    public void delete(Musician musician) {
        musicianDao.delete(musician);
    }

}
