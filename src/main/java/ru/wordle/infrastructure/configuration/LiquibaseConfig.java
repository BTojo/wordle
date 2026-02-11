package ru.wordle.infrastructure.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Value("${spring.liquibase.change-log:classpath:db/changelog/db.changelog-master.xml}")
    private String changeLogPath;

    @Value("${spring.liquibase.default-schema:wordle}")
    private String defaultSchema;

    @Value("${spring.liquibase.liquibase-schema:wordle}")
    private String liquibaseSchema;

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setChangeLog(changeLogPath);
        liquibase.setDataSource(dataSource);

        // ВАЖНО: Установите схему
        liquibase.setDefaultSchema(defaultSchema);
        liquibase.setLiquibaseSchema(liquibaseSchema);

        // Дополнительные настройки
        liquibase.setShouldRun(true);
        liquibase.setDropFirst(false);
        liquibase.setTestRollbackOnUpdate(false);

        return liquibase;
    }
}