package ru.wordle.infrastructure.initializer;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BootstrapDatabaseInitializer implements InitializingBean {

    private final JdbcTemplate jdbc;

    public BootstrapDatabaseInitializer(@Qualifier("bootstrapJdbcTemplate") JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[BOOTSTRAP] init start");
        try {
            Integer test = jdbc.queryForObject("SELECT 1", Integer.class);
            System.out.println("[BOOTSTRAP] connected to postgres OK, SELECT 1 = " + test);

            Integer exists = jdbc.query(
                    "SELECT 1 FROM pg_database WHERE datname = ?",
                    ps -> ps.setString(1, "wordle"),
                    rs -> rs.next() ? 1 : 0
            );
            System.out.println("[BOOTSTRAP] check 'wordle' exists = " + exists);

            if (exists != null && exists == 1) {
                System.out.println("[BOOTSTRAP] database 'wordle' already exists — skip create");
                return;
            }

            System.out.println("[BOOTSTRAP] creating database 'wordle' ...");
            jdbc.execute("CREATE DATABASE wordle WITH ENCODING 'UTF8' TEMPLATE template1 OWNER postgres");
            System.out.println("[BOOTSTRAP] database 'wordle' created");

        } catch (Exception e) {
            System.out.println("[BOOTSTRAP] ERROR: " + e.getClass().getName() + ": " + e.getMessage());
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("already exists")) {
                System.out.println("[BOOTSTRAP] database 'wordle' already exists (race) — continue");
            } else {
                throw new RuntimeException("Failed to initialize database", e);
            }
        } finally {
            System.out.println("[BOOTSTRAP] init end");
        }
    }
}