package com.wantedalways.config;

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
public class RouteConfig {

    public String serverAddr;

    public String dataId;

    public String routeGroup;

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    @Value("${wantedalways.config.route.data-id}")
    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    @Value("${wantedalways.config.route.group:DEFAULT_GROUP}")
    public void setRouteGroup(String routeGroup) {
        this.routeGroup = routeGroup;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public String getDataId() {
        return dataId;
    }

    public String getRouteGroup() {
        return routeGroup;
    }
}
