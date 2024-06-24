package com.zerobase.storereservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StoreReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreReservationApplication.class, args);
    }

}
