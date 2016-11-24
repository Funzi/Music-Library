package cz.muni.fi.pa165.service;

import org.springframework.stereotype.Service;

/**
 *
 * @author Jan Stourac
 */
@Service
public interface SecurityService {

	String getLoggedInUsername();

    void autologin(String username, String password);
}
