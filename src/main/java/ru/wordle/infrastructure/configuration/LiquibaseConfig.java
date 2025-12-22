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

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setChangeLog(changeLogPath);
        liquibase.setDataSource(dataSource);


        return liquibase;
    }
}
