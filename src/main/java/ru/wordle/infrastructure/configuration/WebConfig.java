package ru.wordle.infrastructure.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ru.wordle")
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
public class WebConfig extends WebMvcConfigurerAdapter {
}
