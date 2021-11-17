package com.example.cinemabasegradle.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.example.cinemabasegradle")
@EntityScan("com.example.cinemabasegradle")
@EnableAutoConfiguration
@PropertySource("classpath:application-testJpa.properties")
@PropertySource("classpath:sql-query.properties")
public class EmbeddedTestConfig {

    @Bean
    @Primary
    public LiquibaseProperties liquibaseProperties() {
        LiquibaseProperties properties = new LiquibaseProperties();
        properties.setChangeLog("classpath:/db/changelog/changelog-master.xml");
        return properties;
    }
}
