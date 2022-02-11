package com.musala.droneproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DroneprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DroneprojectApplication.class, args);
    }

}
