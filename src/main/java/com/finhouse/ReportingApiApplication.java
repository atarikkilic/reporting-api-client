package com.finhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ReportingApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReportingApiApplication.class, args);
    }
}