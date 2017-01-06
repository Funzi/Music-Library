package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.*;
import cz.muni.fi.pa165.entity.*;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * Created by olda on 22.11.2016.
 */
@Service
public class AlbumServiceImpl implements AlbumService {

    @Inject
    private AlbumDao albumDao;

    @Inject
    private SongDao songDao;

    @Inject
    private ArtDao artDao;

    @Inject
    private AlbumRatingDao albumRatingDao;

    @Inject
    private GenreDao genreDao;

    @Inject
    private MusicianDao musicianDao;

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
    public void updateAlbum(Album album) {
        albumDao.update(album);
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

    @Override
    public Album createOrUpdateEverything(Album album) {
        if (album.getArt() != null) {
            createOrUpdateArt(album.getArt());
        }

        if (album.getId() != null) {
            albumDao.update(album);
        } else {
            albumDao.create(album);
        }

        if (album.getRatings() != null && !album.getRatings().isEmpty()) {
            album.getRatings().forEach(this::createOrUpdateAlbumRating);
        }

        if (album.getSongs() != null && !album.getSongs().isEmpty()) {
            album.getSongs().forEach(this::createOrUpdateSong);
        }

        return album;
    }

    private void createOrUpdateSong(Song song) {
        if (song.getGenre() != null) {
            createOrUpdateGenre(song.getGenre());
        }

        if (song.getMusician() != null) {
            createOrUpdateMusician(song.getMusician());
        }

        if (song.getId() != null) {
            songDao.update(song);
        } else {
            songDao.create(song);
        }
    }

    private void createOrUpdateMusician(Musician musician) {
        if (musician.getId() != null) {
            musicianDao.update(musician);
        } else {
            musicianDao.create(musician);
        }
    }

    private void createOrUpdateGenre(Genre genre) {
        if (genre.getId() != null) {
            genreDao.update(genre);
        } else {
            genreDao.create(genre);
        }
    }

    private void createOrUpdateAlbumRating(AlbumRating ar) {
        if (ar.getId() != null) {
            albumRatingDao.update(ar);
        } else {
            albumRatingDao.create(ar);
        }
    }

    private void createOrUpdateArt(Art art) {
        if (art.getId() != null) {
            artDao.update(art);
        } else {
            artDao.create(art);
        }
    }

    @Override
    public List<Album> getAlbums(List<Long> musicians, List<Long> genres) {
        return albumDao.getAlbums(musicians, genres);
    }
}
