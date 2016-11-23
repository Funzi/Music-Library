package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.ArtFacade;
import cz.muni.fi.pa165.api.SongFacade;
import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.api.AlbumFacade;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.service.AlbumService;
import cz.muni.fi.pa165.service.ArtService;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Album mappedAlbum = new Album();
        mappedAlbum.setReleaseDate(albumDTO.getReleaseDate());
        mappedAlbum.setCommentary(albumDTO.getCommentary());
        mappedAlbum.setTitle(albumDTO.getTitle());

        mappedAlbum.setArt(artService.findArtById(albumDTO.getArt().getId()));

        Set<Song> songs = new HashSet<>();
        for (SongDTO s : albumDTO.getSongs()) {
            Song newSong = new Song();
            newSong.setId(s.getId());
            songs.add(newSong);
        }
        mappedAlbum.setSongs(songs);

        Album newAlbum = albumService.createAlbum(mappedAlbum);
        return newAlbum.getId();
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
}
