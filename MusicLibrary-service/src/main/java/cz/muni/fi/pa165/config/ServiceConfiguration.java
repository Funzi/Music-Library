/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.api.dto.GenreDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Song;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author Martin Kulisek
 */
@Configuration
@Import(AppContext.class)
@ComponentScan(basePackages = {"cz.muni.fi.pa165"})
public class ServiceConfiguration {

    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    /**
     * Custom config for Dozer if needed
     *
     * @author nguyen
     *
     */
    public class DozerCustomConfig extends BeanMappingBuilder {

        @Override
        protected void configure() {
            mapping(Song.class, SongDTO.class);
            mapping(Genre.class, GenreDTO.class);
        }
    }

}
