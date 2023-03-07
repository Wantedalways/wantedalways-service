package com.wantedalways.loader.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Wantedalways
 */
@Slf4j
@Component
public class MyInMemoryRouteDefinitionRepository implements RouteDefinitionRepository {

    private final Map<String, RouteDefinition> routes = Collections.synchronizedMap(new LinkedHashMap<>());

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return null;
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap((r) -> {
            if (ObjectUtils.isEmpty(r.getId())) {
                return Mono.error(new IllegalArgumentException("路由id不能为空！"));
            } else {
                this.routes.put(r.getId(), r);
                return Mono.empty();
            }
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }
}
