package com.wantedalways.loader;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.google.common.collect.Lists;
import com.wantedalways.config.RouteConfig;
import com.wantedalways.loader.repository.DynamicRouteService;
import com.wantedalways.loader.vo.MyRouteDefinition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

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

    @Autowired
    private RouteConfig routeConfig;

    @Autowired
    private DynamicRouteService routeService;

    private ApplicationEventPublisher publisher;

    private ConfigService configService;

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

        List<RouteDefinition> routes = Lists.newArrayList();
        try {
            configService = NacosFactory.createConfigService(routeConfig.getServerAddr());
            String configInfo = configService.getConfig(routeConfig.getDataId(), routeConfig.getRouteGroup(), DEFAULT_TIMEOUT);
            if (StringUtils.isNotBlank(configInfo)) {
                log.info("获取当前网关配置:\n\r" + configInfo);
                routes = JSON.parseArray(configInfo, RouteDefinition.class);
            } else {
                log.warn("未配置网关，请检查nacos！");
            }
        } catch (NacosException | NullPointerException e) {
            log.error("创建ConfigService失败", e);
        }

        for (RouteDefinition definition : routes) {
            routeService.add(definition);
        }
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        dynamicRouteByNacosListener(routeConfig.getDataId(), routeConfig.getRouteGroup());
    }

    /**
     * 监听nacos路由
     */
    public void dynamicRouteByNacosListener(String dataId, String group) {
        try {
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("进行网关更新:\n\r{}", configInfo);
                    List<MyRouteDefinition> definitionList = JSON.parseArray(configInfo, MyRouteDefinition.class);
                    for (MyRouteDefinition definition : definitionList) {
                        routeService.update(definition);
                    }
                }

                @Override
                public Executor getExecutor() {
                    log.info("getExecutor\n\r");
                    return null;
                }
            });
        } catch (Exception e) {
            log.error("从Nacos读取动态路由配置出错！", e);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
