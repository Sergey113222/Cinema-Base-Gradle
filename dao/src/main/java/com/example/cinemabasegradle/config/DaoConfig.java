package com.example.cinemabasegradle.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:sql-query.properties")
public class DaoConfig {
}