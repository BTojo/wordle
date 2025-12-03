package ru.wordle.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configurationpublic
class BootstrapDatabaseConfig {

    @Bean(name = "bootstrapDataSource")
    public DataSource bootstrapDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5433/postgres");
        ds.setUsername("postgres");
        ds.setPassword("123");
        return ds;
    }

    @Bean(name = "bootstrapJdbcTemplate")
    public JdbcTemplate bootstrapJdbcTemplate(DataSource bootstrapDataSource) {
        return new JdbcTemplate(bootstrapDataSource);
    }
}
