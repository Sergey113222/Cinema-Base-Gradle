package com.example.cinemabasegradle.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.example.cinemabasegradle")
@EntityScan("com.example.cinemabasegradle")
@EnableAutoConfiguration
@PropertySource("classpath:application-testService.properties")
public class EmbeddedTestServiceConfig {
}