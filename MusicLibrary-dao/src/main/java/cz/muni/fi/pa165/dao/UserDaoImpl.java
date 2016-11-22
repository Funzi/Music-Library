package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jan Stourac
 */
@Repository
public class UserDaoImpl implements UserDao {

	@PersistenceContext
    private EntityManager em;

	@Override
	public void create(User user) {
		em.persist(user);
	}

	@Override
	public User update(User user) {
		return em.merge(user);
	}

	@Override
	public void delete(User user) {
		em.remove(em.merge(user));
	}

	@Override
	public User findById(Long id) {
		return em.find(User.class, id);
	}

	@Override
	public User findByUsername(String username) {
		return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
				.setParameter("username", username)
				.getSingleResult();
	}

	@Override
	public List<User> findAll() {
		return em.createQuery("SELECT u FROM User u", User.class).getResultList();
	}

}
