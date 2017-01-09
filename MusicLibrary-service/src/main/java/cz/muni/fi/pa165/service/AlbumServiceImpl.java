package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.*;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.exceptions.DataAccessException;
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
        try {
            return albumDao.findAll();
        }catch(Exception e) {
            throw new DataAccessException(e);
        }	
    }

    @Override
    public Album createAlbum(Album album) {
        try {
                albumDao.create(album);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }	
        return album;
    }

    @Override
    public void updateAlbum(Album album) {
        try {
            albumDao.update(album);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }	
    }

    @Override
    public void deleteAlbum(Album album) {
        try {
                albumDao.delete(album);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }	
    }

    @Override
    public void changeCommentary(Album album, String newCommentary) {
        try {
            Album alb = albumDao.findById(album.getId());
            alb.setCommentary(newCommentary);
            albumDao.update(alb);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }	
        
    }

    @Override
    public List<Album> findAlbumsByMusicianId(Long musicianId) {
        try {
            return albumDao.findAlbumByMusicianId(musicianId);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<Album> findAlbumsByReleaseDates(LocalDate from, LocalDate to) {
        try {
            return albumDao.findAlbumsByReleaseDates(from, to);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<Album> findAlbumsByPartialTitle(String partialTitle) {
        try {
            return albumDao.findAlbumsByPartialTitle(partialTitle);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Album createOrUpdateEverything(Album album) {
        try {
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
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
        return album;
    }

    private void createOrUpdateSong(Song song) {
        try {
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
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    private void createOrUpdateMusician(Musician musician) {
        try {
            if (musician.getId() != null) {
            musicianDao.update(musician);
            } else {
                musicianDao.create(musician);
            }
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    private void createOrUpdateGenre(Genre genre) {
        try {
            if (genre.getId() != null) {
            genreDao.update(genre);
            } else {
                genreDao.create(genre);
            }
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    private void createOrUpdateAlbumRating(AlbumRating ar) {
        try {
            if (ar.getId() != null) {
            albumRatingDao.update(ar);
            } else {
                albumRatingDao.create(ar);
            }
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    private void createOrUpdateArt(Art art) {
        try {
            if (art.getId() != null) {
            artDao.update(art);
            } else {
                artDao.create(art);
            }
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<Album> getAlbums(List<Long> musicians, List<Long> genres) {
        try {
            return albumDao.getAlbums(musicians, genres);
        }catch(Exception e) {
            throw new DataAccessException(e);
        }
    }
}
