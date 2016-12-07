package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.User;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jan Stourac
 */
@Service
public interface SecurityService {

	String getLoggedInUsername();

	User getLoggedInUser();

	boolean hasRole(String role);

    void autologin(String username, String password);
}
