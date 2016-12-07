package cz.muni.fi.pa165.api;

import cz.muni.fi.pa165.api.dto.UserDTO;

/**
 *
 * @author Jan Stourac
 */
public interface SecurityFacade {

	String getLoggedInUsername();

	UserDTO getLoggedInUser();

}
