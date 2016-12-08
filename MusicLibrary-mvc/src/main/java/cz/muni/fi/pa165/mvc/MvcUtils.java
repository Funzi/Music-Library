package cz.muni.fi.pa165.mvc;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Jan Stourac
 */
public class MvcUtils {

	public static String redirectReferer(HttpServletRequest request) {
		return "redirect:" + request.getHeader("Referer");
	}
}
