package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Musician;
import java.util.List;

/**
 *
 * @author Jan Stourac
 */
public interface MusicianDao {

	public Musician findById(Long id);

	public void create(Musician c);

	public Musician update(Musician c);

	public void delete(Musician c);

	public List<Musician> findAll();
}
