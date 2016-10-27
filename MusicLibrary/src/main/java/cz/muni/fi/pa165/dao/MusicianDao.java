package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Musician;
import java.util.List;

/**
 *
 * @author Jan Stourac
 */
public interface MusicianDao {

	public Musician findById(Long id);

	public void create(Musician musician);

	public Musician update(Musician musician);

	public void delete(Musician musician);

	public List<Musician> findAll();
}
