package ru.fast.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class JKHConfigServer {

    public static void main(String[] args) {
        SpringApplication.run(JKHConfigServer.class, args);
    }
}
