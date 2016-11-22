package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Album;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of data access layer for Album entity.
 *
 * @author Oldrich Konecny
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
		return em.createQuery("select a from Album a ", Album.class).getResultList();
	}

	@Override
	public List<Album> findAlbumByMusicianId(Long id) {
		return null;
		//em.createQuery("select a from Album a where ",Album.class).getResultList();
	}

	@Override
	public List<Album> findAlbumsByReleaseDates(LocalDate from, LocalDate to) {
		TypedQuery<Album> typedQuery = em.createQuery("SELECT a from Album a where a.releaseDate BETWEEN :from AND :to", Album.class);
		typedQuery.setParameter("from", from);
		typedQuery.setParameter("to", to);
		return typedQuery.getResultList();
	}

	@Override
	public List<Album> findAlbumsByPartialTitle(String partialTitle) {
		TypedQuery<Album> typedQuery = em.createQuery("SELECT a from Album a where a.title like :title", Album.class);
		typedQuery.setParameter("title", "%"+partialTitle+"%");
		return typedQuery.getResultList();
	}
}
