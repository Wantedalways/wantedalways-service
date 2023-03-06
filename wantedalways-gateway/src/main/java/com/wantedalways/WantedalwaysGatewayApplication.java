package com.wantedalways;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Wantedalways
 */
@SpringBootApplication
@EnableDiscoveryClient
public class WantedalwaysGatewayApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WantedalwaysGatewayApplication.class, args);
    }

    /**
     * 容器初始化后加载路由
     */
    @Override
    public void run(String... args) throws Exception {

    }
}