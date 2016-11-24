package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Role;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jan Stourac
 */
@Repository
public class RoleDaoImpl implements RoleDao {

	@PersistenceContext
    private EntityManager em;

	@Override
	public void create(Role role) {
		em.persist(role);
	}

	@Override
	public Role update(Role role) {
		return em.merge(role);
	}

	@Override
	public void delete(Role role) {
		em.remove(em.merge(role));
	}

	@Override
	public Role findById(Long id) {
		return em.find(Role.class, id);
	}

	@Override
	public Role findByName(String name) {
		return em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
				.setParameter("name", name)
				.getSingleResult();
	}

	@Override
	public List<Role> findAll() {
		return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
	}

}
