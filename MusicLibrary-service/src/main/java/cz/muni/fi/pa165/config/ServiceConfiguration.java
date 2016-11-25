package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.ArtDTO;
import cz.muni.fi.pa165.api.dto.GenreDTO;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.Art;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.entity.Song;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import static org.dozer.loader.api.FieldsMappingOptions.customConverter;
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

	public class DozerCustomConfig extends BeanMappingBuilder {

		@Override
		protected void configure() {
			mapping(Song.class, SongDTO.class);
			mapping(Genre.class, GenreDTO.class);
			mapping(Musician.class, MusicianDTO.class);
			mapping(Album.class, AlbumDTO.class);
			mapping(Art.class, ArtDTO.class).fields("image", "image", customConverter(Base64Converter.class));

		}
	}

}
