package com.wantedalways.loader.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.route.RouteDefinition;

/**
 * @author Wantedalways
 */
@Setter
@Getter
public class MyRouteDefinition extends RouteDefinition {

    private Integer status;
}
