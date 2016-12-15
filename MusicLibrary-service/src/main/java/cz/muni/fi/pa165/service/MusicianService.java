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

    List<Musician> findAll();
    Musician findById(Long id);
    Musician findByName(String name);
    Musician create(Musician musician);
    void delete(Musician musician);
    public void update(Musician mapTo);
}
