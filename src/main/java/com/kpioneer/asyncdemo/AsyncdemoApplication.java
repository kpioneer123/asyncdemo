package com.kpioneer.asyncdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AsyncdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncdemoApplication.class, args);
    }

}
