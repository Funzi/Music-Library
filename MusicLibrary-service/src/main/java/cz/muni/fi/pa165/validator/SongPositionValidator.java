package cz.muni.fi.pa165.validator;

import cz.muni.fi.pa165.api.AlbumFacade;
import cz.muni.fi.pa165.api.dto.SongCreateDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Jan Stourac
 */
@Component
public class SongPositionValidator implements Validator {

	@Autowired
	private AlbumFacade albumFacade;

	@Override
	public boolean supports(Class<?> type) {
		return SongCreateDTO.class.equals(type) || SongDTO.class.equals(type);
	}

	@Override
	public void validate(Object o, Errors errors) {
		boolean isUnique = o.getClass().equals(SongDTO.class) ? isUnique((SongDTO) o) : isUniqueCreate((SongCreateDTO) o);
		if (!isUnique) {
			errors.rejectValue("position", "song.position.notunique");
		}
	}

	private boolean isUnique(SongDTO song) {
		return song.getAlbum().getSongs().stream().allMatch(s -> Objects.equals(song.getId(), s.getId()) || s.getPosition() != song.getPosition());
	}

	private boolean isUniqueCreate(SongCreateDTO song) {
		return albumFacade.getAlbumById(song.getAlbumId()).getSongs().stream().allMatch(s -> Objects.equals(song.getId(), s.getId()) || s.getPosition() != song.getPosition());
	}

}
