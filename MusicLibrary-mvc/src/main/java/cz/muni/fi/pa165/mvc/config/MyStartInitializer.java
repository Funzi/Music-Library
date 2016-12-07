package cz.muni.fi.pa165.mvc.config;

import cz.muni.fi.pa165.RestWebContext;
import cz.muni.fi.pa165.config.SecurityConfig;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Replaces web.xml file. Extends the class
 * {@link org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer}
 * that
 * <ul>
 * <li>creates spring context specified in class returned by
 * {@link #getRootConfigClasses()}</li>
 * <li>initializes
 * {@link org.springframework.web.servlet.DispatcherServlet Spring MVC dispatcher servlet}
 * with it</li>
 * <li>maps dispatcher servlet to URL returned by
 * {@link #getServletMappings()}</li>
 * <li>maps filters returned by {@link #getServletFilters()} to the dispatcher
 * servlet</li>
 * </ul>
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public class MyStartInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{MySpringMvcConfig.class, SecurityConfig.class, RestWebContext.class};
	}

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
		encodingFilter.addMappingForUrlPatterns(null, false, "/*");

		super.onStartup(servletContext);
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("utf-8");
		return new Filter[]{encodingFilter};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

}
