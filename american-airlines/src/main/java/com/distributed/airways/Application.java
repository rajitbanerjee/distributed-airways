package com.distributed.airways;

import com.distributed.airways.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {
    public static void main(String[] args) {
        System.out.println("Starting airline service: American Airlines");
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        initDatabase(context);
    }

    private static void initDatabase(ConfigurableApplicationContext context) {
        context.getBean(FlightService.class).initDatabase();
    }
}
