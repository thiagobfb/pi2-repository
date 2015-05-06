package br.upis.sel.config;



import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.sun.faces.config.ConfigureListener;


public class ConfigInitialize extends AbstractAnnotationConfigDispatcherServletInitializer {

	
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {SELSecurityConfig.class, SELConfig.class };
	}

	
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { SELSecurityConfig.class, SELConfig.class };
	}

	
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	
	@Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        // Use JSF view templates saved as *.xhtml, for use with Facelets
        servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
        // Enable special Facelets debug output during development
        servletContext.setInitParameter("javax.faces.PROJECT_STAGE","Development");
        // Causes Facelets to refresh templates during development
        servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD",     "1");
        
        servletContext.setInitParameter("primefaces.THEME", "glass-x");
//        // Declare Spring Security Facelets tag library
//        servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES",  "/WEB-INF/springsecurity.taglib.xml");

        servletContext.addListener(ConfigureListener.class);

        ServletRegistration.Dynamic facesServlet = servletContext.addServlet("Faces Servlet", FacesServlet.class);
        facesServlet.setLoadOnStartup(1);
        facesServlet.addMapping("*.jsf");
        // Let the DispatcherServlet be registered
        super.onStartup(servletContext);
    }

	
}
