package com.algorceries.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.algorceries.api.entity.Household;
import com.algorceries.api.repository.HouseholdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ApiApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    @Profile("dev")
    CommandLineRunner seedDevHousehold(HouseholdRepository repo) {
        return args -> {
            if (repo.findByName("dev").isEmpty()) {
                repo.save(new Household("dev"));
                LOGGER.info("Created \"dev\" household.");
            }
        };
    }
}
