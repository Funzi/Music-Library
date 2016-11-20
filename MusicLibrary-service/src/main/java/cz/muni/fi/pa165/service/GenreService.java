/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Genre;
import java.util.List;

/**
 *
 * @author David Pribula
 */
public interface GenreService {
    
    public List<Genre>findAll();
    public Genre findById(Long id);
    void create(Genre genre);
    void remove(Genre genre);
    Genre findByName(String name);
}
