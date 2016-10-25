package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Musician;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
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
	public List<Musician> findAll() {
		return em.createQuery("select m from Musician m", Musician.class)
				.getResultList();
	}

	@Override
	public Musician update(Musician musician) {
		return em.merge(musician);
	}

	@Override
	public void create(Musician c) {
		em.persist(c);
	}

	@Override
	public void delete(Musician c) {
		em.remove(em.merge(c));
	}

}
