/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Genre;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author David Pribula
 */
@Service
public interface GenreService {

	List<Genre> findAll();

	Genre findById(Long id);

	Genre create(Genre genre);

	void delete(Genre genre);

	void updateGenre(Genre mapTo);
}
