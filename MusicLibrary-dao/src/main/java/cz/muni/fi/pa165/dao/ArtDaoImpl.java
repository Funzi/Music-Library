package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Art;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Created by olda on 17.11.2016.
 */
@Repository
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
