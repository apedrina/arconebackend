package io.github.apedrina.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@EnableJpaRepositories
@SpringBootApplication
public class ArcOneWebApplication {

    private static final String ADDRESS = "1234 NW Bobcat Lane, St. Robert, MO 65584-5678";
    private static final String DATE = "1980-08-23";
    private static final String EMAIL = "robertson@gmail.com";
    private static final String NAME = "Robertson";
    private static final String LAST_NAME = "Martin";
    private static final long PHONE = 2124567890l;

    public static void main(String[] args) {

        SpringApplication.run(ArcOneWebApplication.class, args);

    }

    @Bean
    @Primary
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        return Jackson2ObjectMapperBuilder.json();

    }

}
