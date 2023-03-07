package com.wantedalways;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Wantedalways
 */
@SpringBootApplication
@EnableDiscoveryClient
public class WantedalwaysSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(WantedalwaysSystemApplication.class, args);
    }
}
