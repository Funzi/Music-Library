package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.ArtFacade;
import cz.muni.fi.pa165.api.dto.ArtDTO;
import cz.muni.fi.pa165.entity.Art;
import cz.muni.fi.pa165.service.ArtService;
import cz.muni.fi.pa165.service.BeanMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Base64;

/**
 * Created by olda on 23.11.2016.
 */
@Service
@Transactional
public class ArtFacadeImpl implements ArtFacade {

    @Inject
    private ArtService artService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long createArt(ArtDTO artDTO) {
        Art art = artService.createArt(beanMappingService.mapTo(artDTO, Art.class));
        return art.getId();
    }

    @Override
    public ArtDTO getArtById(Long id) {
        Art art = artService.findArtById(id);
        return beanMappingService.mapTo(art, ArtDTO.class);
    }

    @Override
    public void deleteArt(ArtDTO artDTO) {
        Art art = beanMappingService.mapTo(artDTO, Art.class);
        artService.deleteArt(art);
    }

    /* we using Dozer bean mapping now
    private Art convertToArt(ArtDTO artDTO) {
        Art art = new Art();
        art.setImageName(artDTO.getImageName());
        art.setImageType(artDTO.getImageType());
        art.setImage(Base64.getDecoder().decode(artDTO.getImage()));

        return art;
    }

    private ArtDTO convertToArtDto(Art art) {
        ArtDTO artDTO = new ArtDTO();
        artDTO.setId(art.getId());
        artDTO.setImageType(art.getImageType());
        artDTO.setImageName(art.getImageName());
        artDTO.setImage(Base64.getEncoder().encodeToString(art.getImage()));
        return artDTO;
    }
    */


}
