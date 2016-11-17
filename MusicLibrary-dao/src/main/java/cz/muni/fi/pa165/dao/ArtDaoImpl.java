package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Art;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by olda on 17.11.2016.
 */
@Repository
@Transactional
public class ArtDaoImpl implements ArtDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Art findById(Long id) {
        return em.find(Art.class, id);
    }

    @Override
    public void create(Art art) {
        em.persist(art);
    }

    @Override
    public Art update(Art art) {
        return em.merge(art);
    }

    @Override
    public void delete(Art art) {
        em.remove(em.merge(art));
    }

    @Override
    public List<Art> findAll() {
        return em.createQuery("SELECT a from Art a", Art.class).getResultList();
    }
}
