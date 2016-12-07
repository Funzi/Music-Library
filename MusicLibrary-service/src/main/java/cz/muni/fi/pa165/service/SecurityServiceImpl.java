package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jan Stourac
 */
@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

	@Override
	public String getLoggedInUsername() {
		try {
			return SecurityContextHolder.getContext().getAuthentication().getName();
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public User getLoggedInUser() {
		String username = getLoggedInUsername();
		return username != null ? userService.findByUsername(username) : null;
	}

	@Override
	public void autologin(String username, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			logger.debug(String.format("Auto login %s successfully!", username));
		}
	}

	@Override
	public boolean hasRole(String role) {
		try {
			return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(role));
		} catch (Exception ex) {
			return false;
		}
	}

}
