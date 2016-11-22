package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Musician;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by olda on 20.11.2016.
 */
public interface AlbumFacade {
    Long createAlbum(AlbumDTO albumDTO);
    void deleteAlbum(Long id);
    void changeCommentary(Long albumId, String commentary);
    AlbumDTO getAlbumById(Long id);
    List<AlbumDTO> getAlbumByMusician(MusicianDTO musicianDto);
    List<AlbumDTO> getAlbumByReleaseDates(LocalDate from, LocalDate to);
    List<AlbumDTO> getAlbumByPartialTitle(String partialTitle);
    List<AlbumDTO> getAllAlbums();
}
