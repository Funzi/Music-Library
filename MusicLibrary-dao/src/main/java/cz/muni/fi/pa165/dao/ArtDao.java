package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Art;

import java.util.List;

/**
 * Created by olda on 17.11.2016.
 */
public interface ArtDao {

    Art findById(Long id);

    void create(Art art);

    Art update(Art art);

    void delete(Art art);

    List<Art> findAll();


}
