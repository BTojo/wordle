package ru.wordle.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class JpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        // Укажите фактический пакет ваших @Entity:
        em.setPackagesToScan("ru.wordle.domain.entity");
        // Критично: явный провайдер для Hibernate 4.3.x
        em.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistence.class);

        Properties props = new Properties();
        props.put("hibernate.hbm2ddl.auto", "update"); // авто-создание/обновление таблиц
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        props.put("hibernate.show_sql", "true");        // опционально
        props.put("hibernate.format_sql", "true");      // опционально
        props.put("hibernate.jdbc.lob.non_contextual_creation", "true");
        em.setJpaProperties(props);
        return em;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
