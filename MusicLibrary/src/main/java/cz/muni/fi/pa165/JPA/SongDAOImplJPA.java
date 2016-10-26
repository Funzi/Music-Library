/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.JPA;

import cz.muni.fi.pa165.dao.SongDao;
import cz.muni.fi.pa165.entity.Song;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author david
 */
public class SongDAOImplJPA implements SongDao{

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void createSong(Song song) {
        em.persist(song);
    }

    @Override
    public void updateSong(Song song) {
        em.merge(song);
    }

    @Override
    public void deleteSong(Song song) {
        em.remove(song);
    }

    @Override
    public void findSongById(Long id) {
        em.find(Song.class, id);
    }

    @Override
    public List<Song> getAllSongs() {
        return em.createQuery("SELECT s FROM SONG s", Song.class).getResultList();
    }
    
}
