package com.wantedalways.config;

import com.wantedalways.config.vo.ShiroVo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 读取配置文件
 * @author Wantedalways
 */
@Component("baseConfig")
@ConditionalOnProperty("wantedalways")
@Setter
@Getter
public class BaseConfig {

    /**
     * 排除shiro拦截项
     */
    private ShiroVo shiro;
}
