package com.chenk.mq_producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MqProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqProducerApplication.class, args);
    }

}
