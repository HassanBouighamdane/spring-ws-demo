package com.exemple.wswithspring;

import com.exemple.wswithspring.entities.Compte;
import com.exemple.wswithspring.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner run(CompteRepository compteRepository) {
        return args -> {
           compteRepository.save(new Compte(null,9000,new Date(),"active"));
           compteRepository.save(new Compte(null,3000,new Date(),"active"));
           compteRepository.save(new Compte(null,4000,new Date(),"blocked"));

            compteRepository.findAll().forEach(System.out::println);

        };
    }
}
