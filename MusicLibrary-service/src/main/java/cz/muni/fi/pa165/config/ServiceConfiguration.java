package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.AppContext;
import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.AlbumRatingDTO;
import cz.muni.fi.pa165.api.dto.ArtDTO;
import cz.muni.fi.pa165.api.dto.GenreDTO;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.entity.Album;
import cz.muni.fi.pa165.entity.AlbumRating;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

	@Bean
	public PasswordStrengthValidator passwordStrengthValidator() {
		DefaultPasswordStrengthValidator validator = new DefaultPasswordStrengthValidator();
		validator.setMinLength(8);
		validator.setMinGroupsCount(2);
		validator.addAllGroups();
		return validator;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public class DozerCustomConfig extends BeanMappingBuilder {

		@Override
		protected void configure() {
			mapping(Song.class, SongDTO.class);
			mapping(Genre.class, GenreDTO.class);
			mapping(Musician.class, MusicianDTO.class);
			mapping(AlbumRating.class, AlbumRatingDTO.class);
			mapping(Art.class, ArtDTO.class).fields("image", "image", customConverter(Base64Converter.class));
			mapping(Album.class, AlbumDTO.class).fields("releaseDate", "releaseDate", customConverter(LocalDateConvert.class));

		}
	}

}
