package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.SecurityFacade;
import cz.muni.fi.pa165.api.dto.UserDTO;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.SecurityService;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jan Stourac
 */
@Service
@Transactional
public class SecurityFacadeImpl implements SecurityFacade {

	final static Logger LOG = LoggerFactory.getLogger(SecurityFacadeImpl.class);

	@Inject
	private BeanMappingService beanMappingService;

	@Inject
	private SecurityService securityService;

	@Override
	public String getLoggedInUsername() {
		return securityService.getLoggedInUsername();
	}

	@Override
	public UserDTO getLoggedInUser() {
		return beanMappingService.mapTo(securityService.getLoggedInUser(), UserDTO.class);
	}


}
