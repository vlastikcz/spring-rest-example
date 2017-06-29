package com.github.vlastikcz.springrestexample.configuration;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringRestExampleConfiguration {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
