package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.AlbumFacade;
import cz.muni.fi.pa165.api.MusicianFacade;
import cz.muni.fi.pa165.api.SongFacade;
import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.MusicianDTO;
import cz.muni.fi.pa165.api.dto.SongCreateDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.config.ServiceConfiguration;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import cz.muni.fi.pa165.entity.Musician;
import cz.muni.fi.pa165.util.TestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 *
 * @author Jan Stourac
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class AlbumFacadeTest extends AbstractTestNGSpringContextTests {

	@Autowired
    private AlbumFacade albumFacade;

	@Autowired
    private SongFacade songFacade;

	@Autowired
	private MusicianFacade musicianFacade;

	@PersistenceUnit
	private EntityManagerFactory emf;

    private AlbumDTO album1;
	private SongDTO songDTO;
	private MusicianDTO musicianDTO;

    @BeforeMethod
    public void setUp() {
		album1 = new AlbumDTO() {
			{
				setId(null);
				setTitle("Album title");
				setCommentary("albumCommentary");
			}
		};

		songDTO = new SongDTO() {
			{
				setTitle("song");
			}
		};

		musicianDTO = new MusicianDTO() {
			{
				setName("musician");
			}
		};

    }

	@Test
	public void testSongDTOFetching() {
		Long albumId = albumFacade.createAlbum(album1);
		SongDTO song1 = new SongDTO() {
				{
					setTitle("Song title");
					setAlbum(albumFacade.getAlbumById(albumId));
				}
		};

		songFacade.createSong(convertToSongCreateDTO(song1));
		Set<SongDTO> songs = albumFacade.getAlbumById(albumId).getSongs();

		assertEquals(songs.size(), 1);
		songs.forEach(s -> assertEquals(s.getTitle(), song1.getTitle()));
	}

	@Test
	public void testCreateAlbum() {
		Long id = albumFacade.createAlbum(album1);
		assertNotNull(id);
	}

	@Test
	public void testGetAlbumById() {
		Long id = albumFacade.createAlbum(album1);
		album1.setId(id);
		AlbumDTO albumDTO = albumFacade.getAlbumById(id);
		assertEquals(album1.getId(), albumDTO.getId());
	}

	@Test
	public void testChangeCommentary() {
		Long id = albumFacade.createAlbum(album1);
		String str = "odjebat sa stadeto";
		albumFacade.changeCommentary(id, str);
		AlbumDTO albumDTO = albumFacade.getAlbumById(id);
		assertEquals(albumDTO.getCommentary(), str);
	}

	@Test
	public void testGetAlbumByMusician() {
		Long musicianId = musicianFacade.createMusician(musicianDTO);
		musicianDTO.setId(musicianId);

		Long albumId = albumFacade.createAlbum(album1);
		album1.setId(albumId);

		songDTO.setMusician(musicianDTO);
		songDTO.setAlbum(album1);
		Long songId = songFacade.createSong(convertToSongCreateDTO(songDTO));
		songDTO.setId(songId);

		List<AlbumDTO> list = albumFacade.getAlbumByMusician(musicianDTO);
		assertTrue(list.get(0).getId() == albumId);
	}

	@Test
	public void testGetAlbumByReleaseDates() {
		album1.setReleaseDate(LocalDate.of(2015, 1, 1));
		Long id = albumFacade.createAlbum(album1);
		album1.setId(id);
		List<AlbumDTO> list = albumFacade.getAlbumByReleaseDates(LocalDate.of(2009, 1, 1), LocalDate.of(2015, 1, 1));

		assertEquals(list.get(0).getId(), album1.getId());
	}

	@Test
	public void testGetAlbumByPartialTitle() {
		Long id = albumFacade.createAlbum(album1);
		album1.setId(id);
		List<AlbumDTO> list = albumFacade.getAlbumByPartialTitle("tit");

		assertEquals(list.get(0).getId(), id);
	}

	@Test
	public void testGetAllAlbums() {
		AlbumDTO albumDTO = new AlbumDTO();
		albumDTO.setTitle("netusim");
		albumFacade.createAlbum(albumDTO);
		albumFacade.createAlbum(album1);

		List<AlbumDTO> list = albumFacade.getAllAlbums();

		assertTrue(list.size() == 2);
	}

	@AfterMethod
	public void deleteData() {
		TestUtils.deleteAllData(emf);
	}

	private SongCreateDTO convertToSongCreateDTO(SongDTO s) {
		SongCreateDTO sd = new SongCreateDTO();
		sd.setTitle(s.getTitle());
		sd.setBitrate(s.getBitrate());
		sd.setCommentary(s.getCommentary());
		sd.setPosition(s.getPosition());
		sd.setAlbumId(s.getAlbum() != null ? s.getAlbum().getId() : null);
		sd.setMusicianId(s.getMusician() != null ? s.getMusician().getId() : null);
		sd.setGenreId(s.getGenre() != null ? s.getGenre().getId() : null);
		return sd;
	}

}
