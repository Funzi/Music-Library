/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MusicianDao;
import cz.muni.fi.pa165.dao.SongDao;
import cz.muni.fi.pa165.entity.Song;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Martin Kulisek
 */
@Service
public class SongServiceImpl implements SongService {

    @Inject
    private SongDao songDao;
    
    @Inject
    private MusicianDao musicianDao;

    @Override
    public Song findById(Long id) {
        return songDao.findById(id);
    }

    @Override
    public List<Song> findAll() {
        return songDao.findAll();
    }

    @Override
    public Song create(Song s) {
        songDao.create(s);
        return s;
    }

    @Override
    public void delete(Song s) {
        songDao.delete(s);
    }

}
