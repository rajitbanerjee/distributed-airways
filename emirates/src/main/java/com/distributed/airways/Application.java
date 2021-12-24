package com.distributed.airways;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.out.println("Starting airline service: Emirates");
        SpringApplication.run(Application.class, args);
    }
}
