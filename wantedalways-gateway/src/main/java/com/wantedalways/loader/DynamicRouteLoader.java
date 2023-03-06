package com.wantedalways.loader;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.wantedalways.config.RouteConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * 动态路由加载器
 *
 * @author Wantedalways
 */
@Slf4j
@Component
@DependsOn("routeConfig")
@RefreshScope
public class DynamicRouteLoader implements ApplicationEventPublisherAware {

    private RouteConfig routeConfig;

    public static final long DEFAULT_TIMEOUT = 30000;

    /**
     * 初始化路由
     */
    public void init() {
        log.info("初始化路由...");
        loadRoutesByNacos();
    }

    /**
     * 从nacos中加载路由配置
     */
    private void loadRoutesByNacos() {
        try {
            ConfigService configService = NacosFactory.createConfigService(routeConfig.getServerAddr());
            String configInfo = configService.getConfig(routeConfig.getDataId(), routeConfig.getRouteGroup(), DEFAULT_TIMEOUT);

        } catch (NacosException | NullPointerException e) {
            log.error("创建ConfigService失败", e);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }
}
