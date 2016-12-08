package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Musician;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 * Implementation of data access layer for Album entity.
 *
 * @author Oldrich Konecny
 */
@Repository
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
		TypedQuery<Album> typedQuery = em.createQuery("SELECT a from Album a inner join a.songs s inner join s.musician m where m.id = :id", Album.class);
		typedQuery.setParameter("id", id);
		return typedQuery.getResultList();
	}

	@Override
	public List<Album> findAlbumsByReleaseDates(LocalDate from, LocalDate to) {
		TypedQuery<Album> typedQuery = em.createQuery("SELECT a from Album a where a.releaseDate BETWEEN :fromDate AND :toDate", Album.class);
		typedQuery.setParameter("fromDate", from);
		typedQuery.setParameter("toDate", to);
		return typedQuery.getResultList();
	}

	@Override
	public List<Album> findAlbumsByPartialTitle(String partialTitle) {
		TypedQuery<Album> typedQuery = em.createQuery("SELECT a from Album a where a.title like :title", Album.class);
		typedQuery.setParameter("title", "%" + partialTitle + "%");
		return typedQuery.getResultList();
	}

	@Override
	public List<Album> findBestRated(int limit) {
		return findBestRated(limit, new Date());
	}

	@Override
	public List<Album> findBestRated(int limit, Date upTo) {
		List<Album> albums = new ArrayList<>();

		em.createQuery("select r.album.id from AlbumRating r where r.added <= :upTo group by r.album order by avg(r.rvalue) desc", Long.class)
				.setParameter("upTo", upTo)
				.setMaxResults(limit)
				.getResultList()
				.stream()
				.filter(id -> id != null)
				.forEach(id -> albums.add(findById(id)));

		return albums;
	}

	@Override
	public List<Album> findBestRatedForMusician(int limit, Musician musician) {
		List<Album> albums = new ArrayList<>();
		em.createQuery("select r.album.id from AlbumRating r, Song s where r.album = s.album and s.musician = :musician group by r.album order by avg(r.rvalue) desc", Long.class)
				.setParameter("musician", musician)
				.setMaxResults(limit)
				.getResultList()
				.stream()
				.filter(id -> id != null)
				.forEach(id -> albums.add(findById(id)));
		return albums;
	}

	@Override
	public List<Album> getAlbums(List<Long> musicians, List<Long> genres) {
		boolean hasMusicians = musicians != null && !musicians.isEmpty();
		boolean hasGenres = genres != null && !genres.isEmpty();

		String query = "select a from Album a inner join a.songs s where "
				+ (hasMusicians ? "s.musician.id in (:musicians)" : "a.id = a.id")
				+ " and "
				+ (hasGenres ? "s.genre.id in (:genres)" : "a.id = a.id");

		TypedQuery<Album> q = em.createQuery(query, Album.class);
		if (hasMusicians) {
			q.setParameter("musicians", musicians);
		}

		if (hasGenres) {
			q.setParameter("genres", genres);
		}

		return q.getResultList();
	}
}
