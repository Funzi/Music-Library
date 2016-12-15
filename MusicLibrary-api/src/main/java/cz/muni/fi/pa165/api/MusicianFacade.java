package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.api.dto.MusicianDTO;
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

        void updateMusician(MusicianDTO musician);
        
	List<MusicianDTO> getAllMusicians();
}
