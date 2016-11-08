package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.api.MusicianService;
import cz.muni.fi.pa165.dao.MusicianDao;
import cz.muni.fi.pa165.entity.Musician;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Jan Stourac
 */
public class MusicianServiceImpl implements MusicianService {

	@Inject
	private MusicianDao musicianDao;

	@Override
	public List<Musician> getAllMusicians() {
		return musicianDao.findAll();
	}

}
