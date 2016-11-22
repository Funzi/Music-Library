package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Musician;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 * Implementation of data access layer for Musician entity.
 *
 * @author Jan Stourac
 */
@Repository
@Transactional
public class MusicianDaoImpl implements MusicianDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Musician findById(Long id) {
		return em.find(Musician.class, id);
	}

	@Override
	public Musician findByName(String name) {
		try {
			return em.createQuery("select m from Musician m where m.name = :name", Musician.class)
					.setParameter("name", name)
					.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Musician> findAll() {
		return em.createQuery("select m from Musician m", Musician.class)
				.getResultList();
	}

	@Override
	public Musician update(Musician musician) {
		return em.merge(musician);
	}

	@Override
	public void create(Musician musician) {
		em.persist(musician);
	}

	@Override
	public void delete(Musician musician) {
		em.remove(em.merge(musician));
	}

}
