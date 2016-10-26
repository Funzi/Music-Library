package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Album;

import java.util.List;

/**
 * Created by olda on 26.10.2016.
 */
public interface AlbumDao {
    public Album findById(Long id);

    public void createAlbum(Album album);

    public Album updateAlbum(Album album);

    public void deleteAlbum(Album album);

    public List<Album> findAll();
}
