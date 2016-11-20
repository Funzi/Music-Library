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
    
    public List<Genre>findAll();
    public Genre findById(Long id);
    public void create(Genre genre);
    public void delete(Genre genre);
}
