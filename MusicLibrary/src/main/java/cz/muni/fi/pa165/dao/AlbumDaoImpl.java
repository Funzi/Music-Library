package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Album;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by olda on 26.10.2016.
 */
@Repository
@Transactional
public class AlbumDaoImpl implements AlbumDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Album findById(Long id) {
        return em.find(Album.class, id);
    }

    @Override
    public void create(Album album) {
        em.persist(album);
    }

    @Override
    public Album update(Album album) {
        return em.merge(album);
    }

    @Override
    public void delete(Album album) {
        em.remove(em.merge(album));
    }

    @Override
    public List<Album> findAll() {
        return em.createQuery("select a from ALBUM a ", Album.class).getResultList();
    }
}
