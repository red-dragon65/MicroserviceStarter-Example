package com.example.hiddenservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// This tells spring to let this app register and find services using the Discovery-Server
@EnableDiscoveryClient

@SpringBootApplication
public class HiddenServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiddenServiceApplication.class, args);
    }

}
