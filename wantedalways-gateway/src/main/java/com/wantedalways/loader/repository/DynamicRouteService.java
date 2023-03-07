package com.wantedalways.loader.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author Wantedalways
 */
@Slf4j
@Service
public class DynamicRouteService implements ApplicationEventPublisherAware {

    @Autowired
    private MyInMemoryRouteDefinitionRepository repository;

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * 新增路由
     */
    public synchronized String add(RouteDefinition definition) {
        log.info("新增路由：" + definition);
        try {
            repository.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
        } catch (Exception e) {
            log.warn(e.getMessage(),e);
        }
        return "success";
    }

    /**
     * 更新路由
     */
    public synchronized String update(RouteDefinition definition) {
        try {
            log.info("更新路由：" + definition);
        } catch (Exception e) {
            return "更新失败，未找到路由！路由id：" + definition.getId();
        }
        try {
            repository.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            return "update route fail";
        }
    }
}
