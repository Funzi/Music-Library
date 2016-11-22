package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.AlbumDao;
import cz.muni.fi.pa165.dao.SongDao;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Song;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Created by olda on 22.11.2016.
 */
@Service
public class AlbumServiceImpl implements AlbumService {

    @Inject
    AlbumDao albumDao;

    @Inject
    SongDao songDao;

    @Override
    public Album findAlbumById(Long id) {
        return albumDao.findById(id);
    }

    @Override
    public List<Album> findAllAlbums() {
        return albumDao.findAll();
    }

    @Override
    public Album createAlbum(Album album) {
        albumDao.create(album);
        return album;
    }

    @Override
    public void deleteAlbum(Album album) {
        albumDao.delete(album);
    }

    @Override
    public void changeCommentary(Album album, String newCommentary) {
        Album alb = albumDao.findById(album.getId());
        alb.setCommentary(newCommentary);
        albumDao.update(alb);
    }

    @Override
    public List<Album> findAlbumsByMusicianId(Long musicianId) {
        return albumDao.findAlbumByMusicianId(musicianId);
    }

    @Override
    public List<Album> findAlbumsByReleaseDates(LocalDate from, LocalDate to) {
        return albumDao.findAlbumsByReleaseDates(from, to);
    }

    @Override
    public List<Album> findAlbumsByPartialTitle(String partialTitle) {
        return albumDao.findAlbumsByPartialTitle(partialTitle);
    }
}
