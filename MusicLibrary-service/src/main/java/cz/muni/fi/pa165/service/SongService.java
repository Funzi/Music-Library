/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Song;
import java.util.List;

/**
 *
 * @author Martin Kulisek
 */
public interface SongService {

    public Song findById(Long id);

    public List<Song> findAll();

    public Song create(Song p);

    public List<Song> getSongsByMusicianName(String musicianName);

    public void delete(Song p);
}
