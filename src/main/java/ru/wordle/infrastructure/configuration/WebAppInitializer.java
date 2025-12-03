package ru.wordle.infrastructure.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(
                PropertiesConfig.class,
                DatabaseConfig.class,
                JpaConfig.class,
                LiquibaseConfig.class
        );
        servletContext.addListener(new ContextLoaderListener(rootContext));


        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.setParent(rootContext);
        webContext.register(WebConfig.class);

        ServletRegistration.Dynamic dispatcher =
                servletContext.addServlet("dispatcher", new DispatcherServlet(webContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
