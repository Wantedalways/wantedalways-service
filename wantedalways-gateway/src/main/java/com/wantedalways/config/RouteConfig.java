package com.wantedalways.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 路由配置类
 *
 * @author Wantedalways
 */
@Configuration
@RefreshScope
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteConfig {

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    public String serverAddr;

    @Value("${wantedalways.config.route.data-id}")
    public String dataId;

    @Value("${wantedalways.config.route.group:DEFAULT_GROUP}")
    public String routeGroup;

}
