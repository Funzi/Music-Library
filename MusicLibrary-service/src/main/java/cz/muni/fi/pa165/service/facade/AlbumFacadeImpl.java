package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.AlbumFacade;
import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.service.AlbumService;
import cz.muni.fi.pa165.service.ArtService;
import cz.muni.fi.pa165.service.BeanMappingService;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by olda on 20.11.2016.
 */
@Service
@Transactional
public class AlbumFacadeImpl implements AlbumFacade {

	final static Logger LOG = LoggerFactory.getLogger(AlbumFacadeImpl.class);

	@Inject
	private BeanMappingService beanMappingService;

	@Inject
	private AlbumService albumService;

	@Inject
	private ArtService artService;

	@Override
	public Long createAlbum(AlbumDTO albumDTO) {
		Album mappedAlbum = beanMappingService.mapTo(albumDTO, Album.class);

		Album newAlbum = albumService.createAlbum(mappedAlbum);
		return newAlbum.getId();
	}

	@Override
	public void updateAlbum(AlbumDTO albumDTO) {
		albumService.updateAlbum(beanMappingService.mapTo(albumDTO, Album.class));
	}

	@Override
	public void deleteAlbum(AlbumDTO albumDTO) {
		Album album = new Album();
		album.setId(albumDTO.getId());
		albumService.deleteAlbum(album);
	}

	@Override
	public void changeCommentary(Long albumId, String commentary) {
		Album album = albumService.findAlbumById(albumId);
		albumService.changeCommentary(album, commentary);
	}

	@Override
	public AlbumDTO getAlbumById(Long id) {
		Album album = albumService.findAlbumById(id);
		return (album != null) ? beanMappingService.mapTo(album, AlbumDTO.class) : null;
	}

	@Override
	public List<AlbumDTO> getAlbumByMusician(MusicianDTO musicianDto) {
		List<Album> albums = albumService.findAlbumsByMusicianId(musicianDto.getId());
		return beanMappingService.mapTo(albums, AlbumDTO.class);
	}

	@Override
	public List<AlbumDTO> getAlbumByReleaseDates(LocalDate from, LocalDate to) {
		List<Album> albums = albumService.findAlbumsByReleaseDates(from, to);
		return beanMappingService.mapTo(albums, AlbumDTO.class);
	}

	@Override
	public List<AlbumDTO> getAlbumByPartialTitle(String partialTitle) {
		List<Album> albums = albumService.findAlbumsByPartialTitle(partialTitle);
		return beanMappingService.mapTo(albums, AlbumDTO.class);
	}

	@Override
	public List<AlbumDTO> getAllAlbums() {
		List<Album> albums = albumService.findAllAlbums();
		return beanMappingService.mapTo(albums, AlbumDTO.class);
	}

	@Override
	public List<AlbumDTO> getAlbums(List<Long> musicians, List<Long> genres) {
		return beanMappingService.mapTo(albumService.getAlbums(musicians, genres), AlbumDTO.class);
	}

}
