package org.example.config;

import org.example.service.MutantDetector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MutantDetector mutantDetector() {
        return new MutantDetector();
    }
}
