package ru.fast.mobapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableRedisRepositories
public class MobileAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(MobileAppApplication.class, args);
    }
}

//TODO balancer  + refresh token + rm cache