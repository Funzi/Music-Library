package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.*;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.exceptions.DataAccessException;
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
        if (album == null) throw new NullPointerException("album is null");
        if (album.getArt() != null) createOrUpdateArt(album.getArt());

        if (album.getId() == null) {
            albumDao.create(album);
        } else {
            Album albumCheck = albumDao.findById(album.getId());
            if (albumCheck == null) throw new DataAccessException("Error, can not find album with #id="+ album.getId()+" id Database");
            albumDao.update(album);
        }

        if (album.getRatings() != null && !album.getRatings().isEmpty()) {
            album.getRatings().forEach(this::createOrUpdateAlbumRating);
        }

        if (album.getSongs() != null && !album.getSongs().isEmpty()) {
            album.getSongs().forEach(this::createOrUpdateSong);
        }


        return  null;
    }

    private void createOrUpdateSong(Song song) {
        if (song.getGenre() != null) createOrUpdateGenre(song.getGenre());

        if (song.getMusician() != null) createOrUpdateMusician(song.getMusician());

        if (song.getId() != null) {
            Song songCheck = songDao.findById(song.getId());
            if (songCheck == null) throw new DataAccessException("Error, can not find Song with #id="+ song.getId()+" in Database");
            songDao.update(song);
        }else {
            songDao.create(song);
        }
    }

    private void createOrUpdateMusician(Musician musician) {
        if (musician.getId() != null) {
            if (musicianDao.findById(musician.getId()) == null) throw new DataAccessException("Error, can not find Musician with #id="+ musician.getId()+" in Database");
            musicianDao.update(musician);
        }else {
            musicianDao.create(musician);
        }
    }

    private void createOrUpdateGenre(Genre genre) {
        if (genre.getId() != null) {
            if (genreDao.findById(genre.getId()) == null) throw new DataAccessException("Error, can not find Genre with #id="+ genre.getId()+" in Database");
            genreDao.update(genre);
        }else {
            genreDao.create(genre);
        }
    }

    private void createOrUpdateAlbumRating(AlbumRating ar) {
        if (ar.getId() != null) {
            AlbumRating albumRating = albumRatingDao.findById(ar.getId());
            if (albumRating == null) throw new DataAccessException("Error, can not find Song with #id="+ ar.getId()+" in Database");
            albumRatingDao.update(ar);
        }else {
            albumRatingDao.create(ar);
        }
    }

    private void createOrUpdateArt(Art art) {
        if (art.getId() != null) {
            Art artCheck = artDao.findById(art.getId());
            if (artCheck == null) throw new DataAccessException("Error, can not find Art with #id="+ art.getId()+" in Database");
            artDao.update(art);
        }else {
            artDao.create(art);
        }
    }




}
