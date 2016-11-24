/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import config.ServiceConfiguration;
import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Song;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Martin Kulisek
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BeanMappingService beanMappingService;

    private Set<Song> songs = new HashSet<>();
    private List<Album> albums = new ArrayList<>();

    @BeforeMethod
    public void createAlbums() {
        Song song1 = new Song();
        song1.setId(2L);
        song1.setTitle("Forever Young");
        Song song2 = new Song();
        song2.setId(2L);
        song2.setTitle("Forever Old");

        Album alb1 = new Album();
        alb1.setId(4L);
        alb1.addSong(song1);
        alb1.setTitle("Sharp TV");
        albums.add(alb1);

        Album alb2 = new Album();
        alb2.setId(4L);
        alb2.addSong(song1);
        alb2.addSong(song2);
        alb2.setTitle("Radio");
        albums.add(alb2);

        Album alb3 = new Album();
        alb3.setId(4L);
        alb3.addSong(song2);
        alb3.setTitle("Spoon");
        albums.add(alb3);

        songs.add(song1);
        songs.add(song2);

    }

    @Test
    public void shouldMapInnerCategories() {
        List<AlbumDTO> cdtos = beanMappingService.mapTo(albums, AlbumDTO.class);
        Assert.assertEquals(cdtos.get(0).getSongs().size(), 1);

    }

}
