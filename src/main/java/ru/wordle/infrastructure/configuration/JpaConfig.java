package ru.wordle.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate; // Не забудь импорт!

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@Import(PropertiesConfig.class)
public class JpaConfig {

    @Value("${jpa.packages-to-scan}")
    private String packagesToScan;

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Value("${hibernate.show_sql}")
    private String showSql;

    @Value("${hibernate.format_sql}")
    private String formatSql;

    @Value("${hibernate.jdbc.lob.non_contextual_creation}")
    private String nonContextualCreation;

    @Bean
    @DependsOn("liquibase")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(packagesToScan);

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties props = new Properties();
        props.put("hibernate.dialect", dialect);
        props.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        props.put("hibernate.show_sql", showSql);
        props.put("hibernate.format_sql", formatSql);
        props.put("hibernate.jdbc.lob.non_contextual_creation", nonContextualCreation);

        em.setJpaProperties(props);
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
}
