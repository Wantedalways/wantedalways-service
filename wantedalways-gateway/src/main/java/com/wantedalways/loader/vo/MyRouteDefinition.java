package com.wantedalways.loader.vo;

import lombok.Data;
import org.springframework.cloud.gateway.route.RouteDefinition;

/**
 * @author Wantedalways
 */
@Data
public class MyRouteDefinition extends RouteDefinition {

    private Integer status;
}
