/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Song;
import cz.muni.fi.pa165.entity.SongRating;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Martin Kulisek
 */
@Repository
public class SongRatingDaoImpl implements SongRatingDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(SongRating rating) {
        em.persist(rating);
    }

    @Override
    public SongRating update(SongRating rating) {
        return em.merge(rating);
    }

    @Override
    public void delete(SongRating rating) {
        em.remove(em.merge(rating));
    }

    @Override
    public SongRating findById(Long id) {
        return em.find(SongRating.class, id);
    }

    @Override
    public List<SongRating> findAll() {
        return em.createQuery("select r from SongRating r", SongRating.class).getResultList();
    }

    @Override
    public Double avg(Song song, Date upTo) {
        return em.createQuery("select avg(r.rvalue) from SongRating r where r.song = :song and r.added <= :upTo", Double.class)
                .setParameter("song", song)
                .setParameter("upTo", upTo)
                .getSingleResult();
    }

}
