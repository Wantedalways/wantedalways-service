package com.wantedalways;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Wantedalways
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.wantedalways"})
public class WantedalwaysSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(WantedalwaysSystemApplication.class, args);
    }
}
