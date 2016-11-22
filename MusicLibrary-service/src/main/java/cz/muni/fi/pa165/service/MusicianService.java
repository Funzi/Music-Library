package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Musician;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jan Stourac
 */
@Service
public interface MusicianService {

    public List<Musician> findAll();
    public Musician findById(Long id);
	public Musician findByName(String name);
    public Musician create(Musician musician);
    public void delete(Musician musician);
}
