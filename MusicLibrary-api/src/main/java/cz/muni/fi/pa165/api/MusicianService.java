package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.entity.Musician;
import java.util.List;

/**
 *
 * @author Jan Stourac
 */
public interface MusicianService {
	List<Musician> getAllMusicians();
}
