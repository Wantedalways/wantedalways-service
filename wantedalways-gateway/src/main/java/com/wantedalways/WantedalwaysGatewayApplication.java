package com.wantedalways;

import com.wantedalways.loader.DynamicRouteLoader;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DynamicRouteLoader dynamicRouteLoader;

    public static void main(String[] args) {
        SpringApplication.run(WantedalwaysGatewayApplication.class, args);
    }

    /**
     * 容器初始化后加载路由
     */
    @Override
    public void run(String... args) {
        dynamicRouteLoader.init();
    }
}