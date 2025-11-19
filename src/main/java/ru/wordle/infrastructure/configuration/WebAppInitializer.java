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
        // 1) Root context: здесь регистрируем bootstrap-компоненты и основной DataSource
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(
                BootstrapDatabaseConfig.class,   // DataSource -> postgres (для CREATE DATABASE)
                ru.wordle.infrastructure.initializer.BootstrapDatabaseInitializer.class,
                DatabaseConfig.class             // основной DataSource -> wordle и JdbcTemplate
        );
        servletContext.addListener(new ContextLoaderListener(rootContext)); // поднимет root раньше MVC

        // 2) Web (child) context: только веб-слой (контроллеры, MVC)
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.setParent(rootContext);
        webContext.register(WebConfig.class);

        // 3) DispatcherServlet
        ServletRegistration.Dynamic dispatcher =
                servletContext.addServlet("dispatcher", new DispatcherServlet(webContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
