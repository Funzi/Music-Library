package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.api.dto.ArtDTO;
import cz.muni.fi.pa165.api.dto.GenreDTO;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.entity.Art;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.entity.Song;
import java.util.ArrayList;
import java.util.List;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import static org.dozer.loader.api.FieldsMappingOptions.customConverter;

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
        //dozer.setCustomConverters(getConverters());
        return dozer;
    }

    private List<CustomConverter> getConverters() {
        return new ArrayList<CustomConverter>() {
            {
                add(new Base64Converter());
            }
        };
    }

    public class DozerCustomConfig extends BeanMappingBuilder {

        @Override
        protected void configure() {
            mapping(Song.class, SongDTO.class);
            mapping(Genre.class, GenreDTO.class);
            mapping(Musician.class, MusicianDTO.class);
            mapping(Art.class, ArtDTO.class).fields("image", "image", customConverter(Base64Converter.class));

        }
    }

}
