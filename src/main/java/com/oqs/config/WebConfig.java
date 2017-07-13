package com.oqs.config;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        //create the root Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationConfig.class, SecurityConfig.class);

        servletContext.addListener(new ContextLoaderListener(rootContext));

        //Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext servletApplicationContext = new AnnotationConfigWebApplicationContext();
        servletApplicationContext.register(MVCConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(servletApplicationContext);
        // throw NoHandlerFoundException to controller ExceptionHandler.class. Used for <error-page> analogue
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);//TODO set exception handler servlet

        //register and map the dispatcher servlet
        //note Dispatcher servlet with constructor arguments
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

    /**
     * added to load spring security filter in root context (created in onStartup())
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {SecurityConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[0];
    }
}