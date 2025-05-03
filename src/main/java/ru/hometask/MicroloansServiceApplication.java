package ru.hometask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.hometask.repositories")
public class MicroloansServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroloansServiceApplication.class, args);
    }
}
