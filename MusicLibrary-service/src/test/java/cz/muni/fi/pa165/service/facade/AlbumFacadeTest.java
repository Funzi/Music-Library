package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.AlbumFacade;
import cz.muni.fi.pa165.api.SongFacade;
import cz.muni.fi.pa165.api.dto.AlbumDTO;
import cz.muni.fi.pa165.api.dto.SongDTO;
import cz.muni.fi.pa165.config.ServiceConfiguration;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Jan Stourac
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class AlbumFacadeTest extends AbstractTestNGSpringContextTests {

	@Autowired
    private AlbumFacade albumFacade;

	@Autowired
    private SongFacade songFacade;

    private AlbumDTO album1;

    @BeforeMethod
    public void setUp() {
		album1 = new AlbumDTO() {
			{
				setTitle("Album title");
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

		songFacade.createSong(song1);
		Set<SongDTO> songs = albumFacade.getAlbumById(albumId).getSongs();

		assertEquals(songs.size(), 1);
		songs.forEach(s -> assertEquals(s.getTitle(), song1.getTitle()));
	}

}
