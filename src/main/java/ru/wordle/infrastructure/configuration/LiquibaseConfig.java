package ru.wordle.infrastructure.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Value("${liqui.enabled:true}")
    private boolean liquibaseEnabled;

    // Читаем имя схемы из application.properties
    @Value("${liqui.default-schema:wordle}")
    private String defaultSchema;

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);

        liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.xml");

        liquibase.setDefaultSchema(defaultSchema);

        liquibase.setShouldRun(liquibaseEnabled);

        return liquibase;
    }
}
