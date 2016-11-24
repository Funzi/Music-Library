package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.AlbumRating;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jan Stourac
 */
@Repository
public class AlbumRatingDaoImpl implements AlbumRatingDao {

	@PersistenceContext
    private EntityManager em;

	@Override
	public void create(AlbumRating rating) {
		em.persist(rating);
	}

	@Override
	public AlbumRating update(AlbumRating rating) {
		return em.merge(rating);
	}

	@Override
	public void delete(AlbumRating rating) {
		em.remove(em.merge(rating));
	}

	@Override
	public AlbumRating findById(Long id) {
		return em.find(AlbumRating.class, id);
	}

	@Override
	public List<AlbumRating> findAll() {
		return em.createQuery("select r from AlbumRating r", AlbumRating.class).getResultList();
	}

	@Override
	public Double avg(Album album, Date upTo) {
		return em.createQuery("select avg(r.rvalue) from AlbumRating r where r.album = :album and r.added <= :upTo", Double.class)
				.setParameter("album", album)
				.setParameter("upTo", upTo)
				.getSingleResult();
	}

}
