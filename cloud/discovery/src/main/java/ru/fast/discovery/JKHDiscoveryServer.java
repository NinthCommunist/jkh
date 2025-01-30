package ru.fast.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class JKHDiscoveryServer {

    public static void main(String[] args) {
        SpringApplication.run(JKHDiscoveryServer.class, args);
    }
}
