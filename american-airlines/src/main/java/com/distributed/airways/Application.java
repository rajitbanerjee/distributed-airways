package com.distributed.airways;

import com.distributed.airways.entity.Flight;
import com.distributed.airways.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        context.getBean(FlightRepository.class).save(new Flight("1", "MH150"));
    }
}
