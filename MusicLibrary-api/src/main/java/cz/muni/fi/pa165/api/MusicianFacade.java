package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import java.util.List;

/**
 *
 * @author Jan Stourac
 */
public interface MusicianFacade {

	Long createMusician(MusicianDTO musician);

	MusicianDTO getMusicianById(long id);

	MusicianDTO getMusicianByName(String name);

	void deleteMusician(MusicianDTO musician);

	List<MusicianDTO> getAllMusicians();

	List<SongDTO> getSongsForMusician(MusicianDTO musician);
}
