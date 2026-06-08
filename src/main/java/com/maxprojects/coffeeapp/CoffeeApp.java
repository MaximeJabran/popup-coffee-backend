package com.maxprojects.coffeeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoffeeApp {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeApp.class, args);
    }

}
