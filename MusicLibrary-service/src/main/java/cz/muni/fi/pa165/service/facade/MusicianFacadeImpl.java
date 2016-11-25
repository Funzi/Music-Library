package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.MusicianFacade;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.MusicianService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jan Stourac
 */
@Service
@Transactional
public class MusicianFacadeImpl implements MusicianFacade {

	@Autowired
    private MusicianService musicianService;

    @Autowired
    private BeanMappingService beanMappingService;

	@Override
	public List<MusicianDTO> getAllMusicians() {
		return beanMappingService.mapTo(musicianService.findAll(), MusicianDTO.class);
	}

	@Override
	public Long createMusician(MusicianDTO musician) {
		Musician m = beanMappingService.mapTo(musician, Musician.class);
		m.setId(null);
		m = musicianService.create(m);
		return m.getId();
	}

	@Override
	public MusicianDTO getMusicianById(long id) {
		return beanMappingService.mapTo(musicianService.findById(id), MusicianDTO.class);
	}

	@Override
	public MusicianDTO getMusicianByName(String name) {
		return beanMappingService.mapTo(musicianService.findByName(name), MusicianDTO.class);
	}

	@Override
	public void deleteMusician(MusicianDTO musician) {
		musicianService.delete(beanMappingService.mapTo(musician, Musician.class));
	}

}
